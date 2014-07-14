(ns p032.jhpark)


(def  c [1 2 3])
(def c  [:a :a :b :b])
(def c [[1 2] [3 4]])



(reduce (fn [acc k]
          (concat acc (take 2 (cycle (vector k)))))
        '()
        c)
