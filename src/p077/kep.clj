(ns p077.kep)


(defn foo [lst]
  (->> lst
       (group-by set)
       (filter (fn [[_ v]] (<= 2 (count v))))
       (map #(->> % second set))
       (set)))
