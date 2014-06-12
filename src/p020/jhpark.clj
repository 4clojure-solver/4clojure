(ns npr.jhpark)



;; Penuhimate Element
(def a '(1 2 3 4 5))
(def aa [1 2 3 4 5])
(def aaa [[1 2] [3 4]])


(defn element-get
  [data]
  (let [index (- (count data) 2)]
    (nth data index)))




;; Nth Element
(def b '(4 5 6 7))
(def bb [:a :b :c] )
(def bbb [1 2 3 4] )
(def bbbb '([1 2] [3 4] [5 6]) )


(defn get-element
  [data index]
  (get (vec data) index))


(get-element bbbb 2)



