package com.example.gwt.sandbox.shared;

import java.io.Serializable;

public class Selection2 implements Serializable
{
    private String label;

    public Selection2() {
    }

    public Selection2(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
