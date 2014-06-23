(ns p027.guruma)

(defn __ [v]
  (= (seq v) (reverse v)))


;; testcase-1
(false? (__ '(1 2 3 4 5)))

;; testcase-2
(true? (__ "racecar"))

;; testcase-3
(true? (__ [:foo :bar :foo]))

;; testcase-4
(true? (__ '(1 1 3 3 1 1)))

;; testcase-5
(false? (__ '(:a :b :c)))
