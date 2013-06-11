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
See [INTRO](https://github.com/RallySoftware/jarvis-core/blob/master/INTRO.md) for more information.

## Interacting with Jarvis
* ~\<command\> - This will execute any plugin found that is registered with that command. It will print the reply text back to the flow it was executed in.
* ~~\<command\> \<list of @usernames\> - This will private message the reply of the command to the list of users provided.
* ~leave - Will block jarvis from the flow it is executed in as well as kill the thread that is listening for messages in that flow.
* To have jarvis join a private flow - first use the flowdock ui to invite him to the flow. If he does not auto join the flow issue the ~join command with the flow you wish for him to join. This may need to be done over a private message.
* ~list-flows will print all flows that jarvis is currently listening to.

## Pluggins shipped with jarvis-core
* ~help - Prints the descriptions of each plugin that has been loaded.
* ~join - joins the specified flows (if he can).
* ~list-flows - list all of the flows that jarvis is currently listening to.

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
