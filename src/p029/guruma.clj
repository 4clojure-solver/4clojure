(ns p029.guruma)

(defn __ [s]
  (apply str
         (filter #(Character/isUpperCase %) (seq s))))

(= (__ "HeLlO, WoRlD!") "HLOWRD")

(empty? (__ "nothing"))

(= (__ "$#A(*&987Zf") "AZ")


