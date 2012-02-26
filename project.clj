(defproject karmalet "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "1.0.1"]
                 [clj-http "0.3.2"]
                 [cheshire "2.2.0"]
                 [korma "0.3.0-beta4"]
                 [com.rabbitmq/amqp-client "2.7.1"]
                 [clj-time "0.3.7"]
                 [mysql/mysql-connector-java "5.1.8"]
                 [lobos "1.0.0-SNAPSHOT"]]
  :dev-dependencies [[lein-ring "0.5.4"]
                     [lein-lobos "0.8.0-SNAPSHOT"]])

