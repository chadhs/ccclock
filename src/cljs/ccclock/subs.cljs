(ns ccclock.subs
  (:require [re-frame.core :as re-frame]))


(re-frame/reg-sub
 ::time
 (fn [db _]
   (let [time-raw      (:time db)
         time-segments (clojure.string/split (.toTimeString time-raw) ":")
         hour->int     (js/parseInt (first time-segments))
         hour-12h      (if (>= hour->int 13) (- hour->int 12) hour->int)
         hour          (if (<= hour-12h 9) (str "0" hour-12h) hour-12h)
         time-h1       (first (str hour))
         time-h2       (second (str hour))
         minutes       (second time-segments)
         time-h1-class (if (= "0" time-h1) "time-zero" "time-one")]
     {:time-h1       time-h1
      :time-h2       time-h2
      :time-min      minutes
      :time-h1-class time-h1-class})))


(re-frame/reg-sub
 ::weather
 (fn [db _]
   (let [weather (:weather db)]
     (if (empty? weather)
       "NA"
       (let [temp-current (.toFixed (get-in weather [:currently :apparentTemperature]) 0)
             today-daily  (first (get-in weather [:daily :data]))
             temp-high    (.toFixed (get today-daily :apparentTemperatureHigh) 0)
             temp-low     (.toFixed (get today-daily :apparentTemperatureLow) 0)
             cond-current (get-in weather [:currently :icon])
             today-hourly (get-in weather [:hourly :data])
             cond-3hour   (get (nth today-hourly 3) :icon)
             cond-6hour   (get (nth today-hourly 6) :icon)]
         {:temp-current temp-current
          :temp-high    temp-high
          :temp-low     temp-low
          :cond-current cond-current
          :cond-3hour   cond-3hour
          :cond-6hour   cond-6hour})))))
