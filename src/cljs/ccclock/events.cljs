(ns ccclock.events
  (:require [re-frame.core :as re-frame]
            [ajax.core :refer [GET]]
            [day8.re-frame.tracing :refer [fn-traced]])
  (:require [ccclock.db :as db]
            [ccclock.config :as config]))

;; init db
(re-frame/reg-event-db ::initialize-db (fn-traced [_ _] db/default-db))

;; event dispatch
(defn dispatch-event-update-time
  []
  (let [now (js/Date.)] (re-frame/dispatch [::update-time now])))
(defonce do-time (js/setInterval dispatch-event-update-time 1000))

(defn dispatch-event-update-weather
  []
  (let [request-url (if config/debug? ; running in dev mode
                      "http://localhost:8081/api/weather"
                      "/api/weather")]
    (GET request-url
         {:handler         #(re-frame/dispatch [::update-weather %1])
          :response-format :json
          :keywords?       true})))
(dispatch-event-update-weather); run once on load to populate state db
(defonce do-weather (js/setInterval dispatch-event-update-weather 300000))

;; event handling
(re-frame/reg-event-db ::update-time
                       (fn-traced [db [_ new-time]] (assoc db :time new-time)))

(re-frame/reg-event-db ::update-weather
                       (fn-traced [db [_ response]]
                                  (-> db
                                      (assoc :weather response))))
