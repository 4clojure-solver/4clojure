(ns p028.guruma)

(defn __ [s]
  (letfn [(g [acc e]
             (if (coll? e)
               (vec (concat acc (__ e)))
               (conj acc e)))]
    (reduce g [] s)))

(= (__ '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))

(= (__ ["a" ["b"] "c"]) '("a" "b" "c"))

(= (__ '((((:a))))) '(:a))



