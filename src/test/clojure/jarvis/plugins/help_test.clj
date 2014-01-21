;; (ns jarvis.plugins.help-test
;;   (:use clojure.test)
;;   (:require [jarvis.plugins.help :as help]
;;             [jarvis.test-util :as test-util]))

;; (defn
;;   ^{:description "foo" :command "bar"}
;;   test-plugin [message]
;;   "baz")

;; (defn
;;   ^{:description "yo" :command "dawg"}
;;   yo-plugin [message]
;;   "happy")

;; (deftest help
;;   (is (= (str "\n\tAll known plugins - "
;;            "\n\n\t~bar foo"
;;            "\n\n\t~leave can be used to have me leave a flow. I will need to be invited back through the flowdock api to join again."
;;            "\n\n\t~<command> can be used also in private-messages with me."
;;            "\n\n\t~~<command> @<name> will private message the people listed the results of the executed command."
;;            "\n\n\tFor more details see https://github.com/RallySoftware/jarvis-core")
;;         (help/help (test-util/message "~help") ["help"] [#'test-plugin])))
;;   (is (= (str "\n\tAll known plugins - "
;;            "\n\t~bar foo"
;;            "\n\t~dawg yo"
;;            "\n\t~leave can be used to have me leave a flow. I will need to be invited back through the flowdock api to join again."
;;            "\n\t~<command> can be used also in private-messages with me."
;;            "\n\t~~<command> @<name> will private message the people listed the results of the executed command."
;;            "\n\tFor more details see https://github.com/RallySoftware/jarvis-core")
;;         (help/help (test-util/message "~help") ["help"] [#'test-plugin #'yo-plugin]))))
