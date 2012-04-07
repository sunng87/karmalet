(defproject karmalet "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "1.0.2"]
                 [ring-json-response "0.1.0"]
                 [clj-http "0.3.6"]
                 [korma "0.2.1"]
                 [com.mefesto/wabbitmq "0.2.0"]
                 [clj-time "0.3.7"]
                 [mysql/mysql-connector-java "5.1.18"]
                 [lobos "1.0.0-SNAPSHOT"]]
  :dev-dependencies [[lein-ring "0.6.3"]
                     [lein-lobos "0.8.0-SNAPSHOT"]]
  :ring {:handler karmalet.web/app})

