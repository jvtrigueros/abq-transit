(ns abq-transit.core-test
  (:use [clojure.pprint])
  (:require [clojure.test :refer :all]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.data.zip.xml :as zx]
            [clojure.java.io :as io]
            [abq-transit.core :refer :all]))

(def kml-route (atom nil))

(defn load-kml-route
  "Loads a KML route into a zipper."
  [test-fn]
  (reset! kml-route (with-open
                      [xml (-> "route2.kml"
                               io/resource
                               io/input-stream)]
                      (zip/xml-zip (xml/parse xml))))
  (test-fn))

(use-fixtures :once load-kml-route)

(deftest route-number
  (testing "Verify that route number is correct."
    (is (= 2 (route (zx/xml1-> @kml-route :Document))))))
