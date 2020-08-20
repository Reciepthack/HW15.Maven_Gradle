package com.homevork15.word;

public class WordText {
    private final String string;
    private final int count;

    public WordText(String string, int count) {
        this.string = string;
        this.count = count;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getString() {
        return string;
    }

    public int getCount() {
        return count;
    }

}

