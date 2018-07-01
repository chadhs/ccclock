(ns ccclock.handler
  (:require [ring.util.response :as response]
            [environ.core       :as environ]
            [clj-http.client    :as client]))


(defn handle-index [req]
  ;; NOTE: this will deliver your index.html
  (-> (response/resource-response "index.html" {:root "public"})
      (response/content-type "text/html")))


(defn handle-proxy [req]
  (let [weather-url    "https://api.darksky.net/forecast"
        weather-apikey (environ/env :weather-apikey)
        lat-lon        (environ/env :lat-lon)
        result         (client/get
                        (str weather-url "/"
                             weather-apikey "/"
                             lat-lon))]
    {:status  200
     :headers {"Access-Control-Allow-Origin" "*"
               "Access-Control-Allow-Headers" "Content-Type"
               "Content-Type" "application/json"}
     :body    (get result :body)}))
