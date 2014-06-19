;; Reverse a Sequence
(defn p26 [n]
  (letfn [(fib [a b] (cons a (lazy-seq (fib b (+ a b)))))]
    (take n (fib 1 1)) ))

