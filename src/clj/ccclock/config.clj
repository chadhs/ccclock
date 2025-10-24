(ns ccclock.config
  (:require [aero.core :refer (read-config)]
            [clojure.java.io :as io]))

(defn config
  "Load the api config from config files at the project root. These files pull in environment variables with a default fallback for some values. If a dev config is present, override the production config to support local dev."
  []
  (let [base-config (read-config (io/resource "config.edn"))
        dev-config? (try (slurp "./config-dev.edn") (catch Exception e false))
        dev-config  (when dev-config? (read-config "config-dev.edn"))]
    ;; Merge dev config into base config (dev config takes precedence)
    (merge base-config dev-config)))
