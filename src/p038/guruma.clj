(ns p038.guruma)


(defn __ [& l]
  (reduce #(if (> %1 %2) %1 %2) l)
  )

(= (__ 1 8 3 4) 8)

(= (__ 30 20) 30)

(= (__ 45 67 11) 67)
