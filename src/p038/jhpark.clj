(ns p038.jhpark)


(def a '(1 8 3 4))
(def b '(45 67 11))

(->> b
    (apply sorted-set)
    (into[])
    (last)
    )

