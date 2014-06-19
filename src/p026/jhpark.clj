(ns p026.jhpark)




(def a 3)

(peek (pop [0 1]))

(defn fibonacci
  [data cnt acc]
  (if (= data cnt)
    acc
    (let [b-last (if (nil? (peek (vec (butlast acc)))) 0)
          last   (if (nil? (peek acc) ) 1 (peek acc))]
      (println "b-last-> " b-last " last-> " last " acc-> " acc)
    (fibonacci data (inc cnt) (conj acc last ) )  ))
  )


(fibonacci 3 0 [])

(vec (butlast [1]))