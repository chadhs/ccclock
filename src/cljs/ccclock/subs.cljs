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
         time-h1       (first hour)
         time-h2       (second hour)
         minutes       (second time-segments)
         time-h1-class (if (= "0" time-h1) "time-zero" "time-one")]
     {:time-h1  time-h1
      :time-h2  time-h2
      :time-min minutes
      :time-h1-class time-h1-class})))


(re-frame/reg-sub
 ::weather
 (fn [db _]
   (let [weather (:weather db)]
     (if (empty? weather)
       "NA"
       (let [temp-current     (.toFixed (get-in weather [:main :temp]) 0)
             cond-current     (get (first (get weather :weather)) :id)]
         {:temp-current     temp-current
          :cond-current     cond-current})))))


(re-frame/reg-sub
 ::forecast
 (fn [db _]
   (let [forecast (:forecast db)
         weather  (:weather db)]
     (if (or (empty? forecast) (empty? weather))
       "NA"
       (let [current-high    (vector (get-in weather [:main :temp_max]))
             current-low     (vector (get-in weather [:main :temp_min]))
             forecast-today  (take 3 (get forecast :list))
             forecast-high   (map #(get-in % [:main :temp_max]) forecast-today)
             forecast-low    (map #(get-in % [:main :temp_min]) forecast-today)
             temp-high       (.toFixed (apply max (apply merge current-high forecast-high)) 0)
             temp-low        (.toFixed (apply min (apply merge current-low forecast-low)) 0)
             conds-today     (map #(get (first (% :weather)) :id) forecast-today)
             cond-3h         (first conds-today)
             cond-6h         (second conds-today)]
         {:temp-high temp-high
          :temp-low  temp-low
          :cond-3h   cond-3h
          :cond-6h   cond-6h})))))
