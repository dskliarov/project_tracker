(ns project_tracker.handler
  (:require [compojure.core :refer [defroutes routes]]
            [project_tracker.routes.home :refer [home-routes]]
            [project_tracker.db.schema :as schema]
            [noir.util.middleware :as middleware]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.rotor :as rotor]
            [selmer.parser :as parser]
            [environ.core :refer [env]]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat.
   Put any initialization code here"
  []
  (timbre/info "Project Tracker is starting")
  (timbre/set-config!
   [:appenders :rotor]
   {:min-level :info
    :enabled? true
    :assync? false ; should be always false for rotor
    :max-message-per-msecs nil
    :fn rotor/appender-fn})

  (timbre/set-config!
   [:shared-appender-config :rotor]
   {:path "project_tracker.log" :max-size (* 512 1024) :backlog 10})

  (if (env :dev) (parser/cache-off))

  ;; iniitalize database if needed
  ;; (if-not (schema/initialized?) (schema/create-tables))

  (timbre/info "Project Tracker started successfully"))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (timbre/info "Project Tracker is shutting down"))

(defn template-error-page [handler]
  (if (env :dev)
    (fn [request]
      (try
        (handler request)
        (catch clojure.lang.ExceptionInfo ex
          (let [{:keys [type error-template] :as data} (ex-data ex)]
            (if (= :selmer-validation-error type)
              {:status 500
               :body (parser/render error-template data)}
              (throw ex))))))
    handler))

(def app (middleware/app-handler
          ;; add your application routes here
          [home-routes app-routes]
          ;; add custom middleware here
          :middleware [template-error-page]
          ;; add access rules here
          :access-rules []
          ;; serialize/deserialize the following data formats
          ;; available formats:
          ;; :json :json-kw :yaml-kw :edn :yaml-in-html
          :formats [:json-kw :edn]))
