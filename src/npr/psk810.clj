(ns npr.psk810
  (:import org.apache.commons.codec.binary.Base64)
  (:require [clojure.data.codec.base64 :as b64]
            [pandect.core :as pd]))

(defn base64-encode [s]
  (b64/encode (.getBytes s)))

(defn base64-decode [s]
  (String. (b64/decode s)))

(base64-decode (base64-encode "abcd"))


(def prices
  {:price-ex  {:ko {:USD 1.00 :KRW 1100}
               :en {:USD 1.00 :MXN  300}}
   :price-2   {:en {:USD 3.00}}
   :price-3   {:en {:USD 9.00}}
   :price-4   {:en {:USD 19.00}}
   :price-5   {:en {:USD 39.00}}
   :price-6   {:en {:USD 59.00}}
   :price-7   {:en {:USD 99.00}}
   :price-8   {:en {:USD 199.00}}
   :price-9   {:en {:USD 299.00}}
   })

(def products [
  ; :name  :category   :image :description  :price-id   :chips]
  ["name" "category"  "url"  "description"  :price-2    750000]
  ["name" "category"  "url"  "description"  :price-3    3600000]
  ["name" "category"  "url"  "description"  :price-4    9500000]
  ["name" "category"  "url"  "description"  :price-5    27300000]
  ["name" "category"  "url"  "description"  :price-6    64900000]
  ["name" "category"  "url"  "description"  :price-7    198000000]
  ["name" "category"  "url"  "description"  :price-8    497500000]
  ["name" "category"  "url"  "description"  :price-9    897000000]
   ])


(defn pc [products prices locale currency]
  (for [[_ _ _ _ price-id chips] products
        :let [price (get-in prices [price-id locale currency])]]
    [price chips]
  ))

(clojure.pprint/pprint
 (pc products prices :en :USD))

(defn g [v]
  (loop [acc [] sub (f [] v)]
    (if (= (count v) (count (first sub)))
      (concat acc sub)
      (recur (concat acc sub)
             (mapcat #(f % v) sub)))))


(g [1 2 3])
(g [:a :b :c :d :e :f :g])

