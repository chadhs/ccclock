(ns ccclock.views
  (:require [re-frame.core :as re-frame]
            [ccclock.subs  :as subs]))


(defn main-panel
  "ccclock main display ui"
  []
  (let [time-data          @(re-frame/subscribe [::subs/time])
        weather-data  @(re-frame/subscribe [::subs/weather])
        forecast-data @(re-frame/subscribe [::subs/forecast])
        time-h1       (get time-data :time-h1)
        time-h2       (get time-data :time-h2)
        time-min      (get time-data :time-min)
        time-h1-class (get time-data :time-h1-class)
        temp-current  (get weather-data  :temp-current)
        temp-high     (get forecast-data :temp-high)
        temp-low      (get forecast-data :temp-low)
        cond-current  (get weather-data  :cond-current)
        cond-3h       (get forecast-data :cond-3h)
        cond-6h       (get forecast-data :cond-6h)]
    [:div
     [:div.time-display
      [:text {:class time-h1-class} time-h1] [:text.time-rest time-h2 ":" time-min]]
     [:div.temp-display
      [:div.temp
       temp-current "°"]
      [:div.hi-low
       temp-high"°" "/" temp-low "°"]]
     [:div.cond-display
      [:i.wi {:class (str "wi-owm-" cond-current)}] " "
      [:i.wi {:class (str "wi-owm-" cond-3h)}] " "
      [:i.wi {:class (str "wi-owm-" cond-6h)}]]]))
