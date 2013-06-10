package com.rallydev.jarvis;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceConnector;
import groovy.util.ResourceException;

import java.net.URL;
import java.net.URLConnection;

public abstract class Groovy {

    private static class ClasspathResourceConnector implements ResourceConnector {

        public URLConnection getResourceConnection(String name) throws ResourceException {
            try {
                if(name.startsWith("/")) {
                    return getClass().getResource(name).openConnection();
                } else {
                    return new URL(name).openConnection();
                }
            } catch(Exception e) {
                throw new ResourceException(e);
            }
        }
    }

    private static final GroovyScriptEngine SCRIPT_ENGINE = new GroovyScriptEngine(new ClasspathResourceConnector());

    public static void loadGroovyPlugin(String script) throws Exception {
        SCRIPT_ENGINE.run(script, new Binding());
    }
}
