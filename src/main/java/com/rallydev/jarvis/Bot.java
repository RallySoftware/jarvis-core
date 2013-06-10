package com.rallydev.jarvis;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;
import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;

public abstract class Bot {
    public static void addPlugin(Object plugin, Map metadata) {
        ADD_PLUGIN.invoke(plugin, metadata);
    }

    public static void addCommand(String commandName, String description, String author, final Closure callback) {
        HashMap<String, String> map = new HashMap<String,String>();
        map.put("command", commandName);
        map.put("description", description);
        map.put("author", author);

        addPlugin(new GroovyCommand(callback, commandName), map);
    }

    private static final Var REQUIRE = RT.var("clojure.core", "require");
    static {
        REQUIRE.invoke(Symbol.intern("jarvis.plugins"));
    }
    private static final Var ADD_PLUGIN = RT.var("jarvis.plugins", "add-plugin");

    private static class GroovyCommand implements Plugin {
        private final Closure callback;
        private final String command;

        private GroovyCommand(Closure callback, String command) {
            this.callback = callback;
            this.command = command;
        }

        public Object invoke(Map message) {
            return callback.call(message);
        }
    }
}
