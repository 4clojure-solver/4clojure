;; 4Clojure Question 22
;;
;; Write a function which returns the total number of elements in a sequence.
;;
;; Restrictions (please don't use these function(s)): count
;;
;; Use M-x 4clojure-check-answers when you're done!


(def __ (fn [lst]
          (loop [i 0 lst lst]
            (if (seq lst)
              (recur (inc i) (rest lst))
              i)))

(fn [coll]
  (loop [c 0 col coll]
    (if (empty? col)
      c
      (recur (inc c) (rest col)))))



(= (__ '(1 2 3 3 1)) 5)

(= (__ "Hello World") 11)

(= (__ [[1 2] [3 4] [5 6]]) 3)

(= (__ '(13)) 1)

(= (__ '(:a :b :c)) 3)
