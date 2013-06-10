(ns jarvis.util-test
  (:use [clojure.test])
  (:require [jarvis.test-util :as test-util]
            [jarvis.util :as util]))

(deftest config-property
  (is (= nil (util/config-property "some-property")))
  (is (= (System/getenv "PWD") (util/config-property "PWD")))
  (System/setProperty "some-foo-property" "some-foo-value")
  (is (= "some-foo-value" (util/config-property "some-foo-property")))
  (is (= "some-default-value" (util/config-property "some-property" "some-default-value"))))