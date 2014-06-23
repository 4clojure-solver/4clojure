(ns p029.kep)

(defn get-caps [str]
  (->> str
       (re-seq #"[A-Z]")
       clojure.string/join))


(def __ get-caps)


(= (__ "HeLlO, WoRlD!") "HLOWRD")
(empty? (__ "nothing"))
(= (__ "$#A(*&987Zf") "AZ")
