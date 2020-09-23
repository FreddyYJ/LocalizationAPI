package com.github.freddyyj.localizationapi;

public enum DefaultKey {
    NAME("language.name"),
    REGION("language.region"),
    CODE("language.code");
    private final String value;
    DefaultKey(String value){
        this.value=value;
    }

    @Override
    public String toString() {
        return value;
    }
}
