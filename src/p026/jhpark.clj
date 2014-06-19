(ns p026.jhpark)


(defn fibonacci
  ([data]
   (fibonacci data 0 []))

  ([data cnt acc]
  (if (= data cnt)
    acc
    (let [b-last  (peek (vec (butlast acc)))
          b-last  (if (nil? b-last) 0 b-last)
          last    (peek acc)
          last    (if (nil? last) 1 last)]
    (fibonacci data (inc cnt) (conj acc (+ last b-last )) )  ))))


(fibonacci 80)