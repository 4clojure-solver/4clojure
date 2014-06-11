(ns p021.kep)


(defn foo [col n]
  {:pre [(not (neg? n))]}

  (loop [n' n col' col]
    (if (zero? n')
      (first col')
      (recur (dec n') (next col')))))


(def __ foo)


(= (__ '(4 5 6 7) 2) 6)
;=> true

(= (__ [:a :b :c] 0) :a)
;=> true

(= (__ [1 2 3 4] 1) 2)
;=> true

(= (__ '([1 2] [3 4] [5 6]) 2) [5 6])
;=> true
