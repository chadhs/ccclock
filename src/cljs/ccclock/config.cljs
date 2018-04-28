(ns ccclock.config
  (:require [ccclock.environ :as environ]))


(def debug?
  ^boolean goog.DEBUG)


(def secrets
  ;; TODO load from env.edn, if vals empty load from getenv
  environ/secrets)
