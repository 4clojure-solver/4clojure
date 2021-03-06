(ns npr.kep
  (:require
   [clojure.tools.trace :as t]
   [clojure.math.combinatorics :as combo]))



(combo/combinations [1 2 3] 2)
;=> ((1 2) (1 3) (2 3))

(combo/permutations [1])
;=> ([1])

(combo/permutations [1 2])
;=> ([1 2] [2 1])

(combo/permutations [2 3])
;=> ([2 3] [3 2])


(defn perm [x]
  (if (zero? (count x))
    (list x)
    (mapcat (fn [a]
              (map (fn [b]
                     (vec (concat [a] b)))
                   (perm (remove #{a} x))))
            x)))


(= (combo/permutations [1 2 3 4]) (perm [1 2 3 4]))
;=> true


(defn npr
  ([x]
     (npr x (count x)))
  ([x cnt]
     (if (zero? cnt)
       (list x)
       (mapcat (fn [a]
                 (map (fn [b]
                        (vec (take cnt (concat [a] b))))
                      (npr (remove #{a} x) (dec cnt))))
               x))))

(npr [1 2 3] 1)
;=> ([1] [2] [3])

(npr [1 2 3] 2)
;=> ([1 2] [1 3] [2 1] [2 3] [3 1] [3 2])

(npr [1 2 3] 3)
;=> ([1 2 3] [1 3 2] [2 1 3] [2 3 1] [3 1 2] [3 2 1])
