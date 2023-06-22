(ns ccclock.views.main
  (:require [ccclock.styles :as styles]
            [ccclock.views.weather :as views.weather]
            [ccclock.views.time :as views.time]))

(defn main-panel [] [:<> [views.time/time-panel] [views.weather/weather-panel]])
