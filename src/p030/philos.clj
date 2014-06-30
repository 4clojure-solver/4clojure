;; Compress a Sequence 30
(defn compress [c]
  (reduce #(if (= (last %) %2) % (conj % %2)) [] c))
