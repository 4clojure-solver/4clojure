;; Sum It All Up
(defn p24 [c]
  (reduce #(+ % %2) c))
