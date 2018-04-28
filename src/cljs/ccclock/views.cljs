(ns ccclock.views
  (:require [re-frame.core :as re-frame]
            [ccclock.subs :as subs]))


(defn display-time
  []
  (let [time-raw      @(re-frame/subscribe [::subs/time])
        time-segments (clojure.string/split (.toTimeString time-raw) ":")
        clock-time    (str (first time-segments) ":" (second time-segments))]
    clock-time))


(defn display-weather
  []
  (let [weather @(re-frame/subscribe [::subs/weather])]
    (if (not (empty? weather))
      (.toFixed (get-in weather [:main :temp]) 0)
      "NA")))


(defn main-panel []
  [:div
   [:div.time-display
    (display-time)]
   [:div.temp-display
    [:div.temp
     (display-weather)]]])
