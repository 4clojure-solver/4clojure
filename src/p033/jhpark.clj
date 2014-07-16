(ns p033.jhpark)

(def c [1 2 3] )


(reduce (fn [acc k ]
          (concat acc (take 3 (cycle (vector k)))))
        '()
        c
        )