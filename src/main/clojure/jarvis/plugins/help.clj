(ns jarvis.plugins.help
  (:require [jarvis.plugins :as plugins]
            [clojure.string :as s]
            [clj-flowdock.api.team-inbox :as inbox]
            [clj-flowdock.api.flow :as flow]
            [clj-flowdock.api.message :as message]))

(defn- build-help-string
  [plugin-meta]
  (let [primary-string (str "<code>~" (:command plugin-meta) "</code> : " (:description plugin-meta))]
    (if (empty? (:params plugin-meta))
      primary-string
      (str primary-string "<pre><b>Params:</b> " (:params plugin-meta) "<br /><b>Usage:</b> " (:usage plugin-meta) "</pre>"))))

(defn ^{:description "Prints the description of all plugins."
        :command "help"
        :author "Adam Esterline"
        :plugin true}
  help
  ([message content-vec] (help message content-vec @plugins/plugins-atom))
  ([message content-vec plugins]
     (let [plugin-meta (map meta plugins)
           sorted-plugin-meta (sort-by :command plugin-meta)
           help-text (map build-help-string sorted-plugin-meta)
           formatted-help-text (str
                                "I will only respond when directly given a <code>~<i>command</i></code> as a new thread. I will not reply to @Jarvis or threaded commands.<br />"
                                "<br /><b>All Known Plugins:</b><br /><i>Usage and parameters are shown below a command where applicable.</i><br />"
                                (s/join "<br />" help-text)
                                "<br /><code>~leave</code> : To have me leave a flow. I will need to be invited back through the flowdock api to join again."
                                "<br /><code>~<i>command</i></code> can be used also in private-messages with me."
                                "<br /><br />For more details see https://github.com/RallySoftware/jarvis-core")
           flow (flow/get (message/flow-id message))]
       (inbox/post (get flow "api_token") {:source "Jarvis" :from_address "donotreply@rallydev.com" :subject "Help" :content formatted-help-text :tags ["jarvis_help" "help_me_jarvis"]})
       (str "I have placed the items you are looking for in the team inbox, found to the left of this pane."))))
