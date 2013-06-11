# How to create a plugin for jarvis

## Plugin metadata
All plugins set some amount of metadata during the loading phase of jarvis. This metadata allows Jarvis to quickly find
which plugins to execute or even print out a helpful description in the help plugin. 

```clojure
^{:description "Prints the description of all plugins."
  :command "help"
  :author "Adam Esterline"
  :plugin true}
```

In groovy when you create a command, the first param is the command, second is the description, and the third is the author.
```groovy
Bot.addCommand("hello", "Simple Hello from Groovy", "Adam Esterline")
```

## Message -> String
When jarvis executes a plugin it only passes in 1 parameter and that is the enchanced message received from the flow. 
All plugins must return a string that will be used as the content of the reply message.

```clojure
(defn my-awesome-plugin [message]
  "my awesome reply")
```

```groovy
Bot.addCommand("hello", "Simple Hello from Groovy", "Adam Esterline") { message ->
    "Hello from Groovy"
}
```

## \*/jarvis/plugins/\*
When jarvis starts it will scan the entire classpath (including jars) looking for plugins in the following path. This allows
for plugin development to be done outside of the jarvis environment and then be included as a versioned depenency in your
jarvis runtime. 







