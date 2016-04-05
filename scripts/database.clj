(ns database
  (:refer-clojure :exclude [alter drop bigint boolean char double float time])
  (require [korma.db :as db]
           [abq-transit.core :refer [read-config]])
  (use [lobos connectivity core schema]))

(defn create-routes-table []
  (create
    (table :routes
           (timestamp :timestamp (default (now)))
           (float :lat)
           (float :lon)
           (integer :heading)
           (integer :route))))

(defn -main [& args]
  (if-let [config-path (first args)]
    (let [config (read-config config-path)]
      (open-global (db/sqlite3 (:db config)))
      (create-routes-table))))
