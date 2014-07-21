(ns p039.manmyung)

(defn __ [a b]
  (apply concat (map list a b)))

(__ [1 2 3] [:a :b :c])

(= (__ [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))
