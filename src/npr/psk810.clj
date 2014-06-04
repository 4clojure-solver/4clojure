(ns npr.psk810)

(defn f [a v]
  (map #(conj a %)
       (apply (partial disj (set v)) a)))

(defn g [v]
  (loop [acc [] sub (f [] v)]
    (if (= (count v) (count (first sub)))
      (concat acc sub)
      (recur (concat acc sub)
             (mapcat #(f % v) sub)))))


(g [1 2 3])
(g [:a :b :c :d :e :f :g])
