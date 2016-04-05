(ns abq-transit.core
  (:gen-class)
  (:use [clojure.pprint])
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [korma.db :as db]
            [korma.core :as kore]
            [abq-transit.parse :as transit-parse]))

(declare routes)

(kore/defentity routes)

(defn read-config [config-path]
  (edn/read-string (slurp config-path)))

(defn -main
  "Given a list of URLs, parse KML."
  [& args]
  (if-let [config-path (first args)]
    (let [config (read-config config-path)
          db (-> (:db config)
                 db/sqlite3
                 db/create-db
                 db/default-connection)]
      (doseq [route-url (:routes config)]
        (with-open [route-is (-> route-url
                                 io/input-stream)]
          (let [route (transit-parse/parse-transit-kml route-is)]
            (kore/insert routes
                         (kore/values route))))))

    (println "Please pass in an EDN configuration file as your first command line argument.")))
