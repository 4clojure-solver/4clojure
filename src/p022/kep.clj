(ns p022.kep)

(defn foo [col]
  (->> col
       (map (fn [_] 1))
       (reduce +)))

(def __ foo)

(= (__ '(1 2 3 3 1)) 5)

(= (__ "Hello World") 11)

(= (__ [[1 2] [3 4] [5 6]]) 3)

(= (__ '(13)) 1)

(= (__ '(:a :b :c)) 3)
