# Introduction to Jarvis

## Command lifecycle

### Step 0 - Commence Primary Ignition
When jarvis is initialized it performs several actions before it starts listening for messages.

* Load all plugins in the classpath that match the pattern \*/jarvis/plugins/\*
* Create a map of command -> plugin (the plugin is actually the function that will be executed)
* Create a thread pool
* Initialize 1 thread per flow for processing messages
* Initialize thread for reading private messages and global flow notifications

### Step 1 - Alert all commands. Calculate every possible destination along their last known trajectory.
Let's assume that the message we are processing is from an actual flow and not a private message. It may look like the 
following - 

```clojure
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
   "content" "~help",
   "event" "message",
   "id" 12655,
   "uuid" "83kTk4vQbNk3uI7v"}
```

First, we have to determine that the message is not telling Jarvis to leave a flow. So we execute the leave-command? 
function, if it returns true we close the flow connection and stop the thread from processing any more messages. If not,
we call the invoke-plugin function passing in the message and the map of plugins.

By this point, we still do not know if we are executing a command or not (we just know that we are not leaving a flow).
So we check to see if the message contains a command we know about using the command->plugin function. This function will
return a plugin if a match is found or nil otherwise. 

### Step 2 -  I've made a lot of special modifications myself.
Just passing around the raw message was often cumbersome in the early stages of development of Jarvis. So we decided that
we should enhance the message slightly and include the full user profile as well as the parent message (if there is one).
This allows us to determine context if the plugin is being executed in a reply of a conversation as well as who actually
executed the plugin.

The message above has the enhanced user attribute.

### Step 3 - Move the ship out of the asteroid field so that we can send a clear transmission.
Now we have to determine if the message is to a normal reply or was issued as a tell command (~~). In either case the resulting
function call takes both the enhanced message and the plugin. 

First lets assume that a normal reply is in order, then all we have to do is execute the plugin (which takes the enchanced 
message as a parameter) storing off the reply that is returned. At this point we call off to the clj-flowdock api to send 
reply to the flow.

If the message was a tell command (~~) then the only difference is that we need to parse out the list of users to private
message from the message tags.

```clojure
"tags" [":user:29988" ":user:1234" ":unread:29988" ":user:35899" ":unread:35899"],
```





