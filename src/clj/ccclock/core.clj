(ns ccclock.core
  (:require [compojure.core           :refer [defroutes GET]]
            [compojure.route          :as    route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response       :as    response]))


(defroutes app-routes
  ;; NOTE: this will deliver all of your assets from the public directory
  ;; of resources i.e. resources/public
  (route/resources "/" {:root "public"})
  ;; NOTE: this will deliver your index.html
  (GET             "/"      [] (-> (response/resource-response "index.html" {:root "public"})
                                   (response/content-type "text/html")))
  (GET             "/weather" [] "call-weather-handler")
  (route/not-found             "Page not found"))


(def app (wrap-defaults app-routes site-defaults))


(def dev-app (wrap-defaults #'app-routes site-defaults))
