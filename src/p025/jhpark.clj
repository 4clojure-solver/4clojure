(ns p025.jhpark)

(def d [1 2 3 4])



(filter #(not= 0 (mod % 2)) d )
(filter #(odd? %) d)