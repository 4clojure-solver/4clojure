(ns p028.jdj)
;; 4Clojure Question 28
;;
;; Write a function which flattens a sequence.
;;
;; Restrictions (please don't use these function(s)): flatten
;;
;; Use M-x 4clojure-check-answers when you're done!


(defn my-flatten [lst]
  (let [s1 (take-while (comp not sequential?) lst)
        s2 (drop-while (comp not sequential?) lst)]
    (if (not (seq s2))
      s1
      (recur (concat s1 (first s2) (rest s2))))))




(defn split-at-with [lst f]
  (loop [s1 [] s2 lst]
    (if (or (empty? s2) (f (first s2)))
      [s1 s2]
      (recur (conj s1 (first s2)) (rest s2)))))

(defn my-flatten2 [lst]
  (let [[s1 s2] (split-at-with lst sequential?)]
    (if (empty? s2)
      s1
      (recur (concat s1 (first s2) (rest s2))))))





(def __ my-flatten2)

(= (__ '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))

(= (__ ["a" ["b"] "c"]) '("a" "b" "c"))

(= (__ '((((:a))))) '(:a))
