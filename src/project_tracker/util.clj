(ns project_tracker.util
  (:require [noir.io :as io]
            [markdown.core :as md]))

(defn md->html
  "reads a markdown file public/md and returns an HTML string"
  [filename]
  (->>
   (io/slurp-resourse filename)
   (md/md-to-html-string)))