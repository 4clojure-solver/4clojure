(ns p030.jhpark)


(def ori "Leeeeeerrroyyy")
(def b [1 1 2 3 3 2 2 3])
(def c [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2])


(loop [data ori acc []]
  (if (empty? data)
    acc
    (do
      (let [acc-list    (last acc)
            data-first  (first data)]
        (if (= acc-list data-first)
          (recur (rest data) acc )
          (recur (rest data)  (conj acc data-first)))
        ) )))

