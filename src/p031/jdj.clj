(ns p031.jdj)
;; 4Clojure Question 31
;;
;; Write a function which packs consecutive duplicates into sub-lists.
;;
;; Use M-x 4clojure-check-answers when you're done!


(defn __ [lst]
  (let [f (fn [acc e]
            (let [e' (first (last acc))]
              (if (= e e')
                (concat (butlast acc) (list (list e (inc (second (last acc))))))
                (concat acc (list (list e 1))))))]
    (->> (reduce f '() lst)
         (map (fn [[e i]] (repeat i e))))))





(= (__ [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))

(= (__ [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))

(= (__ [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))
