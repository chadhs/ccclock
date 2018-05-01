(ns ccclock.config
  (:require [ccclock.environ :as environ]))


(def debug?
  ^boolean goog.DEBUG)


(def secrets
  environ/secrets)
