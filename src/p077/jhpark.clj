(ns p077.jhpark)



(def source ["meat" "mat" "team" "mate" "eat"] )

(defn sort-string
  [string]
  (reduce str (sort string)))

(defn to-map
  ;;return {:eat "aet", :mate "aemt", :team "aemt", :mat "amt", :meat "aemt"}
  []
  (let [bb {}
        aa (for [a source]
             (assoc  bb (keyword  a) (sort-string a)))]
    (reduce conj aa)))



(let [source2 (to-map)
      aa      (doseq [val (distinct (vals (to-map)))]

                )] )


(def c  {:eat "aet", :mate "aemt", :team "aemt", :mat "amt", :meat "aemt"} )


(doseq [val (distinct (vals (to-map)))]

  )
(map #(some #{%} (map val c))(distinct (vals c)))


(keep #(some (set %) (map val c) )  (distinct (vals c)))

(keep #(if (#(some #{%} (map val c)))  ) (distinct (vals c)))



