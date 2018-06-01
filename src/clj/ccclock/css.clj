(ns ccclock.css
  (:require [garden.def :refer [defstyles]]))

(def colors
  {:default-text       "#6A5ACD"
   :default-background "#000"})

(defstyles screen
  [:*
   {:margin "0"
    :padding "0"}]

  [:body
   {:font-family "courier, monospace"
    :background-color (get colors :default-background)
    :color (get colors :default-text)}]

  [:#app
   {:margin "0.5vw"
    :margin-top "4vw"
    :padding "0.5vw"}]

  [:.time-display
   {:font-size "23vw"
    :width "70%"
    :float "right"}]

  [:.time-zero
   {:color (get colors :default-background)}]

  [:.time-one
   {:color (get colors :default-text)}]

  [:.temp-display
   {:width "20%"
    :float "left"
    :padding-top "0vw"}]

  [:.temp-display :.temp
   {:font-size "16.5vw"
    :padding-bottom "1vw"}]

  [:.temp-display :.hi-low
   {:font-size "7vw"}]

  [:.cond-display
   {:font-size "19vw"
    :text-align "center"
    :letter-spacing "1vw"
    :padding-top "6vw"
    :clear "both"
    :width "100%"}])
