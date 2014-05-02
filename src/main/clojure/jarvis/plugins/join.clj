(ns jarvis.plugins.join
  (:require [jarvis.bot :as bot]
            [jarvis.plugins :as plugins]
            [clj-flowdock.api.flow :as f]
            [clj-flowdock.api.user :as user]
            [clj-flowdock.api.message :as m]
            [clojure.string :as s]))

(declare join-flows join-flow)

(defn ^{:description "usage: ~join <i>flow-display-name</i> -> Join the flow that is specified."
        :command "join"
        :author "Jonathan Chauncey, Matt Farrar"
        :plugin true}
  join [message content-vec]
  (join-flows message @plugins/plugins-atom))

(defn- join-flows [message plugins]
  (let [flows (rest (s/split (m/content message) #"\s"))
        reply (map #(join-flow % plugins) flows)]
    (str "" (s/join "\n" reply))))

(defn- join-flow [flow-name plugins]
  (if-let [flow (f/find "name" flow-name)]
    (do
      (f/add-myself flow)
      (bot/init-flow-thread flow plugins)
      (str "Joined flow - " flow-name))
    (str "Could not join flow - " flow-name ". Try inviting me through the UI with my email address.")))