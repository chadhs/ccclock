(ns ccclock.config
  (:require [aero.core :refer (read-config)]
            [clojure.java.io :as io]))

(defn config
  "Load the api config from the config files at the project root. These files pull in environment variables with a default fallback for some values. If a dev config is present, override the production config to support local dev."
  []
  (let [config      (read-config (io/resource "config.edn"))
        dev-config? (try (slurp "./config-dev.edn") (catch Exception e false))
        dev-config  (when dev-config? (read-config "config-dev.edn"))]
    (if (= (System/getenv "CCCLOCK_ENV") "prod")
      config
      (merge config dev-config))))
