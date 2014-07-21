(ns p038.kep)

(defn my-max [& args]
  (->> args
       (sort >)
       (first)))

(def __ my-max)

(= (__ 1 8 3 4) 8)
(= (__ 30 20) 30)
(= (__ 45 67 11) 67)
