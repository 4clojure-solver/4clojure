(ns p077.kep)


(defn foo [lst]
  (->> lst
       (group-by set)
       (map second)
       (filter #(<= 2 (count %)))
       (map set)
       (set)))
