(ns karmalet.web
  (:use [karmalet db browserid social])
  (:use [compojure core route handler])
  (:use [ring.util json response])
  (:use [cheshire.core]))

(defn login [req]
  (let [assertion (-> req :params :assertion)
        result (verify-browserid assertion)]
    (if (= "okay" (:status result))
      (let [email (:email result)
            id-results (find-user email)
            id (if (empty? id-results)
                 (:GENERATED_KEY (save-user email))
                 (-> id-results first (get :id)))
            services (find-service-by-user id)]
        (assoc (json-response {:success true
                               :id id
                               :services services})
          :session {:id id}))
      (json-response {:success false}))))

(defn get-karma [req]
  (let [service (-> req :params :service)
        id (-> req :params :id)]
    (json-response {:service service
                    :id id
                    :karma (case service
                             "reddit" (reddit id)
                             "twitter" (twitter id)
                             "github" (github id)
                             "stackoverflow" (stackoverflow id)
                             "hackernews" (hackernews id))}) ))

(defn save-accounts [req]
  (let [user-id (-> req :session :id)
        services (-> req :params :services parse-string)]
    (add-service user-id services)))

(defroutes web-routes
  (GET "/" [] (redirect "index.html"))
  (POST "/login" [] login)
  (GET "/karma" [] get-karma)
  (POST "/accounts" [] save-accounts)
  (resources "/"))

(def app
  (site web-routes))

