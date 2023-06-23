(ns ccclock.views.time
  (:require [re-frame.core :as re-frame])
  (:require [ccclock.subs :as subs]))

(defn time-panel
  []
  (let [time-data     @(re-frame/subscribe [::subs/time])
        time-h1       (:time-h1 time-data)
        time-h2       (:time-h2 time-data)
        time-min      (:time-min time-data)
        time-h1-class (:time-h1-class time-data)]
    [:div.time-display
     [:text {:class time-h1-class}
      time-h1]
     [:text.time-rest time-h2 ":" time-min]]))
