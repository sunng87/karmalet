(ns karmalet.web
  (:use [karmalet vmc browserid])
  (:use [compojure core route handler])
  (:use [ring.util.json]))

(defn login [req]
  (let [assertion (-> req :params :assertion)
        result (verify-browserid assertion)]
    (if (= "okay" (:status result))
      (json-response {:result "success"})
      (json-response {:result "failed"}))))

(defroutes web-routes
  (POST "/login" [] login)
  (resources "/"))

(def app
  (site web-routes))

