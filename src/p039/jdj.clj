;; 4Clojure Question 39
;;
;; Write a function which takes two sequences and returns the first item from each, then the second item from each, then the third, etc.
;;
;; Restrictions (please don't use these function(s)): interleave
;;
;; Use M-x 4clojure-check-answers when you're done!


(defn __ [l1 l2]
  (mapcat vector l1 l2))


(defn __ [l1 l2]
  (loop [acc [] l1 l1 l2 l2]
    (if-not (and (seq l1) (seq l2))
      acc
      (recur (conj acc (first l1) (first l2)) (rest l1) (rest l2)))))


(defmacro __ [l1 l2]
  `(quote ~(mapcat vector l1 l2)))


(= (__ [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))

(= (__ [1 2] [3 4 5 6]) '(1 3 2 4))

(= (__ [1 2 3 4] [5]) [1 5])

(= (__ [30 20] [25 15]) [30 25 20 15])
