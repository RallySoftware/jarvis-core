(ns jarvis.plugins.help
  (:require [jarvis.plugins :as plugins]
            [clojure.string :as s]))

(defn ^{:description "Prints the description of all plugins."
        :command "help"
        :author "Adam Esterline"
        :plugin true}
  help
  ([message content-vec] (help message content-vec @plugins/plugins-atom))
  ([message content-vec plugins]
    (let [plugin-meta (map meta plugins)
          sorted-plugin-meta (sort-by :command plugin-meta)
          help-text (map #(str "\t" (:command %) ":" (:description %)) sorted-plugin-meta)]
      (str
        "\n\tAll known plugins - \n"
        (s/join "\n" help-text)
        "\n\t~leave can be used to have me leave a flow. I will need to be invited back through the flowdock api to join again."
        "\n\t~<command> can be used also in private-messages with me."
        "\n\t~~<command> @<name> will private message the people listed the results of the executed command."
        "\n\tFor more details see https://github.com/RallySoftware/jarvis-core"))))
