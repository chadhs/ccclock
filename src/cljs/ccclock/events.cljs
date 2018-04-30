(ns ccclock.events
  (:require [re-frame.core  :as    re-frame]
            [ccclock.db     :as    db]
            [ccclock.config :as    config]
            [ajax.core      :refer [GET]]))


;; event dispatch
(defn dispatch-event-update-time
  []
  (let [now (js/Date.)]
    (re-frame/dispatch [::update-time now])))
(defonce do-time (js/setInterval dispatch-event-update-time 1000))


(re-frame/reg-event-db
 ::fetch-weather
 (fn
   [db _]
   (let [appid (get config/secrets :openweathermap-appid)
         zip   (str (get config/secrets :postal-code) "," (get config/secrets :country-code))]
     (GET
      "https://api.openweathermap.org/data/2.5/weather"
      {:params          {"zip"   zip
                         "units" "imperial"
                         "appid" appid}
       :handler         #(re-frame/dispatch [::update-weather %1])
       :response-format :json
       :keywords?       true}))))
(defonce do-weather (js/setInterval (re-frame/dispatch [::fetch-weather]) 900000))


(re-frame/reg-event-db
 ::fetch-forecast
 (fn
   [db _]
   (let [appid (get config/secrets :openweathermap-appid)
         zip   (str (get config/secrets :postal-code) "," (get config/secrets :country-code))]
     (GET
      "https://api.openweathermap.org/data/2.5/forecast"
      {:params          {"zip"   zip
                         "units" "imperial"
                         "appid" appid}
       :handler         #(re-frame/dispatch [::update-forecast %1])
       :response-format :json
       :keywords?       true}))))
(defonce do-forecast (js/setInterval (re-frame/dispatch [::fetch-forecast]) 900000))


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
