(ns p020.kep)


(defn foo [col n]
  (->> (count col)
       (mod n)
       (nth col)))


(def __
  (fn [col]
    (foo col -2)))


(= (__ (list 1 2 3 4 5)) 4)
;=> true

(= (__ ["a" "b" "c"]) "b")
;=> true

(= (__ [[1 2] [3 4]]) [1 2])
;=> true
