(ns project_tracker.common.validation
  (:require [project_tracker.common.result :as result]))

(defn has-value? [map key error-msg]
  (let [value (key map)]
    (if (and value (not= value ""))
      (result/result map)
      (result/error map key error-msg))))

(defn on-error
  "If the diven field has an error, execute func and return its value"
  [errors field func]
  (if-let [errs (field errors)]
   (func errs)))