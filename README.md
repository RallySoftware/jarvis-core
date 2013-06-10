[![Build Status](https://travis-ci.org/RallySoftware/jarvis-core.png)](https://travis-ci.org/RallySoftware/jarvis-core)

# jarvis-core

Jarvis core is the runtime library used when creating a Jarvis instance. It contains all the necessary components to scan for plugins and initialize threads for interacting with flows. For more information on running an instance of
Jarvis please see the [readme](http://github.com/RallySoftware/jarvis).

## Leiningen
https://clojars.org/com.rallydev/jarvis-core
```clojure
[com.rallydev/jarvis-core "1.0.0"]
```

## Maven
```xml
<dependency>
  <groupId>com.rallydev</groupId>
  <artifactId>jarvis-core</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Environment Variables
```bash
export FLOWDOCK_TOKEN=<user flowdock token>
export THREAD_POOL_SIZE=<number of threads to give jarvis -- defaults to 100>
```

## Running jarvis
```clojure
(jarvis.bot/init)
```

This will initialize jarvis in the following manner -
* Scan the classpath looking for files that match the pattern */jarvis/plugins/.
* Return a map of all plugins found where the key is the command name and the value is the plugin function.
* Initialize all threads (1 per flow) to use the streaming api.
* Also initialize a thread to read the user flow (user=1). This will allow jarvis to answer private messages.

## Interacting with Jarvis
* ~\<command\> - This will execute any plugin found that is registered with that command. It will print the reply text back to the flow it was executed in.
* ~~\<command\> \<list of @usernames\> - This will private message the reply of the command to the list of users provided.
* ~leave - Will block jarvis from the flow it is executed in as well as kill the thread that is listening for messages in that flow.
* To have jarvis join a private flow - first use the flowdock ui to invite him to the flow. If he does not auto join the flow issue the ~join command with the flow you wish for him to join. This may need to be done over a private message.
* ~list-flows will print all flows that jarvis is currently listening to.

## Developing plugins
* All plugins must live in a directory structured in the following pattern */jarvis/plugins/*
* Must be on the classpath when jarvis is started 
* .clj, .class, and .groovy files
* Should honor the Plugin contract established in Plugin.java or in plugins.clj - 
  This means it must contain the appropriate metadata for each plugin.
* Plugins must return a string. The string returned is the message that is delivered back to the user in flowdock.

### How to write a plugin
```clojure
(ns my-cool-plugin.do-something-awesome)

(defn ^{:description "This plugin does something awesome!"
        :command "awesome"
        :author "Jonathan Chauncey"
        :plugin true}
  awesome [message]
  ("I return an awesome string to be printed!"))
```

Plugins return a string that is sent back to flowdock as either a reply in a flow or a private message.

## Dependencies
```clojure
[org.clojure/clojure "1.5.0"]
[com.rallydev/clj-flowdock "1.1.0"]
[org.codehaus.groovy/groovy-all "2.1.0"]
[org.clojure/java.classpath "0.2.0"]
[org.clojure/tools.logging "0.2.6"]
[clj-http "0.7.2"]
[fs "1.3.2"]
[ch.qos.logback/logback-classic "1.0.9"]
```

# License
Copyright (c) Rally Software Development Corp. 2013  
Distributed under the MIT License.
