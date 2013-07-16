(ns jarvis.util
  (:require [jarvis.flowdock.debug :as debug]
            [clj-flowdock.api.flow :as f]
            [clj-flowdock.api.message :as m]
            [clj-flowdock.api.user :as user]
            [clojure.string :as s]
            [clojure.tools.logging :as log]
            [clojure.tools.reader.edn :as edn]))

(defn- env-variable [name]
  (-> (System/getenv)
    (get name)))

(defn- build-string [content]
  (str "[" content "]"))

(defn config-property
  ([name]
    (config-property name nil))
  ([name default-value]
    (if-let [env-value (env-variable name)]
      env-value
      (if-let [system-value (System/getProperty name)]
        system-value
        default-value))))

(defn enhance-message [message]
  (-> message
    (assoc "parent" (m/parent-message message))
    (assoc "user" (user/get (m/user message)))
    debug/print-message))

(defn close-flow-connection [flow-connection]
  (log/info (str "Closing flow connection - " (.flow-id flow-connection)))
  (m/chat (.flow-id flow-connection) "Very well sir, goodbye.")
  (f/block-user (.flow-id flow-connection) (get (user/me) "id"))
  (.close flow-connection))

(defn message-content->vec [message]
  (vec (map str (-> message
            (get "content")
            (subs 1)
            build-string
            (edn/read-string)))))