(defproject project_tracker "0.1.0-SNAPSHOT"
  :description "Project trackerff"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [environ "0.5.0"]
                 [com.taoensso/timbre "3.2.1"]
                 [markdown-clj "0.9.44"]
                 [korma "0.3.1"]
                 [com.taoensso/tower "2.0.2"]
                 [selmer "0.6.6"]
                 [compojure "1.1.6"]
                 [log4j "1.2.17"
                  :exclusions
                  [javax.mail/mail
                   lavax.jms/jms
                   com..sun.jdmk/jmxtools
                   com.sun.jmx/jmxril]]
                 [lib-noir "0.8.3"]
                 [com.novemberain/monger "2.0.0"]
                 [ring-server "0.3.1"]]
  :plugins
  [[lein-ring "0.8.12"] [lein-environ "1.0.0"]]

  :ring
  {:handler project_tracker.handler/app
   :init project_tracker.handler/init
   :destroy project_tracker.handler/destroy}

  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}}

  :min-lein-version "2.0.0")
