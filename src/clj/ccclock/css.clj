(ns ccclock.css
  (:require [garden.def :refer [defstyles]]))


(defstyles screen
  [:*
   {:margin "0"
    :padding "0"}]

  [:body
   {:font-family "courier, monospace"
    :background-color "#000"
    :color "#6A5ACD"}]

  [:#app
   {:margin "0.5vw"
    :margin-top "2vw"
    :padding "0.5vw"}]

  [:.time-display
   {:font-size "23vw"
    :width "70%"
    :float "right"}]

  [:.temp-display
   {:width "20%"
    :float "left"
    :padding-top "2vw"}]

  [:.temp-display :.temp
   {:font-size "16vw"
    :padding-bottom "1vw"}]

  [:.temp-display :.hi-low
   {:font-size "7vw"}]

  [:.cond-display
   {:font-size "20vw"
    :text-align "center"
    :letter-spacing "1vw"
    :padding-top "2vw"
    :clear "both"
    :width "100%"}])
