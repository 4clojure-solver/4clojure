(ns p077.kep)


(defn foo [lst]
  (->> lst
       (group-by frequencies)
       (map second)
       (filter #(<= 2 (count %)))
       (map set)
       (set)))
