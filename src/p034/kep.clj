(ns p034.kep)

(defn my-range
  [from to]

  (->> (iterate inc from)
       (take (- to from))))

(def __ my-range)

(= (__ 1 4) '(1 2 3))
(= (__ -2 2) '(-2 -1 0 1))
(= (__ 5 8) '(5 6 7))
