(ns abq-transit.core
  (:gen-class)
  (:use [clojure.pprint])
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [korma.db :as db]
            [korma.core :as kore]
            [overtone.at-at :refer :all]
            [abq-transit.parse :as transit-parse]))

(declare routes)

(kore/defentity routes)

(defn read-config [config-path]
  (edn/read-string (slurp config-path)))

(def route-values (atom []))

(defn parse-url [route-url]
  (with-open [route-is (-> route-url
                           io/input-stream)]
    (transit-parse/parse-transit-kml route-is)))

(defn commit-routes [values]
  (do
    (println (kore/insert routes
                          (kore/values (vec @values))))
    (reset! values [])))

(defn -main
  "Given a configuration file, parse KML and insert into a DB."
  [& args]
  (if-let [config-path (first args)]
    (let [config (read-config config-path)
          db (-> (:db config)
                 db/postgres
                 db/create-db
                 db/default-connection)
          routes (:routes config)
          thread-pool (mk-pool)
          collect-period (* 15 1000)
          purge-period (* 4 collect-period)]
      (every collect-period #(swap! route-values concat (map parse-url routes)) thread-pool)
      (every purge-period #(commit-routes route-values) thread-pool :initial-delay purge-period))
    (println "Please pass in an EDN configuration file as your first command line argument.")))
