(ns p034.manmyung)

(defn __ [a b]
  (take (- b a) (iterate inc a)))

;test
(loop [i 2 result []]
  (if (>= i 5)
     result
     (recur (inc i) (conj result i))))

(= (__ 1 4) '(1 2 3))
(= (__ -2 2) '(-2 -1 0 1))
(= (__ 5 8) '(5 6 7))
