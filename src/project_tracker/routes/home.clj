(ns project_tracker.routes.home
  (:require [compojure.core :refer :all]
            [project_tracker.views.layout :as layout]))

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (GET "/" [] (home)))
