;; Get the Caps
(fn [s]
  (apply str (filter #(Character/isUpperCase %) s)))
