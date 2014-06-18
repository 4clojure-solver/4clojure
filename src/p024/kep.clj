(ns p024.kep)

(defn sum [col]
  (reduce + col))

(def __ sum)

(= (__ [1 2 3]) 6)
