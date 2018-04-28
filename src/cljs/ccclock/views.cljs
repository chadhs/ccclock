(ns ccclock.views
  (:require [re-frame.core :as re-frame]
            [ccclock.subs :as subs]))


(defn display-time
  []
  (let [time-raw      @(re-frame/subscribe [::subs/time])
        time-segments (clojure.string/split (.toTimeString time-raw) ":")
        clock-time    (str (first time-segments) ":" (second time-segments))]
    [:div.example-clock
     clock-time]))


(defn main-panel []
  [:div.time-display
   [display-time]])
