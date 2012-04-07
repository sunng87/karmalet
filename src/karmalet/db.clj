(ns karmalet.db
  (:use [karmalet.vmc])
  (:use [korma core db]))

(defn- db-info []
  (if (vmc?)
    {:db (mysql-db)
     :port (mysql-port)
     :host (mysql-host)
     :user (mysql-user)
     :password (mysql-password)}
    {:db "karmalet"
     :port 3306
     :host "localhost"
     :user "karmalet"
     :password "karmalet"}))

(defdb conn (mysql (db-info)))

(defentity kusers)
(defentity karma)

(defn find-unupdated-100-users []
  (select kusers
          (fields [:id])
          (order :lastupdate :ASC)
          (limit 100)))

(defn find-user [email]
  (select kusers
          (fields [:id])
          (where {:email email})))

(defn save-user [email]
  (insert kusers (values {:email email})))

(defn find-service-by-user [id]
  (select karma
          (where {:user_id id})))

(defn update-service-karma [id k]
  (update karma
          (set-fields {:karma k})
          (where {:id id})))

(defn add-service [userid kv]
  (let [mk-data (fn [k v] {:user_id userid :service k :karma v})
        data (map #(apply mk-data %) kv)]
  (insert karma
          (values data))))



