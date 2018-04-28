(ns ccclock.events
  (:require [re-frame.core :as re-frame]
            [ccclock.db :as db]
            [ccclock.config :as config]
            [ajax.core :refer [GET]]))


;; event dispatch
(defn dispatch-event-update-time
  []
  (let [now (js/Date.)]
    (re-frame/dispatch [::update-time now])))
(defonce do-time (js/setInterval dispatch-event-update-time 1000))


(re-frame/reg-event-db
 ::weather
 (fn
   [db _]
   (let [openweathermap-appid (get config/secrets :openweathermap-appid)]
     (GET
      "https://api.openweathermap.org/data/2.5/weather"
      {:params {"zip" "53149,us"
                "units" "imperial"
                "appid" openweathermap-appid}
       :handler       #(re-frame/dispatch [::process-weather %1])
       :error-handler #(re-frame/dispatch [::bad-response %1])
       :response-format :json
       :keywords? true
       }))))
(defonce do-weather (js/setInterval (re-frame/dispatch [::weather]) 3600000))


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
 ::process-weather
 (fn
   [db [_ response]]
   (-> db
       (assoc :weather response))))


(re-frame/reg-event-db
 ::bad-response
 (fn
   [db [_ response]]
   (-> db
       (assoc :weather {:error "baz"}))))
