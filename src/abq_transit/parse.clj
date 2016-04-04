(ns abq-transit.parse
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.string :as str]
            [clojure.data.zip.xml :refer [xml1-> text]])
  (:import (java.io InputStream)))

(defn- parse-int [^String s]
  (Integer/parseInt (re-find #"\A-?\d+" s)))

(defn route [loc]
  (-> loc
      (xml1-> :Name text)
      (str/split #"\s")
      last
      parse-int))

(defn heading [loc]
  (some-> loc
          (xml1-> :Placemark :Style :IconStyle :heading text)
          parse-int))

(defn coordinates [loc]
  (some-> loc
          (xml1-> :Placemark :Point :coordinates text)
          (str/split #",")))

(defn lon [loc]
  (some-> loc
          coordinates
          first
          read-string))


(defn lat [loc]
  (some-> loc
          coordinates
          second
          read-string))


(defn parse-transit-kml
  "Given an input stream to an ABQ Transit KML, a map containing keys for
   route, heading, lon, and lat is returned. This function doesn't close
   the input stream."
  [^InputStream is]
  (let [transit-zip (-> is
                        xml/parse
                        zip/xml-zip)
        document (xml1-> transit-zip :Document)]
    {:route   (route document)
     :heading (heading document)
     :lon     (lon document)
     :lat     (lat document)}))
