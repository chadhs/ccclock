(defproject ccclock "0.6.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.312"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.5"]
                 [garden "1.3.5"]
                 [ns-tracker "0.3.1"]
                 [cljs-ajax "0.7.3"]
                 ;; server side libraries
                 [ring "1.6.3"]
                 [compojure "1.6.1"]
                 [environ "1.1.0"]
                 [ring/ring-defaults "0.3.2"]
                 [clj-http "3.9.0"]
                 [cheshire "5.8.0"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-garden "0.3.0"]
            ;; server side plugins
            [lein-ring "0.12.4"]
            [lein-environ "1.1.0"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "resources/public/css"]

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler ccclock.core/dev-app}

  :ring {:handler ccclock.core/app}

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet   ccclock.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

  :repl-options  {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}

  :profiles
  {:dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:dependencies [[binaryage/devtools "0.9.10"]
                                  [day8.re-frame/re-frame-10x "0.3.3"]
                                  [figwheel-sidecar "0.5.16"]
                                  [cider/piggieback "0.3.6"]]
                   :plugins       [[lein-figwheel "0.5.16"]]}
   :project/test {}
   :prod         {}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "ccclock.core/mount-root"}
     :compiler     {:main                 ccclock.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload
                                           day8.re-frame-10x.preload]
                    :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true}
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            ccclock.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]}

  ;; server side settings
  :main ccclock.core

  :aot [ccclock.core]

  :uberjar-name "ccclock.jar"

  :prep-tasks ["clean" ["cljsbuild" "once" "min"]["garden" "once"] "compile"])
