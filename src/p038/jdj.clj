;; 4Clojure Question 38
;;
;; Write a function which takes a variable number of parameters and returns the maximum value.
;;
;; Restrictions (please don't use these function(s)): max, max-key
;;
;; Use M-x 4clojure-check-answers when you're done!


(defn __ [& xs]
  (last (sort xs)))


(defn __ [& [x & xs]]
  (loop [max x xs xs]
    (if-not (seq xs)
      max
      (let [f (first xs)]
        (recur (if (<= f max) max f) (rest xs))))))


(defn __ [& [first & rest]]
  (reduce #(if (<= %1 %2) %2 %1) first rest))


(= (__ 1 8 3 4) 8)

(= (__ 30 20) 30)

(= (__ 45 67 11) 67)
