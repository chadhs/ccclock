(ns ccclock.api.weather
  (:require [ccclock.config :as config])
  (:require [clj-http.client :as client]
            [slingshot.slingshot :refer [throw+ try+]] ; provided by clj-http
  ))
(defn fetch-weather
  "fetches weather data"
  []
  (let [apikey   (:openweather-api-key (config/config))
        api-url  "https://api.openweathermap.org/data/3.0/onecall"
        response (try+ (client/get api-url
                                   {:query-params {:lat   42.867
                                                   :lon   -88.333
                                                   :appid apikey}
                                    :headers      {"User-Agent" "CCClock/1.0"}})
                       (catch Object _
                         (throw+
                          ;; construct our own exception object for more
                          ;; control
                          (ex-info
                           "api connection error"
                           {:status 500
                            :error-message
                            (str "We had a problem connecting to the api, "
                                 "please try again. "
                                 "If the issue persists,contact support.")}))))]
    (:body response)))

(defn weather-handler
  [_]
  (let [weather-response (fetch-weather)]
    {:status  200
     :body    weather-response
     :headers {"Access-Control-Allow-Headers" "Content-Type"
               "Access-Control-Allow-Origin"  "*"}}))

(comment (fetch-weather))
