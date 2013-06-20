(ns jarvis.plugins.flows)

(defn ^{:description "ping - I will respond if I am available."
        :command "ping"
        :author "Jonathan Chauncey"
        :plugin true}
  ping [message]
  "Yes sir?")
