(ns ccclock.views
  (:require [re-frame.core :as re-frame]
            [ccclock.subs  :as subs]))


(defn main-panel
  "ccclock main display ui"
  []
  (let [time-data     @(re-frame/subscribe [::subs/time])
        weather-data  @(re-frame/subscribe [::subs/weather])
        time-h1       (get time-data :time-h1)
        time-h2       (get time-data :time-h2)
        time-min      (get time-data :time-min)
        time-h1-class (get time-data :time-h1-class)
        temp-current  (get weather-data  :temp-current)
        temp-high     (get weather-data :temp-high)
        temp-low      (get weather-data :temp-low)
        cond-current  (get weather-data  :cond-current)
        cond-3hour    (get weather-data :cond-3hour)
        cond-6hour    (get weather-data :cond-6hour)]
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
      [:i.wi {:class (str "wi-owm-" cond-3hour)}] " "
      [:i.wi {:class (str "wi-owm-" cond-6hour)}]]]))
