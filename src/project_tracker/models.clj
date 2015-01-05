(ns project_tracker.models
  (:require [monger.core :as mg]
            [project_tracker.environment :as env]))

(defn db-connected? []
  (bound? #'mg/*mongodb-connection*))

(defn db-connect []
  (if (not (db-connected?))
    (let [uri (env/get "MONGOLAB_URI" (env/get "MONGO_PROJECT_TRACKER_URL" "mongodb://127.0.0.1/projecttracker"))]
      (monger.core/connect-via-uri! uri))))