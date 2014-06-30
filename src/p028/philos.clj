(fn flat [c]
  (cond
    (coll? c)
    (concat (flat (first c)) (flat (next c)))

    (nil? c) nil
    :else [c] ))

