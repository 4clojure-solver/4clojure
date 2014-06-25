(ns p030.guruma)


(defn __ [s]
  (reduce #(if (= %2 (last %1)) %1 (conj %1 %2)) [] s))

(= (apply str (__ "Leeeeeerrroyyy")) "Leroy")

(= (__ [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))

(= (__ [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))

