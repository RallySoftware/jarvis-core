(ns jarvis.flowdock.debug
  (:require [clojure.java.io :as io]
            [cheshire.core :as json]
            [clojure.tools.logging :as log]))

(defn print-message [message]
  (-> message
      json/generate-string
      log/debug)
  message)