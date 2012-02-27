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
              (timestamp :nextupdate)))
      (index :kusers [:email])
      (index :kusers [:nextupdate]))
  (down []
        (drop (table :kusers))))

(defmigration add-karma-table
  (up []
      (create 
       (table :karma (integer :id :primary-key :auto-inc)
              (integer :user_id :not-null)
              (integer :service :not-null)
              (integer :karma :not-null)
              (integer :karma_alt)))
      (index :karma [:user_id]))
  (down []
        (drop (table :karma))))

(defmigration rename-user-table-timestamp
  (up []
      (alter
       (table :kusers
              (column :nextupdate :to :lastupdate))))
  (down []
        (alter
         (table :kusers
                (column :lastupdate :to :nextupdate)))))
  

