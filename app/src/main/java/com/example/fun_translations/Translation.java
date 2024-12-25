package com.example.fun_translations;

public class Translation {
    private final String message;
    private final String author;

    public Translation(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
