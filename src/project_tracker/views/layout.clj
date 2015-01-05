(ns project_tracker.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to project_tracker"]
     (include-css "/css/screen.css")]
    [:body body]))
