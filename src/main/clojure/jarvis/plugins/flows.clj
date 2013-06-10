(ns jarvis.plugins.flows
  (:require [jarvis.plugins :as plugins]
            [clj-flowdock.api.message :as message]
            [clj-flowdock.api.flow :as flow]
            [clojure.string :as s]))

(defn ^{:description "list-flows - Prints all flows that I am listening to."
        :command "list-flows"
        :author "Jonathan Chauncey"
        :plugin true}
  list-flows [message]
  (let [flows (flow/list)]
    (s/join ", " (map #(% "name") flows))))