(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]]
               core
               schema
               config)))

(defmigration add-user-table
  (up []
      (create 
       (table :kusers (integer :id :primary-key :auto-inc)
              (varchar :email 100 :unique)
              (timestamp :next-update)))
      (index :kusers [:email])
      (index :kusers [:next-update]))
  (down []
        (drop (table :kusers))))

(defmigration add-karma-table
  (up []
      (create 
       (table :karma (integer :id :primary-key :auto-inc)
              (integer :user-id :not-null)
              (integer :service :not-null)
              (integer :karma :not-null)
              (integer :karma-alt)))
      (index :karma [:user-id]))
  (down []
        (drop (table :karma))))
  

