(ns karmalet.vmc
  (:use [cheshire.core]))

(defn get-env [key]
  (System/getenv key))

(defn- vmc-service-config [service key]
  (if-let [services (get-env "VCAP_SERVICES")]
    (let [services-dict (parse-string services)]
      (-> services-dict
          (get service)
          first
          (get :credentials)
          (get key))))))

(def mongo-config
  (partial vmc-service-config :mongodb-1.8))

(defn mongo-db []
  (mongo-config :db))
(defn mongo-host []
  (mongo-config :hostname))
(defn mongo-port []
  (mongo-config :port))
(defn mongo-user []
  (mongo-config :username))
(defn mongo-passwd []
  (mongo-config :password))

(def redis-config
  (partial vmc-service-config :redis-2.2))

(defn redis-password []
  (redis-config :password))
(defn redis-host []
  (redis-config :hostname))
(defn redis-port []
  (redis-config :port))

(def mysql-config
  (partial vmc-service-config :mysql-5.1))
(defn mysql-host []
  (mysql-config :hostname))
(defn mysql-port []
  (mysql-config :port))
(defn mysql-user []
  (mysql-config :user))
(defn mysql-password []
  (mysql-config :password))

(def rabbitmq-config
  (partial vmc-service-config :rabbitmq-2.4))
(def rabbitmq-url []
  (rabbitmq-config :url))

(defn vmc? []
  (not (nil? (get-env "VMC_APP_NAME"))))
