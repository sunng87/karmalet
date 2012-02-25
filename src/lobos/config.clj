(ns lobos.config
  (:use [lobos.connectivity]))


(def db
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//localhost:3306/karmalet"
   :user "karmalet"
   :password "karmalet"})


