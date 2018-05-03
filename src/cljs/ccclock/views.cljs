(ns ccclock.views
  (:require [re-frame.core :as re-frame]
            [ccclock.subs  :as subs]))


(defn format-time-data
  "provides the current time data for the ui"
  []
  (let [time-raw      @(re-frame/subscribe [::subs/time])
        time-segments (clojure.string/split (.toTimeString time-raw) ":")
        hour->int     (js/parseInt (first time-segments))
        hour          (if (> hour->int 12) (- hour->int 12) hour->int)
        minutes       (second time-segments)
        clock-time    (str hour ":" minutes)]
    clock-time))


(defn format-weather-data
  "provides a map of current weather and forecast data"
  []
  (let [weather @(re-frame/subscribe [::subs/weather])]
    (if (empty? weather)
      "NA"
      (let [temp-current (.toFixed (get-in weather [:currently :apparentTemperature]) 0)
            temp-high    (.toFixed (get-in weather [:daily :apparentTemperatureHigh]) 0)
            temp-low     (.toFixed (get-in weather [:daily :apparentTemperatureLow]) 0)
            cond-current (get-in weather [:currently :icon])
            forecast     (get weather :daily)
            cond-3hour   (get (nth forecast 3) :icon)
            cond-6hour   (get (nth forecast 6) :icon)]
        {:temp-current temp-current
         :temp-high    temp-high
         :temp-low     temp-low
         :cond-current cond-current
         :cond-3hour   cond-3hour
         :cond-6hour   cond-6hour}))))


(defn main-panel
  "ccclock main display ui"
  []
  (let [time-data     (format-time-data)
        weather-data  (format-weather-data)
        temp-current  (get weather-data :temp-current)
        temp-high     (get weather-data :temp-high)
        temp-low      (get weather-data :temp-low)
        cond-current  (get weather-data :cond-current)
        cond-3hour    (get weather-data :cond-3hour)
        cond-6hour    (get weather-data :cond-6hour)]
    [:div
     [:div.time-display
      time-data]
     [:div.temp-display
      [:div.temp
       temp-current "°"]
      [:div.hi-low
       temp-high"°" "/" temp-low "°"]]
     [:div.cond-display
      [:i.wi {:class (str "wi-forecast-io-" cond-current)}] " "
      [:i.wi {:class (str "wi-forecast-io-" cond-3hour)}] " "
      [:i.wi {:class (str "wi-forecast-io-" cond-6hour)}]]]))
