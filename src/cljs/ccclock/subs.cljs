(ns ccclock.subs
  (:require [re-frame.core :as re-frame]))


(re-frame/reg-sub
 ::time
 (fn [db _]
   (:time db)))


(re-frame/reg-sub
 ::weather
 (fn [db _]
   (:weather db)))


(re-frame/reg-sub
 ::forecast
 (fn [db _]
   (:forecast db)))
