(ns ccclock.css
  (:require [garden.def :refer [defstyles]]))


(defstyles screen
  [:*
   {
    :margin "0"
    :padding "0"
    }]

  [:body
   {
    :font-family "courier, monospace"
    :background-color "#000"
    :color "#6A5ACD"
    }]

  [:#app
   {
    :margin "3vw"
    :padding "3vw"
    }]

  [:.time-display
   {
    :font-size "20vw"
    :width "66%"
    :float "right"
    }]

  [:.temp-display
   {
    :width "20%"
    :float "left"
    }]

  [:.temp-display :.temp
   {
    :font-size "12vw"
    }]

  [:.temp-display :.hi-low
   {
    :font-size "6vw"
    }]

  [:.cond-display
   {
    :font-size "16vw"
    :text-align "center"
    :letter-spacing "1vw"
    :padding-top "6vw"
    :clear "both"
    :width "100%"
    }])
