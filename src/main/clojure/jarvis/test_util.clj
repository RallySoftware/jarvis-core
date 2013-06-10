(ns jarvis.test-util)

(defn message [content]
  {"app" "chat",
   "parent" nil,
   "user" {"nick" "Chauncey",
           "name" "Jonathan Chauncey",
           "avatar" "https://d2cxspbh1aoie1.cloudfront.net/avatars/7d17d1f0d22a0f9fff783f0d92a7ab13/",
           "id" :29983,
           "email" "jchauncey@rallydev.com"},
   "flow" "rally-software:jarvish",
   "attachments" [],
   "sent" :1368111056178,
   "tags" [],
   "content" content,
   "event" "message",
   "id" 12655,
   "uuid" "83kTk4vQbNk3uI7v"})

(defn private-message [content]
  {"app" "chat",
   "parent" nil,
   "user" {"nick" "Chauncey",
           "name" "Jonathan Chauncey",
           "avatar" "https://d2cxspbh1aoie1.cloudfront.net/avatars/7d17d1f0d22a0f9fff783f0d92a7ab13/",
           "id" :29983,
           "email" "jchauncey@rallydev.com"},
   "flow" "rally-software:jarvish",
   "attachments" [],
   "sent" :1368111056178,
   "tags" [],
   "content" content,
   "event" "message",
   "id" 12655,
   "to" 35899
   "uuid" "83kTk4vQbNk3uI7v"})

(defn tagged-message []
  {"app" "chat",
   "parent" nil,
   "user" {"nick" "Chauncey",
           "name" "Jonathan Chauncey",
           "avatar" "https://d2cxspbh1aoie1.cloudfront.net/avatars/7d17d1f0d22a0f9fff783f0d92a7ab13/",
           "id" :29983,
           "email" "jchauncey@rallydev.com"},
   "flow" "rally-software:jarvish",
   "attachments" [],
   "sent" :1368111056178,
   "tags" [":user:29988" ":user:1234" ":unread:29988" ":user:35899" ":unread:35899"],
   "content" "~~help @Fatt",
   "event" "message",
   "id" 12655,
   "uuid" "83kTk4vQbNk3uI7v"})

(defn tagged-message-with-content [content]
  {"app" "chat",
   "parent" nil,
   "user" {"nick" "Chauncey",
           "name" "Jonathan Chauncey",
           "avatar" "https://d2cxspbh1aoie1.cloudfront.net/avatars/7d17d1f0d22a0f9fff783f0d92a7ab13/",
           "id" :29983,
           "email" "jchauncey@rallydev.com"},
   "flow" "rally-software:jarvish",
   "attachments" [],
   "sent" :1368111056178,
   "tags" [":user:29988" ":user:1234" ":unread:29988" ":user:35899" ":unread:35899"],
   "content" content,
   "event" "message",
   "id" 12655,
   "uuid" "83kTk4vQbNk3uI7v"})

(defn flow-add-message []
  {"user" 35899,
   "event" "flow-add",
   "content" {"id" "rally-software:devellopers",
              "name" "developers",
              "organization" "Rally Software",
              "open" true}})