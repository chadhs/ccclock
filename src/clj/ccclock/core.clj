(ns ccclock.core
  (:require [compojure.core           :refer [defroutes GET]]
            [compojure.route          :as    route]
            [ring.util.response       :as    response]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.reload   :refer [wrap-reload]]
            [environ.core             :as    environ]
            [ring.adapter.jetty       :refer [run-jetty]])
  (:gen-class))


(defroutes app-routes
  ;; NOTE: this will deliver all of your assets from the public directory
  ;; of resources i.e. resources/public
  (route/resources "/" {:root "public"})
  ;; NOTE: this will deliver your index.html
  (GET             "/"        [] (-> (response/resource-response "index.html" {:root "public"})
                                     (response/content-type "text/html")))
  (GET             "/weather" [] "placeholder weather data")
  (route/not-found               "Page not found"))


(def dev-app
  (-> #'app-routes
      (wrap-defaults site-defaults)
      wrap-reload))


(def app
  (-> app-routes
      (wrap-defaults site-defaults)))


(defn -main [& args]
  (let [port (Integer/parseInt (or (environ/env :port) "3000"))]
    (run-jetty app {:port port :join? false})))
