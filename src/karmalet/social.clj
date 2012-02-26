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

(defsocial reddit
  "http://www.reddit.com/user/%s/about.json"
  (fn [r]
    (-> r
        :data
        :link_karma)))
(defn reddit-page [userid]
  (format "http://www.reddit.com/user/%s" userid))

(defsocial twitter
  "https://api.twitter.com/1/statuses/user_timeline.json?screen_name=%s&count=1"
  (fn [r]
    (-> r
        first
        :user
        :followers_count)))
(defn twitter-page [userid]
  (format "https://twitter.com/%s" userid))

(defsocial github
  "https://api.github.com/users/%s"
  (fn [r]
    (-> r
        :followers)))
(defn github-page [userid]
  (format "https://github.com/%s" userid))

(defsocial stackoverflow
  "http://api.stackoverflow.com/1.1/users/%s"
  (fn [r]
    (-> r
        :users
        first
        :reputation)))
(defn stackoverflow-page [userid]
  (format "http://stackoverflow.com/users/%s" userid))

(defsocial hackernews
  "http://api.ihackernews.com/profile/%s"
  (fn [r]
    (-> r
        :karma)))
(defn hackernews-page [userid]
  (format "http://news.ycombinator.com/user?id=%s" userid))


