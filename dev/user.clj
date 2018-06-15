(ns user
  (:require [figwheel-sidecar.repl-api :as figwheel]
            [ccclock.core              :as api-proxy]
            [ring.server.standalone    :as ring-server]))


(defonce dev-state (atom {}))


(defn start-server
  "Starts a ring server for your developement application"
  []
  (if-not (:ring-server @dev-state)
    (swap! dev-state assoc :ring-server
           ;; NOTE using var for better REPL reloading dev experience
           (ring-server/serve #'api-proxy/dev-app {:open-browser? false}))
    (println "Server already running!")))


(defn stop-server
  "Stops the running ring server."
  []
  (when-let [ring-server (:ring-server @dev-state)]
    (swap! dev-state dissoc :ring-server)
    (println "Stopping ring server!")
    (.stop ring-server)))


;; FIGWHEEL HELPERS: starting and stopping figwheel in the REPL
(defn fig-start
  "This starts the figwheel server and watch based auto-compiler."
  []
  (figwheel/start-figwheel!))


(defn fig-stop
  "Stop the figwheel server and auto-compiler."
  []
  (figwheel/stop-figwheel!))


(defn cljs-repl
  "Launch a ClojureScript REPL that is connected to your build and host environment."
  []
  (figwheel/cljs-repl))
