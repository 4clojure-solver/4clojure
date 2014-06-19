(ns p026.kep)

(defn fib
  ([]
     (cons 1 (fib 1 1)))
  ([a b]
     (cons a (lazy-seq (fib (+ a b) a)))))

(defn __ [cnt]
  (->> (fib)
       (take cnt)))

(= (__ 3) '(1 1 2))
(= (__ 6) '(1 1 2 3 5 8))
(= (__ 8) '(1 1 2 3 5 8 13 21))
