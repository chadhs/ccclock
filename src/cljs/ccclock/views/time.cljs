(ns ccclock.views.time
  (:require [re-frame.core :as re-frame]
            [ccclock.subs :as subs]))

(defn time-panel
  []
  (let [time-data     @(re-frame/subscribe [::subs/time])
        time-h1       (get time-data :time-h1)
        time-h2       (get time-data :time-h2)
        time-min      (get time-data :time-min)
        time-h1-class (get time-data :time-h1-class)]
    [:div.time-display
     [:text {:class time-h1-class}
      time-h1]
     [:text.time-rest time-h2 ":" time-min]]))
