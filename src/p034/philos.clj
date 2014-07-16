(fn [start end] (take (- end start) (iterate inc start)))
