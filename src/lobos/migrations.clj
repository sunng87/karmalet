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
              (timestamp :nextupdate))))
  (down []
        (drop (table :kusers))))

(defmigration add-karma-table
  (up []
      (create 
       (table :karma (integer :id :primary-key :auto-inc)
              (integer :user_id :not-null)
              (varchar :service 20 :not-null)
              (integer :karma :not-null)
              (integer :karma_alt)))
      (index :karma [:user_id]))
  (down []
        (drop (table :karma))))


(defmigration add-karma-account-field
  (up []
      (alter :add
             (table :karma
                    (varchar :account 40)))))

(defmigration remove-karma-alt-field
  (up []
      (alter :drop
             (table :karma
                    (column :karma_alt)))))  

