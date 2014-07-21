(ns p032.jdj)
;; 4Clojure Question 32
;;
;; Write a function which duplicates each element of a sequence.
;;
;; Use M-x 4clojure-check-answers when you're done!


#_(defn __ [lst]
  (mapcat #(repeat 2 %) lst))


(defmacro __ [lst]
  `(quote ~(mapcat #(repeat 2 %) lst))
  ;;`(mapcat #(repeat 2 %) ~lst)
  )


(defmacro foo []
  `(1 2 3 4 5))


(= (__ [1 2 3]) '(1 1 2 2 3 3))

(= (__ [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))

(= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

(= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))
