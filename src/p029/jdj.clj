(ns p029.jdj)
;; 4Clojure Question 29
;;
;; Write a function which takes a string and returns a new string containing only the capital letters.
;;
;; Use M-x 4clojure-check-answers when you're done!



(defn filter-UPPERCASE [s]
  (apply str (filter #(Character/isUpperCase %) s)))




(def __ filter-UPPERCASE)

(= (__ "HeLlO, WoRlD!") "HLOWRD")

(empty? (__ "nothing"))

(= (__ "$#A(*&987Zf") "AZ")
