(ns p025.kep)

(def __ (partial filter odd?))

(= (__ #{1 2 3 4 5}) '(1 3 5))
