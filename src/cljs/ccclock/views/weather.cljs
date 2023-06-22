(ns ccclock.views.weather
  (:require [re-frame.core :as re-frame])
  (:require [ccclock.styles :as styles]
            [ccclock.subs :as subs]))

(defn weather-panel
  []
  (let [weather-data @(re-frame/subscribe [::subs/weather])
        temp-current (get weather-data :temp-current)
        temp-high    (get weather-data :temp-high)
        temp-low     (get weather-data :temp-low)
        night?       (:night? weather-data)
        cond-current (get weather-data :cond-current)
        cond-3hour   (get weather-data :cond-3hour)
        cond-6hour   (get weather-data :cond-6hour)]
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
