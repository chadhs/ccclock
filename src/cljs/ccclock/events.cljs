(ns ccclock.events
  (:require [re-frame.core  :as    re-frame]
            [ccclock.db     :as    db]
            [ccclock.config :as    config]
            [ajax.core      :refer [GET]]))


;; event dispatch
(defn dispatch-event-update-time []
  (let [now (js/Date.)]
    (re-frame/dispatch [::update-time now])))
(defonce do-time (js/setInterval dispatch-event-update-time 1000))


(defn dispatch-event-update-weather []
  (let [appid     (get config/secrets :openweathermap-appid)
        latitude  (get config/secrets :latitude)
        longitude (get config/secrets :longitude)]
    (GET
     "https://api.openweathermap.org/data/2.5/weather"
     {:params          {"lat"   latitude
                        "lon"   longitude
                        "units" "imperial"
                        "appid" appid}
      :handler         #(re-frame/dispatch [::update-weather %1])
      :response-format :json
      :keywords?       true})))
(dispatch-event-update-weather) ; run once on load to populate state db
(defonce do-weather (js/setInterval dispatch-event-update-weather 300000))


(defn dispatch-event-update-forecast []
  (let [appid     (get config/secrets :openweathermap-appid)
        latitude  (get config/secrets :latitude)
        longitude (get config/secrets :longitude)]
    (GET
     "https://api.openweathermap.org/data/2.5/forecast"
     {:params          {"lat"   latitude
                        "lon"   longitude
                        "units" "imperial"
                        "appid" appid}
      :handler         #(re-frame/dispatch [::update-forecast %1])
      :response-format :json
      :keywords?       true})))
(dispatch-event-update-forecast) ; run once on load to populate state db
(defonce do-forecast (js/setInterval dispatch-event-update-forecast 300000))


;; event handling
(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


(re-frame/reg-event-db
 ::update-time
 (fn [db [_ new-time]]
   (assoc db :time new-time)))


(re-frame/reg-event-db
 ::update-weather
 (fn
   [db [_ response]]
   (-> db
       (assoc :weather response))))


(re-frame/reg-event-db
 ::update-forecast
 (fn
   [db [_ response]]
   (-> db
       (assoc :forecast response))))
