(ns ccclock.router
  (:require [reitit.ring :as ring]
            [reitit.ring.middleware.muuntaja
             :refer
             [format-negotiate-middleware
              format-request-middleware
              format-response-middleware]]
            [reitit.coercion.schema]
            [muuntaja.core :as m]
            [clojure.java.io :as io])
  (:require [ccclock.api.weather :as weather]))

(defn index [] (slurp (io/resource "public/index.html")))

(def api-routes
  ["/"
   ["api"
    ["/health" {:get {:handler (fn [_] {:status 200 :body {:status "ok"}})}}]
    ["/weather" {:get {:handler weather/weather-handler}}]]
   ["" {:handler (fn [_] {:body (index) :status 200})}]])

(def app-router
  (ring/ring-handler
   (ring/router [api-routes]
                {:data {:coercion   reitit.coercion.schema/coercion
                        :muuntaja   m/instance
                        :middleware [format-negotiate-middleware
                                     format-response-middleware
                                     format-request-middleware]}})
   (ring/routes
    (ring/redirect-trailing-slash-handler)
    (ring/create-resource-handler {:path "/"})
    (ring/create-default-handler
     {:not-found (fn [_]
                   {:status 404
                    :body
                    "ruh roh... can't find what you're looking for."})}))))
