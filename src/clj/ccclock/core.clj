(ns ccclock.core
  (:require [ccclock.route            :as    route])
  (:require [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.reload   :refer [wrap-reload]]
            [environ.core             :as    environ]
            [ring.adapter.jetty       :refer [run-jetty]])
  (:gen-class))


(def dev-app
  (-> #'route/app-routes
      (wrap-defaults site-defaults)
      wrap-reload))


(def app
  (-> route/app-routes
      (wrap-defaults site-defaults)))


(defn -main [& args]
  (let [port (Integer/parseInt (or (environ/env :proxy-port) "8000"))]
    (run-jetty app {:port port :join? false})))
