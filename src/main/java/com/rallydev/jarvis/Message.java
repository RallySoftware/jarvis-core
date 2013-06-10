package com.rallydev.jarvis;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public abstract class Message {
    public static String parseCommand(Object message) {
        return (String) PARSE_COMMAND.invoke(message);
    }

    private static final Var REQUIRE = RT.var("clojure.core", "require");
    static {
        REQUIRE.invoke(Symbol.intern("jarvis.command"));
    }
    private static final Var PARSE_COMMAND = RT.var("jarvis.command", "parse-command");

}
