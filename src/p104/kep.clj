(ns p104.kep)


(defn char->rom-sym [char]
  (-> char (str) (keyword)))


(defn rom-sym->num [rom-sym]
  (-> {:I 1
       :V 5
       :X 10
       :L 50
       :C 100
       :D 500
       :M 1000}
      (get rom-sym)))


(defn rom-num->num-seq [rom-num]
  (->> rom-num
       (map char->rom-sym)
       (map rom-sym->num)))


(defn foo [rom-num]
  (let [num-seq (rom-num->num-seq rom-num)
        rev-seq (reverse num-seq)]

    (->> (partition 2 1 (conj rev-seq 0))
         (map (fn [[a b]] (if (> a b) - +)))
         (map (fn [x f] (f x)) rev-seq)
         (reduce +))))

;; 2
(defn n->nseq [n]
  (-> {1 [1]
       2 [1 1]
       3 [1 1 1]
       4 [1 5]
       5 [5]
       6 [5 1]
       7 [5 1 1]
       8 [5 1 1 1]
       9 [1 10]
       10 [10]}
      (get n)))


(defn num->rom [num]
  (-> {1 "I"
       5 "V"
       10 "X"
       50 "L"
       100 "C"
       500 "D"
       1000 "M"}
      (get num)))


(defn char->int [c]
  (- (int c) (int \0)))


(defn foo [num]
  (->> (str num)
       (map char->int)
       (map n->nseq)
       (reverse)
       (map-indexed (fn [i seq]
                      (map #(int (* (Math/pow 10 i) %)) seq)))
       (reverse)
       (flatten)
       (map num->rom)
       (apply str)))


(fn [num]
  (letfn [(n->nseq [n]
            (-> {1 [1]
                 2 [1 1]
                 3 [1 1 1]
                 4 [1 5]
                 5 [5]
                 6 [5 1]
                 7 [5 1 1]
                 8 [5 1 1 1]
                 9 [1 10]
                 10 [10]}
                (get n)))


          (num->rom [num]
            (-> {1 "I"
                 5 "V"
                 10 "X"
                 50 "L"
                 100 "C"
                 500 "D"
                 1000 "M"}
                (get num)))


          (char->int [c]
            (- (int c) (int \0)))]
    (->> (str num)
         (map char->int)
         (map n->nseq)
         (reverse)
         (map-indexed (fn [i seq]
                        (map #(int (* (Math/pow 10 i) %)) seq)))
         (reverse)
         (flatten)
         (map num->rom)
         (apply str))))
