(ns jarvis.plugins.help
  (:require [jarvis.plugins :as plugins]
            [clojure.string :as s]
            [clj-flowdock.api.team-inbox :as inbox]
            [clj-flowdock.api.flow :as flow]
            [clj-flowdock.api.message :as message]))

(defn ^{:description "Prints the description of all plugins."
        :command "help"
        :author "Adam Esterline"
        :plugin true}
  help
  ([message content-vec] (help message content-vec @plugins/plugins-atom))
  ([message content-vec plugins]
     (let [plugin-meta (map meta plugins)
           sorted-plugin-meta (sort-by :command plugin-meta)
           help-text (map #(str "<code>~" (:command %) "</code> : " (:description %)) sorted-plugin-meta)
           formatted-help-text (str
                                "Sir, I will only respond when directly given a <code>~<i>command</i></code> as a new thread. I will not reply to threaded commands or @Jarvis.<br />"
                                "<br />All known plugins - <br />"
                                (s/join "<br />" help-text)
                                "<br /><code>~leave</code> can be used to have me leave a flow. I will need to be invited back through the flowdock api to join again."
                                "<br /><code>~<i>command</i></code> can be used also in private-messages with me."
                                "<br />For more details see https://github.com/RallySoftware/jarvis-core")
           flow (flow/get (message/flow-id message))]
       (inbox/post (get flow "api_token") {:source "Jarvis" :from_address "donotreply@rallydev.com" :subject "Help" :content formatted-help-text :tags ["jarvis_help" "help_me_jarvis"]})
       (str "Sir, I have placed the items you are looking for in the team inbox, found to the left of this pane."))))
