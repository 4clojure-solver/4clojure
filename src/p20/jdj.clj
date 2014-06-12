(defn mytest [f]
  (every? true?
   [(= (f (list 1 2 3 4 5)) 4)
    (= (f ["a" "b" "c"]) "b")
    (= (f [[1 2] [3 4]]) [1 2])
    ]))



(defn second-last [lst]
  (-> lst
      reverse
      second))

(defn second-last [lst]
  (-> lst
      vec
      pop
      peek))

(defn second-last [lst]
  (-> lst
      butlast
      last))


(mytest second-last)
