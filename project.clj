(defproject abq-transit "0.1.0-SNAPSHOT"
  :description "Utilities used to interact with ABQ transit data."
  :url "https://github.com/jvtrigueros/abq-transit-utilities"
  :license {:name "Apache License, Version 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.zip "0.1.1"]
                 [clj-time "0.11.0"]
                 [korma "0.4.2"]
                 [org.xerial/sqlite-jdbc "3.8.11.2"]]
  :main ^:skip-aot abq-transit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
