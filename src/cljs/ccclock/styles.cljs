(ns ccclock.styles
  (:require [spade.core :refer [defglobal defclass]]))

(def colors {:default-text "#6A5ACD" :default-background "#000"})

(defglobal defaults
           [:body {:font-family      "courier, monospace"
                   :background-color (:default-background colors)
                   :color            (:default-text colors)}]
           [:#app {:margin "0" :padding "0"}]
           [:.time-display {:font-size "23vw" :width "70%" :float "right"}]
           [:.time-zero {:color (:default-background colors)}]
           [:.time-one {:color (:default-text colors)}]
           [:.temp-display {:width "20%" :float "left" :padding-top "0vw"}]
           [:.temp-display :.temp {:font-size "16.5vw" :padding-bottom "1vw"}]
           [:.temp-display :.hi-low {:font-size "7vw"}]
           [:.cond-display {:font-size      "16.5vw"
                            :text-align     "center"
                            :letter-spacing "1vw"
                            :padding-top    "6vw"
                            :clear          "both"
                            :width          "100%"}])
