(ns p029.jhpark)

(def a "HelloAff")
(def b "$#A(*&987Zf")


(apply str (re-seq #"[A-Z]" b))

