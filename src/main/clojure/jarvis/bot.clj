(ns jarvis.bot
  (:require [jarvis.command :as command]
            [jarvis.bot :as jarvis]
            [jarvis.plugins :as plugins]
            [jarvis.util :as util]
            [clj-flowdock.api.flow :as flow]
            [clj-flowdock.api.message :as m]
            [clj-flowdock.streaming :as streaming]
            [clojure.tools.logging :as log])
  (:import [java.util.concurrent Executors ExecutorService])
  (:gen-class ))

(declare init-flow-thread)

(def threadpool (atom (Executors/newFixedThreadPool (util/config-property "THREAD_POOL_SIZE" 100))))

(defmacro listen [[flow message-sym flow-con-sym] & body]
  `(with-open [~flow-con-sym (streaming/open ~flow)]
     (loop []
       (when-let [~message-sym (.read ~flow-con-sym)]
         ~@body)
       (recur))))

(defn invoke-plugin [flow-connection raw-message plugins]
  (when-let [plugin (command/command->plugin raw-message plugins)]
    (let [message (util/enhance-message raw-message)]
      (try
        (cond
          (command/leave-command? message) (util/close-flow-connection flow-connection)
          (command/tell-command? message) (command/tell message plugin)
          (command/join-command? message) (init-flow-thread (get message "content") plugins)
          (command/private-message? message) (command/private-message message plugin)
          :else (command/reply message plugin))
        (catch Exception e
          (log/error e (plugins/command-name plugin) "threw an exception")
          (m/reply message (str "That command threw the exception ["(.getClass e)"], message ["(.getMessage e)"]")))))))

(defn flow-stream [flow plugins]
  (listen [flow msg flow-connection]
    (invoke-plugin flow-connection msg plugins)))

(defn user-stream [plugins]
  (listen ["" msg flow-connection]
    (invoke-plugin flow-connection msg plugins)))

(defn init-flow-thread [flow plugins]
  (.submit @threadpool #(flow-stream (flow/flow->flow-id flow) plugins)))

(defn init-user-thread [plugins]
  (.submit @threadpool #(user-stream plugins)))

(defn init-threads [plugins]
  (doseq [flow (flow/list)]
    (init-flow-thread flow plugins))
  (init-user-thread plugins))

(defn init []
  (log/info "Starting Jarvis...")
  (when-let [plugins (plugins/load-plugins)]
    (log/info "Starting to read from flowdock streams")
    (init-threads plugins)))

(defn -main []
  (jarvis/init))


