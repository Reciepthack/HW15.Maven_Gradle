package com.homevork15.word;

public class WordText {
    private final String string;
    private final int count;

    public String getString() {
        return string;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public WordText(String string, int count) {
        this.string = string;
        this.count = count;
    }
}
