(ns p067.kep)

(defn prime-nums
  ([] (prime-nums 2 []))

  ([n base]
     (cons n
           (lazy-seq (loop [n' (inc n)
                            base' (conj base n)]
                       (if-not (some (fn [x] (zero? (rem n' x))) base')
                         (prime-nums n' base')
                         (recur (inc n') base')))))))

(defn __ [n]
  (take n (prime-nums)))

(= (__ 2) [2 3])
;=> true
(= (__ 5) [2 3 5 7 11])
;=> true
(= (last (__ 100)) 541)
;=> true
