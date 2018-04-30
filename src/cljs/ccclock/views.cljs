(ns ccclock.views
  (:require [re-frame.core :as re-frame]
            [ccclock.subs  :as subs]))


(defn format-time-data
  "provides the current time data for the ui"
  []
  (let [time-raw      @(re-frame/subscribe [::subs/time])
        time-segments (clojure.string/split (.toTimeString time-raw) ":")
        clock-time    (str (first time-segments) ":" (second time-segments))]
    clock-time))


(defn format-weather-data
  "provides a map of current weather data for the ui"
  []
  (let [weather @(re-frame/subscribe [::subs/weather])]
    (if (empty? weather)
      "NA"
      (let [temp-current (.toFixed (get-in weather [:main :temp]) 0)
            cond-current (get (first (get weather :weather)) :id)]
        {:temp-current temp-current
         :cond-current cond-current}))))


;;; will need to fetch icons for conds to display real weather icons
(defn format-forecast-data
  "provides a map of forecast data for the ui"
  []
  (let [forecast       @(re-frame/subscribe [::subs/forecast])]
    (if (empty? forecast)
      "NA"
      (let [forecast-today (take 3 (get forecast :list))
            temp-high      (.toFixed (apply max (map #(get-in % [:main :temp_max]) forecast-today)) 0)
            temp-low       (.toFixed (apply min (map #(get-in % [:main :temp_min]) forecast-today)) 0)
            conds-today    (map #(get (first (% :weather)) :id) forecast-today)
            cond-3h        (first conds-today)
            cond-6h        (second conds-today)]
        {:temp-high temp-high
         :temp-low  temp-low
         :cond-3h   cond-3h
         :cond-6h   cond-6h}))))


(defn main-panel
  "ccclock main display ui"
  []
  (let [time-data     (format-time-data)
        weather-data  (format-weather-data)
        forecast-data (format-forecast-data)
        temp-current  (get weather-data :temp-current)
        temp-high     (get forecast-data :temp-high)
        temp-low      (get forecast-data :temp-low)
        cond-current  (get weather-data :cond-current)
        cond-3h       (get forecast-data :cond-3h)
        cond-6h       (get forecast-data :cond-6h)]
    [:div
     [:div.time-display
      time-data]
     [:div.temp-display
      [:div.temp
       temp-current "°"]
      [:div.hi-low
       temp-high"°" "/" temp-low "°"]]
     [:div.cond-display
      [:i.wi {:class (str "wi-owm-" cond-current)}] " "
      [:i.wi {:class (str "wi-owm-" cond-3h)}] " "
      [:i.wi {:class (str "wi-owm-" cond-6h)}]]]))
