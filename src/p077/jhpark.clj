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

(->> {:a "bar" :b "foo" :c "bar" :d "baz"} ; initial map
     (group-by val)   ; sorted into a new map based on value of each key
     (#(get % "bar")) ; extract the entries that had value "bar"
     (map key))     ; get the keys that had value bar
(:a :c)
