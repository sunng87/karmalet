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
              (integer :service :not-null)
              (integer :karma :not-null)
              (integer :karma_alt)))
      (index :karma [:user_id]))
  (down []
        (drop (table :karma))))




(defmigration create-test-table
  (up []
      (create
       (table :kk
              (integer :hello))))
  (down []
        (drop (table :kk))))

(defmigration rename-test-table
  (up []
      (alter :rename
             (table :kk
                    (column :hello :to :world))))
  (down []
        (alter :rename
               (table :kk
                      (column :world :to :hello)))))


  

