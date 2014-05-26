(ns p077.jhpark)



(def source ["meat" "mat" "team" "mate" "eat"] )

(defn sort-string
  [string]
  (reduce str (sort string)))

(defn to-map
  []
  (let [bb {}
        aa (for [a source]
             (assoc  bb (keyword  a) (sort-string a)))]
    (reduce conj aa)))


(let [source2 (to-map)
      aa      (doseq [val (distinct (vals (to-map)))]

                )] )


(def c  {:eat "aet", :mate "aemt", :team "aemt", :mat "amt", :meat "aemt"} )


(filter #(= %  ~~ how to..) (distinct  (vals c) ))


