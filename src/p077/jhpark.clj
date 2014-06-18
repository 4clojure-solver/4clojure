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



(let [source2 (to-map) ;{:eat "aet", :mate "aemt", :team "aemt", :mat "amt", :meat "aemt"}
      aa      (for [val (distinct (vals (to-map)))]
                (filter (comp #{val} source2) (keys source2)))]
  aa)


