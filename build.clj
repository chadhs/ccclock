(ns build
  (:require [clojure.tools.build.api :as build-api]))

;; Build configuration
(def project-config
  "Project configuration to support all tasks"
  {:main-namespace  'ccclock.core
   :class-directory "target/classes"
   :project-basis   (build-api/create-basis {:project "deps.edn"})
   :uberjar-file    "target/ccclock-standalone.jar"})

;; Build tasks
(defn clean [_] (build-api/delete {:path "target"}))

(defn uberjar
  [_]
  (let [{:keys [class-directory main-namespace project-basis uberjar-file]}
        project-config]
    (clean nil)
    (build-api/copy-dir {:src-dirs   ["src/clj" "resources"]
                         :target-dir class-directory})
    (build-api/compile-clj
     {:basis project-basis :src-dirs ["src/clj"] :class-dir class-directory})
    (build-api/uber {:class-dir class-directory
                     :uber-file uberjar-file
                     :basis     project-basis
                     :main      main-namespace})))
