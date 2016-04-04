(defproject abq-transit "0.1.0-SNAPSHOT"
  :description "Utilities used to interact with ABQ transit data."
  :url "https://github.com/jvtrigueros/abq-transit-utilities"
  :license {:name "Apache License, Version 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.zip "0.1.1"]
                 [korma "0.4.0"]
                 [org.xerial/sqlite-jdbc "3.7.15-M1"]]
  :main ^:skip-aot abq-transit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
