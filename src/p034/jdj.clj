(ns p034.jdj)
;; 4Clojure Question 34
;;
;; Write a function which creates a list of all integers in a given range.
;;
;; Restrictions (please don't use these function(s)): range
;;
;; Use M-x 4clojure-check-answers when you're done!


(defn my-range [x y]
  (loop [i x acc []]
    (if (<= y i)
      acc
      (recur (inc i) (conj acc i)))))


(defn my-range2 [x y]
  (take (- y x) (iterate inc x)))



(def __ my-range)

(= (__ 1 4) '(1 2 3))

(= (__ -2 2) '(-2 -1 0 1))

(= (__ 5 8) '(5 6 7))
