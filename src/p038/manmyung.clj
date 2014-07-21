(ns p038.manmyung)

(defn __ [& l]
  (reduce (fn [x y]
            (if (< x y)
              y
              x))
          l))

(__ 1 2 3 4)

(= (__ 1 8 3 4) 8)

(= (__ 30 20) 30)

(= (__ 45 67 11) 67)
