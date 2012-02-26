(ns karmalet.vmc
  (:use [cheshire.core]))

(defn get-env [key]
  (System/getenv key))

(defn mongo-config [key]
  (if-let [services (get-env "VCAP_SERVICES")]
    (let [services-dict (parse-string services false)]
      (-> services-dict
          (get "mongodb-1.8")
          first
          (get "credentials")
          (get key)))))

(defn vmc? []
  (not (nil? (get-env "VCAP_SERVICES"))))
