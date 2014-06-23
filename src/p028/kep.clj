(ns p028.kep)

(defn flat [x]
  (if (not (coll? x))
    (list x)
    (mapcat flat x)))

(def __ flat)

(__ '((1 2) 3 [4 [5 6]]))

(= (__ '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))

(= (__ ["a" ["b"] "c"]) '("a" "b" "c"))

(= (__ '((((:a))))) '(:a))
