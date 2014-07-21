(ns p039.jhpark)



(defn a [first second]
  (flatten (map #(if (pos? %)

                  [% (get second (dec %))])
                first))
  )

(a [1 2 3] [:a :b :c])












