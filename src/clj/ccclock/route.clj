(ns ccclock.route
  (:require [ccclock.handler :as    handler])
  (:require [compojure.core  :refer [defroutes GET]]
            [compojure.route :as    route]))


(defroutes app-routes
  ;; NOTE: this will deliver all of your assets from the public directory
  ;; of resources i.e. resources/public
  (route/resources "/" {:root "public"})
  (GET "/"         [] handler/handle-index)
  (GET "/weather"  [] handler/handle-proxy)
  (route/not-found    "Page not found"))
