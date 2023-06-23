(ns ccclock.views.main
  (:require [ccclock.styles :as styles] ; loads css in `styles` for entire app
            [ccclock.views.weather :as views.weather]
            [ccclock.views.time :as views.time]))

(defn main-panel [] [:<> [views.time/time-panel] [views.weather/weather-panel]])
