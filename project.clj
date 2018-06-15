(defproject ccclock "0.6.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.908"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.5"]
                 [garden "1.3.2"]
                 [ns-tracker "0.3.0"]
                 [cljs-ajax "0.7.3"]
                 ;; server side libraries
                 [ring "1.6.2"]
                 [compojure "1.6.0"]
                 [environ "1.1.0"]
                 [ring/ring-defaults "0.3.1"]]

  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-garden "0.2.8"]
            ;; server side plugins
            [lein-ring "0.12.1"]
            [lein-environ "1.1.0"]]

  :min-lein-version "2.5.3"

  ;; server side settings
  :ring {:handler ccclock.core/app}
  :uberjar-name "ccclock.jar"

  :source-paths ["src/clj"]

  :figwheel {:css-dirs ["resources/public/css"]
             ;; start and stop ring server - see dev/user.clj
             :init     user/start-server
             :destroy  user/stop-server}

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet   ccclock.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

  :profiles
  {:dev
   {:dependencies  [[binaryage/devtools "0.9.4"]
                    [figwheel-sidecar "0.5.13"]
                    [com.cemerick/piggieback "0.2.2"]
                    [day8.re-frame/re-frame-10x "0.3.3"]
                    ;; for server library at dev time
                    [ring-server "0.5.0"]]
    :plugins       [[lein-figwheel "0.5.13"]]
    :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                      "target"
                                      "resources/public/css"]
    :source-paths  ["src/clj" "dev"]
    :repl-options  {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}

   :prod { }}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "ccclock.core/mount-root"
                    :open-urls ["http://localhost:3000/index.html"]}
     :compiler     {:main                 ccclock.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true}
                    :preloads             [devtools.preload day8.re-frame-10x.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            ccclock.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]}

  :aliases {"package" ["do" "clean"
                       ["cljsbuild" "once"]
                       ["garden" "once"]
                       ["ring" "uberjar"]]})
