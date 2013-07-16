(ns jarvis.plugins.flows)

(defn ^{:description "I will respond if I am available."
        :command "ping"
        :author "Jonathan Chauncey"
        :plugin true}
  ping [message content-vec]
  "Yes sir?")
