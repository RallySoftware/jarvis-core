(ns jarvis.command
  (:require [jarvis.util :as util]
            [clj-flowdock.api.message :as m]
            [clojure.string :as s]
            [clojure.tools.logging :as log]))

(declare get-user-id-from-message get-user-ids-from-tags user->id)

(defn tell [message plugin]
  (when-let [reply (plugin message (util/message-content->vec message))]
    (let [user-ids (get-user-ids-from-tags message)]
      (log/info "Plugin:" (m/content message) "replied:" (str "'" reply "'"))
      (m/send-private-messages user-ids reply))))

(defn private-message [message plugin]
  (when-let [reply (plugin message (util/message-content->vec message))]
    (let [user-id (get-user-id-from-message message)]
      (log/info "Plugin:" (m/content message) "replied to user:" user-id " with content:" (str "'" reply "'"))
      (m/send-private-message user-id reply))))

(defn reply [message plugin]
  (when-let [reply (plugin message (util/message-content->vec message))]
    (log/info "Plugin:" (m/content message) "replied:" (str "'" reply "'"))
    (m/reply message reply)))

(defn tell-command? [message]
  (let [content (m/content message)]
    (.startsWith content "~~")))

(defn private-message? [message]
  (and
    (contains? message "to")
    (= "message" (get message "event"))))

(defn leave-command? [message]
  (when-let [content (m/content message)]
    (and
      (not (map? content))
      (.equals content "~leave"))))

(defn join-command? [message]
  (= "flow-add" (get message "event")))

(defn parse-command [message-content]
  (let [content-vec (s/split message-content #" ")
        command (first content-vec)]
    (cond
      (.startsWith command "~~") (subs command 2)
      (.startsWith command "~") (subs command 1))))

(defn command->plugin [message plugins]
  (when-let [content (m/content message)]
    (when-not (map? content)
      (get plugins (parse-command content)))))

(defn get-user-id-from-message [message]
  (get-in message ["user" "id"]))

(defn get-user-ids-from-tags [message]
  (if (< 0 (count (message "tags")))
    (->> message
      (message "tags")
      (filter #(.startsWith % ":user:"))
      (map #(user->id %)))
    (list (get-user-id-from-message message))))

(defn- user->id [user]
  (re-find #"\d+" user))
