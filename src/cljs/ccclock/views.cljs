(ns ccclock.views
  (:require [re-frame.core :as re-frame]
            [ccclock.subs  :as subs]))


(defn time-data
  "provides the current time data for the ui"
  []
  (let [time-raw      @(re-frame/subscribe [::subs/time])
        time-segments (clojure.string/split (.toTimeString time-raw) ":")
        clock-time    (str (first time-segments) ":" (second time-segments))]
    clock-time))


(defn weather-current-data
  "provides a map of current weather data for the ui"
  []
  (let [weather @(re-frame/subscribe [::subs/weather])]
    (if (empty? weather)
      "NA"
      (let [temp-current (.toFixed (get-in weather [:main :temp]) 0)
            temp-max     (.toFixed (get-in weather [:main :temp_max]) 0)
            temp-min     (.toFixed (get-in weather [:main :temp_min]) 0)]
        {:temp-current temp-current
         :temp-high    temp-max
         :temp-low     temp-min}))))


(defn main-panel
  "ccclock main display ui"
  []
  (let [temp-current (get (weather-current-data) :temp-current)
        temp-high    (get (weather-current-data) :temp-high)
        temp-low     (get (weather-current-data) :temp-low)]
    [:div
     [:div.time-display
      (time-data)]
     [:div.temp-display
      [:div.temp
       temp-current "°"]
      [:div.hi-low
       temp-high"°" "/" temp-low "°"]]
     [:div.cond-display
      [:i.wi.wi-day-rain] " " [:i.wi.wi-day-sunny] " " [:i.wi.wi-day-cloudy]]]))
