(ns base64.core
  (:import org.apache.commons.codec.binary.Base64))


(def prototype-base64-digits "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/")

(defn- string->bytes [s]
  (byte-array (map (comp byte int) s)))

(defn- bytes->string [bytes]
  (apply str (map char bytes)))

(defn- shuffle-str [s]
  (apply str (shuffle (seq s))))

(defn- make-base64-digits []
  (shuffle-str prototype-base64-digits))

(defn- base64-digits->enc-table ^bytes [^String digits]
  (byte-array
    (map (comp byte int) digits)))

(defn- enc-table->dec-table ^bytes [^bytes enc-table]
  (let [^bytes ba (byte-array (inc (apply max enc-table)))]
    (doseq [[idx enc] (map-indexed vector enc-table)]
      (aset-byte ba enc (byte idx)))
    ba))

(defn- enc-length
  "Calculates what would be the length after encoding of an input array of length n."
  ^long [^long n]
  (-> n
    (+ 2)
    (quot 3)
    (* 4)))

(defn- dec-length
  "Calculates what would be the length after decoding of an input array of length
   in-length with the specified padding length."
  ^long [^long in-length ^long pad-length]
  (-> in-length
    (quot 4)
    (* 3)
    (- pad-length)))


(defn- pad-length
  "Returns the length of padding on the end of the input array."
  ^long [^bytes input ^long offset ^long length]
  (let [end (+ offset length -1)]
    (if (== 61 (long (aget input end)))
      (if (== 61 (long (aget input (dec end))))
        2
        1)
      0)))

(defn- sbyte [n]
  "Change int into signed byte."
  (byte (if (< n 128)
          n
          (- n 256))))

(defn- decode!
  "Reads from the input byte array for the specified length starting at the offset
   index, and base64 decodes into the output array starting at index 0. Returns the
   length written to output.

   Note: length must be a multiple of 4."
  ^long [^bytes input ^bytes output ^bytes dec-table]
  (let [offset 0
        length (alength input)
        in-end (+ offset length -1)
        pad-len (pad-length input offset length)
        out-len (dec-length length pad-len)
        out-end (dec out-len)
        tail-len (rem out-len 3)
        loop-lim (- out-len tail-len)]
    (loop [i offset j 0]
      (when (< j loop-lim)
        (let [a (long (aget dec-table (aget input i)))
              b (long (aget dec-table (aget input (inc i))))
              c (long (aget dec-table (aget input (+ 2 i))))
              d (long (aget dec-table (aget input (+ 3 i))))
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
              a (long (aget dec-table (aget input i)))
              b (long (aget dec-table (aget input (inc i))))
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
              a (long (aget dec-table (aget input i)))
              b (long (aget dec-table (aget input (inc i))))
              c (long (aget dec-table (aget input (+ 2 i))))
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

(defn- encode!
  "Reads from the input byte array for the specified length starting at the offset
   index, and base64 encodes into the output array starting at index 0. Returns the
   length written to output.

   Note: if using partial input, length must be a multiple of 3 to avoid padding."
  ^long [^bytes input ^bytes output ^bytes enc-table]
  (let [offset 0
        length (alength input)
        tail-len (rem length 3)
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
          (aset output j (aget enc-table a))
          (aset output (inc j) (aget enc-table b))
          (aset output (+ 2 j) (aget enc-table c))
          (aset output (+ 3 j) (aget enc-table d)))
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
          (aset output j (aget enc-table a))
          (aset output (inc j) (aget enc-table b1))
          (aset output (+ 2 j) (sbyte 61))
          (aset output (+ 3 j) (sbyte 61)))
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
          (aset output j (aget enc-table a))
          (aset output (inc j) (aget enc-table b))
          (aset output (+ 2 j) (aget enc-table c1))
          (aset output (+ 3 j) (sbyte 61))))
    out-len))

(defn decode
  "Returns a base64 decoded byte array.

  Note: length must be a multiple of 4."
  [^String input ^bytes dec-table]
    (let [bytes (string->bytes input)
          length (alength bytes)
          dest (byte-array (dec-length length (pad-length bytes 0 length)))]
      (decode! bytes dest dec-table)
;;       (bytes->string dest)))
      (String. dest "UTF-8")))

(defn encode
  "Returns a base64 encoded byte array."
  [^String input ^bytes enc-table]
    (let [bytes (.getBytes input "UTF-8")
          dest (byte-array (enc-length (alength bytes)))]
      (encode! bytes dest enc-table)
      (bytes->string dest)))

(String.
 (Base64/decodeBase64
  (Base64/encodeBase64 (.getBytes "가나다라" "UTF-8")))
 "UTF-8")

(defn create-tables []
  (let [base64-digits (make-base64-digits)
        enc-table (base64-digits->enc-table base64-digits)
        dec-table (enc-table->dec-table enc-table)]
    {:base64-digits base64-digits
     :enc-table enc-table
     :dec-table dec-table}))

(def user* (atom {:sock nil}))

; when user longins, cription tables is make by server.
; And it should be set in user with sock.
(defn init-tables []
  (swap! user* assoc :cript (create-tables)))

(defn test-cript [s]
  (let [{:keys [base64-digits enc-table dec-table]} (:cript @user*)]
    (= s (decode (encode s enc-table) dec-table))))

(init-tables)

(decode
 (encode "가나다라" (get-in @user* [:cript :enc-table]))
  (get-in @user* [:cript :dec-table]))

(test-cript "hello world!")
(test-cript "`!@   ~@#!#$@$%#^*&^(&()*(+)__*({}{|{|\"::<>?<>\"}})")
(test-cript "가나다라")
(test-cript "서울")
(test-cript "こんにちは")
(test-cript "你好")
(test-cript "Привет")
(test-cript "हाय")
(test-cript "서울 abcd 123ㅏㅕ4")
(test-cript (str {"a" 123}))
(test-cript (str {123 123}))
(test-cript (str {{:a 1} {:b 22}}))
(test-cript (str {:a 1 :b 3 :c {:fdaf 5}}))
(test-cript (str [1 2 3 4 5]))
(test-cript (str #{1 2 3 4 5}))
(test-cript (str '(1 2 3 4 5)))
