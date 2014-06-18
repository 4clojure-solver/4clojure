(defn mytest [f]
  (every? true?
   [(= (f '(4 5 6 7) 2) 6)
    (= (f [:a :b :c] 0) :a)
    (= (f [1 2 3 4] 1) 2)
    (= (f '([1 2] [3 4] [5 6]) 2) [5 6])
    ]))



(defn my-nth [lst i]
  (get (vec lst) i))


(defn my-nth2 [lst i]
  (loop [n i l lst]
    (if (<= n 0)
      (first l)
      (recur (dec n) (rest l)))))


(defmacro my-nth3 [body i]
  (if (<= i 0)
    `(first ~body)
    `(my-nth3 (rest ~body) ~(dec i))))

;;(f [1 2 3 4] 2)
;;=> (first (rest (rest [1 2 3 4])))



(mytest my-nth2)
