(ns p022.jhpark)

;;; Count a Sequence
(def a '(1 2 3 4 5))
;(reduce (fn [ele] ele) a)
(def data1 [[1 2] [3 4] [5 6]])

(loop [s  a cnt 0]
  (if (empty? s)
    cnt
    (recur (rest s) (inc cnt))))









;; Reverse a Sequence

(def data [1 2 3 4 5])

(loop [data' data result ()]
  (if (empty? data')
    result
    (recur (rest data') (conj result (first data')))))

