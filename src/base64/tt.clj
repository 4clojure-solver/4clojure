(ns base64.tt
  (:import org.apache.commons.codec.binary.Base64))



(def room {:room1 {:a {:age 1}
                   :b {:age 2}
                   :* {:age 3}
                   :c {:age 4}}})


(defn update-values-in
  "Update values at ks in map by f. use wildcard keyword (:*).

   (update-values-in m [:team :* :age] + 3)"

  [m ks f & args]
  (let [pre-ks (take-while #(not= :* %) ks)
        cnt (count pre-ks)
        iter-ks (vec (keys (get-in m pre-ks)))]
    (loop [m m [k & rks] iter-ks]
      (if (nil? k)
        m
        (recur (update-in m
                          (assoc ks cnt k)
                          #(apply f (cons % args)))
               rks)))))

(update-values-in room [:room1 :* :age] + 3)

(def ^:private ^"[B" enc-bytes
  (byte-array
    (map (comp byte int)
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/")))

(def ^:private ^"[B" dec-bytes
  (let [^bytes ba (byte-array (inc (apply max enc-bytes)))]
    (doseq [[idx enc] (map-indexed vector enc-bytes)]
      (aset ba enc (byte idx)))
    ba))

(defn enc-length
  "Calculates what would be the length after encoding of an input array of length n."
  ^long [^long n]
  (-> n
    (+ 2)
    (quot 3)
    (* 4)))

(defn dec-length
  "Calculates what would be the length after decoding of an input array of length
   in-length with the specified padding length."
  ^long [^long in-length ^long pad-length]
  (-> in-length
    (quot 4)
    (* 3)
    (- pad-length)))


(defn pad-length
  "Returns the length of padding on the end of the input array."
  ^long [^bytes input ^long offset ^long length]
  (let [end (+ offset length -1)]
    (if (== 61 (long (aget input end)))
      (if (== 61 (long (aget input (dec end))))
        2
        1)
      0)))

(defn sbyte [n]
  (byte (if (< n 128)
          n
          (- n 256))))

(defn decode!
  "Reads from the input byte array for the specified length starting at the offset
   index, and base64 decodes into the output array starting at index 0. Returns the
   length written to output.

   Note: length must be a multiple of 4."
  ^long [^bytes input ^long offset ^long length ^bytes output]
  (let [in-end (+ offset length -1)
        pad-len (pad-length input offset length)
        out-len (dec-length length pad-len)
        out-end (dec out-len)
        tail-len (rem out-len 3)
        loop-lim (- out-len tail-len)]
    (loop [i offset j 0]
      (when (< j loop-lim)
        (let [a (long (aget dec-bytes (aget input i)))
              b (long (aget dec-bytes (aget input (inc i))))
              c (long (aget dec-bytes (aget input (+ 2 i))))
              d (long (aget dec-bytes (aget input (+ 3 i))))
              x1 (-> a
                   (bit-and 0x3F)
                   (bit-shift-left 2))
              x2 (-> b
                   (bit-shift-right 4)
                   (bit-and 0x3))
              y1 (->
                   (bit-and b 0xF)
                   (bit-shift-left 4))
              y2 (-> c
                   (bit-shift-right 2)
                   (bit-and 0xF))
              z1 (-> c
                   (bit-and 0x3)
                   (bit-shift-left 6))
              z2 (bit-and d 0x3F)
              x (bit-or x1 x2)
              y (bit-or y1 y2)
              z (bit-or z1 z2)]
          (aset output j (sbyte x))
          (aset output (inc j) (sbyte y))
          (aset output (+ 2 j) (sbyte z)))
        (recur (+ 4 i) (+ 3 j))))
    ; handle padded section
    (case tail-len
      0 nil
      1 (let [i (- in-end 3)
              j out-end
              a (long (aget dec-bytes (aget input i)))
              b (long (aget dec-bytes (aget input (inc i))))
              x1 (-> a
                   (bit-and 0x3F)
                   (bit-shift-left 2))
              x2 (-> b
                   (bit-shift-right 4)
                   (bit-and 0x3))
              x (bit-or x1 x2)]
          (aset output j (sbyte x)))
      2 (let [i (- in-end 3)
              j (dec out-end)
              a (long (aget dec-bytes (aget input i)))
              b (long (aget dec-bytes (aget input (inc i))))
              c (long (aget dec-bytes (aget input (+ 2 i))))
              x1 (-> a
                   (bit-and 0x3F)
                   (bit-shift-left 2))
              x2 (-> b
                   (bit-shift-right 4)
                   (bit-and 0x3))
              y1 (->
                   (bit-and b 0xF)
                   (bit-shift-left 4))
              y2 (-> c
                   (bit-shift-right 2)
                   (bit-and 0xF))
              x (bit-or x1 x2)
              y (bit-or y1 y2)]
          (aset output j (sbyte x))
          (aset output (inc j) (sbyte y))))
    out-len))

(defn decode
  "Returns a base64 decoded byte array.

  Note: length must be a multiple of 4."
  ([^bytes input]
    (decode input 0 (alength input)))
  ([^bytes input ^long offset ^long length]
    (let [dest (byte-array (dec-length length (pad-length input offset length)))]
      (decode! input offset length dest)
      dest)))


(defn encode!
  "Reads from the input byte array for the specified length starting at the offset
   index, and base64 encodes into the output array starting at index 0. Returns the
   length written to output.

   Note: if using partial input, length must be a multiple of 3 to avoid padding."
  ^long [^bytes input ^long offset ^long length ^bytes output]
  (let [tail-len (rem length 3)
        loop-lim (- (+ offset length) tail-len)
        in-end (dec (+ offset length))
        out-len (enc-length length)
        out-end (dec out-len)]
    (loop [i offset j 0]
      (when (< i loop-lim)
        (let [x (long (aget input i))
              y (long (aget input (inc i)))
              z (long (aget input (+ 2 i)))
              a (-> x
                  (bit-shift-right 2)
                  (bit-and 0x3F))
              b1 (-> x
                   (bit-and 0x3)
                   (bit-shift-left 4))
              b2 (-> y
                   (bit-shift-right 4)
                   (bit-and 0xF))
              b (bit-or b1 b2)
              c1 (-> y
                   (bit-and 0xF)
                   (bit-shift-left 2))
              c2 (-> z
                   (bit-shift-right 6)
                   (bit-and 0x3))
              c (bit-or c1 c2)
              d (bit-and z 0x3F)]
          (aset output j (aget enc-bytes a))
          (aset output (inc j) (aget enc-bytes b))
          (aset output (+ 2 j) (aget enc-bytes c))
          (aset output (+ 3 j) (aget enc-bytes d)))
        (recur (+ 3 i) (+ 4 j))))
    ; write padded section
    (case tail-len
      0 nil
      1 (let [i in-end
              j (- out-end 3)
              x (long (aget input i))
              a (-> x
                  (bit-shift-right 2)
                  (bit-and 0x3F))
              b1 (-> x
                   (bit-and 0x3)
                   (bit-shift-left 4))]
          (aset output j (aget enc-bytes a))
          (aset output (inc j) (aget enc-bytes b1))
          (aset output (+ 2 j) (byte 61))
          (aset output (+ 3 j) (byte 61)))
      2 (let [i (dec in-end)
              j (- out-end 3)
              x (long (aget input i))
              y (long (aget input (inc i)))
              a (-> x
                  (bit-shift-right 2)
                  (bit-and 0x3F))
              b1 (-> x
                   (bit-and 0x3)
                   (bit-shift-left 4))
              b2 (-> y
                   (bit-shift-right 4)
                   (bit-and 0xF))
              b (bit-or b1 b2)
              c1 (-> y
                   (bit-and 0xF)
                   (bit-shift-left 2))]
          (aset output j (aget enc-bytes a))
          (aset output (inc j) (aget enc-bytes b))
          (aset output (+ 2 j) (aget enc-bytes c1))
          (aset output (+ 3 j) (byte 61))))
    out-len))

(defn encode
  "Returns a base64 encoded byte array."
  ([^bytes input]
    (encode input 0 (alength input)))
  ([^bytes input ^long offset ^long length]
    (let [dest (byte-array (enc-length length))]
      (encode! input offset length dest)
      dest)))

(defn my-encode [s]
  (encode (.getBytes s "UTF-8")))

(defn my-decode [b]
  (String. (decode b) "UTF-8"))

(my-decode
 (my-encode "abcd"))

;; (Integer/toBinaryString 0xff)
;; (Integer/toBinaryString (byte 0x7f))
;; (Integer/toBinaryString (byte -127))

;0x80 - -128
0x7f
(def a1 0xFF)
(byte (- a1 256) )

(defn sbyte [n]
  (byte (if (< n 128)
          n
          (- n 256))))

(sbyte 0xff)

;; (my-decode
;;  (my-encode "가"))

;; (apply str (map (comp char int) (my-encode "abcd")))
;; (apply str (map (comp char int) (my-encode "가나다라")))

;; (apply str (map (comp char int)
;;                 (Base64/encodeBase64 (.getBytes "가나다라" "UTF-8"))))

;; (String.
;;  (my-decode
;;   (Base64/encodeBase64 (.getBytes "가나다라" "UTF-8"))) "UTF-8")

;; (apply str (map (comp char int)
;;                 (my-encode "가나다라")))

;; (count dec-bytes)
;; (aget dec-bytes 117)
(map int
 (Base64/encodeBase64 (.getBytes "가나다라" "UTF-8")))

(String.
(decode
 (Base64/encodeBase64 (.getBytes "가나다라" "UTF-8"))) "UTF-8")


;; (String.
;;  (Base64/decodeBase64
;;   (Base64/encodeBase64 (.getBytes "가나다라" "UTF-8")))
;;  "UTF-8")

;; (apply str (map (comp char int)
;;                 (Base64/decodeBase64
;;                  (Base64/encodeBase64 (.getBytes "가나다라" "UTF-8")))))
