package com.batook.ex2.utils;

import java.io.Serializable;

public class Banner implements Serializable {

    private String line;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "Banner: " + line;
    }
}

