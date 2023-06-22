(ns ccclock.subs
  (:require [re-frame.core :as re-frame]
            [clojure.string :as str]))

(re-frame/reg-sub
 ::time
 (fn [db _]
   (let [time-raw      (:time db)
         time-segments (str/split (.toTimeString time-raw) ":")
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

(defn kelvin->f
  "Takes a temperature in Kelvin and returns an integer value in Fahrenheit."
  [ktemp]
  (.toFixed (+ (* (- ktemp 273.15) (/ 9 5)) 32)))

(defn trunc-time
  "Takes a unix epoch time and limits the precision (length) by n."
  [int n]
  (let [trunc-time-str (subs (str int) 0 (min (count (str int)) n))]
    (js/parseInt trunc-time-str)))

(re-frame/reg-sub
 ::weather
 (fn [db _]
   (let [weather (:weather db)]
     (if (empty? weather)
       "NA"
       (let [temp-current (kelvin->f (get-in weather [:current :feels_like]))
             today-daily  (first (:daily weather))
             today-feels  (sort (vals (:feels_like today-daily)))
             temp-high    (kelvin->f (last today-feels))
             temp-low     (kelvin->f (first today-feels))
             cond-current (:id (first (get-in weather [:current :weather])))
             today-hourly (:hourly weather)
             cond-3hour   (:id (first (:weather (nth today-hourly 3))))
             cond-6hour   (:id (first (:weather (nth today-hourly 6))))
             night?       (> (trunc-time (.getTime (js/Date.)) 10)
                             (:sunset today-daily))]
         {:temp-current temp-current
          :temp-high    temp-high
          :temp-low     temp-low
          :night?       night?
          :cond-current cond-current
          :cond-3hour   cond-3hour
          :cond-6hour   cond-6hour})))))
