(ns ccclock.views.weather
  (:require [re-frame.core :as re-frame])
  (:require [ccclock.subs :as subs]))

(defn weather-panel
  []
  (let [weather-data @(re-frame/subscribe [::subs/weather])
        temp-current (:temp-current weather-data)
        temp-high    (:temp-high weather-data)
        temp-low     (:temp-low weather-data)
        night?       (:night? weather-data)
        cond-current (:cond-current weather-data)
        cond-3hour   (:cond-3hour weather-data)
        cond-6hour   (:cond-6hour weather-data)]
    [:div
     [:div.temp-display
      [:div.temp temp-current "°"]
      [:div.hi-low temp-high "°" "/" temp-low "°"]]
     [:div.cond-display
      [:i.wi {:class (str "wi-owm-"
                      (if night? "night-" "day-")
                      cond-current
                      " wi-fw")}]
      " "
      [:i.wi {:class (str "wi-owm-"
                      (if night? "night-" "day-")
                      cond-3hour
                      " wi-fw")}]
      " "
      [:i.wi {:class (str "wi-owm-"
                      (if night? "night-" "day-")
                      cond-6hour
                      " wi-fw")}]]]))
