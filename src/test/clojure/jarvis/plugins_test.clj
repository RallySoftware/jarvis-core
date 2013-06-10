(ns jarvis.plugins-test
  (:use clojure.test)
  (:require [jarvis.plugins :as plugins]
            [clojure.java.io :as io]))

(deftest plugin?
  (is (= true (plugins/plugin? (io/file "/jarvis/plugins/jenkins.clj"))))
  (is (= false (plugins/plugin? (io/file "/jarvis/plugins.clj"))))
  (is (= true (plugins/plugin? (io/file "/jarvis/config/jarvis/plugins/jenkins.clj")))))

(deftest relative-to
  (is (= "jarvis/plugins/jenkins.clj" (plugins/relative-to "/Users/pairing" "/Users/pairing/jarvis/plugins/jenkins.clj")))
  (is (= "jarvis/plugins/jenkins.clj" (plugins/relative-to "/Users/pairing/" "/Users/pairing/jarvis/plugins/jenkins.clj")))
  (is (= "jarvis/plugins/jenkins.clj" (plugins/relative-to "/" "/jarvis/plugins/jenkins.clj"))))