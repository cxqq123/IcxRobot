package com.example.cx.icxrobot.http;

/**
 * Created by cx
 * on 2018/8/27
 */
public class NameAndValue {

    public String name;
    public String value;

    public NameAndValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
