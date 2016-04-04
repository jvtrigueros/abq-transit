(ns abq-transit.core
  (:gen-class)
  (:use [clojure.pprint])
  (:require [clojure.java.io :as io]
            [abq-transit.parse :as transit-parse]))

(defn -main
  "Given a list of URLs, parse KML."
  [& args]
  (doseq [route-url args]
    (with-open [route (-> route-url
                          io/input-stream)]
      (pprint (transit-parse/parse-transit-kml route)))))

