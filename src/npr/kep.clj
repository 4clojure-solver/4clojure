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
  (if (= (count x) 1)
    (list x)
    (mapcat (fn [a]
              (map (fn [b]
                    (map (fn [c]
                           (flatten (conj [a] c)))
                         b))
                  (perm (remove #{a} x))))
            x)))
