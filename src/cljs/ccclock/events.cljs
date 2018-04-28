(ns ccclock.events
  (:require [re-frame.core :as re-frame]
            [ccclock.db :as db]
            [ajax.core :refer [GET]]))


;; event dispatch
(defn dispatch-event-update-time
  []
  (let [now (js/Date.)]
    (re-frame/dispatch [::update-time now])))
(defonce do-time (js/setInterval dispatch-event-update-time 1000))


;; event handling
(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


(re-frame/reg-event-db
 ::update-time
 (fn [db [_ new-time]]
   (assoc db :time new-time)))
