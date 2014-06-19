(ns p026.guruma)

(defn fib [n]
  (case n
    0 1
    1 1
    (+ (fib (- n 1)) (fib (- n 2)))))


(= 1 (fib 0))
(= 1 (fib 1))
(= 2 (fib 2))
(= 3 (fib 3))
(= 5 (fib 4))
(= 8 (fib 5))


(defn __ [n]
  (for [k (range n)]
    (fib k)))

;; testcase-1
(= (__ 3) '(1 1 2))

;; testcase-2
(= (__ 6) '(1 1 2 3 5 8))

;; testcase-3
(= (__ 8) '(1 1 2 3 5 8 13 21))
