(ns abq-transit.core
  (:gen-class)
  (:use [clojure.pprint])
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [korma.db :as db]
            [abq-transit.parse :as transit-parse]))


(defn- read-config [config-path]
  (edn/read-string (slurp config-path)))

(defn -main
  "Given a list of URLs, parse KML."
  [& args]
  (if-let [config-path (first args)]
    (let [config (read-config config-path)
          db (-> (:db config)
                 db/create-db
                 db/default-connection)]
      (doseq [route-url (:routes config)]
          (with-open [route (-> route-url
                                io/input-stream)]
            (pprint (transit-parse/parse-transit-kml route)))))
    (println "Please pass in an EDN configuration file as your first command line argument.")))
