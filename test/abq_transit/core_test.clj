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

(defn document [loc]
  (zx/xml1-> loc
             :Document))

(deftest route-number-test
  (testing "Verify that route number is 2."
    (is (= 2 (route (document @kml-route))))))

(deftest heading-test
  (testing "Verify that route heading is 351."
    (is (= 351 (heading (document @kml-route))))))
