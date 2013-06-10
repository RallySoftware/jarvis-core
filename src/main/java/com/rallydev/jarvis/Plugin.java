package com.rallydev.jarvis;

import java.util.Map;

public interface Plugin {
    public Object invoke(Map message);
}