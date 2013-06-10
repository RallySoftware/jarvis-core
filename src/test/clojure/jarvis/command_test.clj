(ns jarvis.command-test
  (:use clojure.test)
  (:require [jarvis.command :as command]
    [jarvis.test-util :as test-util]))

(deftest tell-command?
  (is (= true
        (command/tell-command? (test-util/message "~~help"))))
  (is (= false
        (command/tell-command? (test-util/message "~help"))))
  (is (= false
        (command/tell-command? (test-util/message "help")))))

(deftest parse-command
  (is (= "help"
        (command/parse-command "~help")))
  (is (= "help"
        (command/parse-command "~~help")))
  (is (= nil
        (command/parse-command "help"))))

(deftest command->plugin
  (is (= "foo"
        (command/command->plugin (test-util/message "~help") {"help" "foo"})))
  (is (= "foo"
        (command/command->plugin (test-util/message "~~help") {"help" "foo"})))
  (is (= nil
        (command/command->plugin (test-util/message "help") {"help" "foo"}))))

(deftest get-user-ids-from-tags
  (is (= (list "29988" "1234" "35899"
           (command/get-user-ids-from-tags (test-util/tagged-message))))))

(deftest get-user-id-from-message
  (is (= :29983
        (command/get-user-id-from-message (test-util/private-message "~help")))))

(deftest leave-command?
  (is (= true
        (command/leave-command? (test-util/message "~leave"))))
  (is (= false
        (command/leave-command? (test-util/message "~~leave"))))
  (is (= false
        (command/leave-command? (test-util/message "~~leave foo-flow")))))

(deftest join-command?
  (is (= true
        (command/join-command? (test-util/flow-add-message))))
  (is (= false
        (command/leave-command? (test-util/message "test"))))
  (is (= false
        (command/leave-command? (test-util/private-message "foo")))))

(deftest private-message?
  (is (= true
        (command/private-message? (test-util/private-message "private message"))))
  (is (= false
        (command/leave-command? (test-util/message "test"))))
  (is (= false
        (command/leave-command? (test-util/flow-add-message)))))