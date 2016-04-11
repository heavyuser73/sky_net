package com.syaku.security;

public class Result {

    private final String id;
    private final String content;

    public Result(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
