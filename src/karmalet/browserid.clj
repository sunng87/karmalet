(ns karmalet.browserid
  (:use [karmalet.vmc])
  (:use [clj-http.client :only [post]]))

(def browserid-verify-url "https://browserid.org/verify")
(def audience
  (if (vmc?)
    "http://karmalet.cloudfoundry.com/"
    "http://localhost:3000/"))

(defn verify [assertion]
  (let [data {:assertion assertion
              :audience audience}
        result (post browserid-verify-url                     
                     {:body (format "assertion=%s&audience=%s"
                                    assertion
                                    audience)
                      :content-type "application/x-www-form-urlencoded"
                      :as :json})]
    (:body result)))


