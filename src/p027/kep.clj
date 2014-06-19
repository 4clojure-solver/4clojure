(ns p027.kep)

(defn palidrome [col]
  (= (seq col) (reverse col)))

(def __ palidrome)

(false? (__ '(1 2 3 4 5)))
(true? (__ "racecar"))
(true? (__ [:foo :bar :foo]))
(true? (__ '(1 1 3 3 1 1)))
(false? (__ '(:a :b :c)))
