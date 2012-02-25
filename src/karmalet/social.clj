(ns karmalet.social
  (:require [clj-http.client :as client]))

(defn- get-json [url]
  (let [resp  (client/get url {:as :json} )]
    (if (= 200 (:status resp))
      (:body resp)
      nil)))

(defn- get-page [url]
  (let [resp (client/get url)]
    (if (= 200 (:status resp))
      (:body resp)
      nil)))

(defmacro defsocial [fn-name url-pattern callback]
  `(defn ~fn-name [userid#]
     (let [url# (format ~url-pattern userid#)
           resp# (get-json url#)]
       (if-not (nil? resp#)
         (~callback resp#)))))

(defsocial reddit-karma
  "http://www.reddit.com/user/%s/about.json"
  (fn [r]
    (-> r
        :data
        :link_karma)))

(defsocial twitter-followers
  "https://api.twitter.com/1/statuses/user_timeline.json?screen_name=%s&count=1"
  (fn [r]
    (-> r
        first
        :user
        :followers_count)))

(defsocial github-followers
  "https://api.github.com/users/%s"
  (fn [r]
    (-> r
        :followers)))

(defsocial stackoverflow-karma
  "http://api.stackoverflow.com/1.1/users/%s"
  (fn [r]
    (-> r
        :users
        first
        :reputation)))

(defsocial hackernews-karma
  "http://api.ihackernews.com/profile/%s"
  (fn [r]
    (-> r
        :karma)))


