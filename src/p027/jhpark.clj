(ns p027.jhpark)

(def a "racecar")
(def b '(1 2 3 4 5))
(def c [:foo :bar :foo])
(def d '(1 1 3 3 1 1))

(defn my-reverse
  [data]
  (if (string? data)
    (clojure.string/reverse data)
    (reverse data)))



(defn palindrome?
  [data]
  (= data (my-reverse data)))


(palindrome? a)