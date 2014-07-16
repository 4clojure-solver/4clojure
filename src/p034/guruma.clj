(ns p034.guruma)

(defn __ [s e]
  (take-while #(< % e) (iterate inc s)))


(= (__ 1 4) '(1 2 3))

(= (__ -2 2) '(-2 -1 0 1))

(= (__ 5 8) '(5 6 7))
