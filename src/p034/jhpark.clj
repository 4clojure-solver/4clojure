(ns p034.jhpark)




(defn a [x y]
  (take (- y x) (iterate inc x)) )

(a 1 4)






