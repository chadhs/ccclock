(ns ccclock.core
  (:require [org.httpkit.server :refer [run-server]]
            [clojure.tools.namespace.repl :as ns-tools])
  (:require [ccclock.config :as config]
            [ccclock.router :as router])
  (:gen-class))

(def app-config (config/config))

(def app router/app-router)

(defonce server (atom nil))

(defn stop-server
  []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn check-vendor-assets
  "Check if vendor assets exist, provide helpful message if not"
  []
  (let [vendor-path "resources/public/vendor/weather-icons"]
    (when-not (.exists (java.io.File. vendor-path))
      (println "WARNING: Vendor assets not found at" vendor-path)
      (println "Run: npm run vendor:weather-icons")
      (println "Or use: ./dev.sh"))))

(defn -main
  [& args]
  ;; Check for vendor assets before starting
  (check-vendor-assets)
  ;; The #' is useful when you want to hot-reload code
  ;; You may want to take a look: https://github.com/clojure/tools.namespace
  ;; and https://http-kit.github.io/migration.html#reload
  (println "server started on:" (:port app-config))
  (reset! server (run-server #'app app-config)))

(defn restart-server
  []
  (when @server (@server))
  (ns-tools/refresh :after 'ccclock.core/-main))

(comment (restart-server) (stop-server))
