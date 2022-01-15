package com.example.gwt.sandbox.client.component;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.TextBox;

import javax.validation.constraints.NotNull;

public abstract class ChampNumeriqueFormate extends TextBox {

    private static final String EMPTY = "";

    private static final String BCK_RED_COLOR = "#f8cfcf";
    private static final String BCK_WHITE_COLOR = "white";

    protected final String mask;
    protected final String startValue;
    private final RegExp regExp;


    public ChampNumeriqueFormate(String mask) { this(mask, EMPTY);}

    public ChampNumeriqueFormate(String mask, String startValue) {

        this.mask = mask != null ? mask : EMPTY;
        this.startValue = startValue != null ? startValue : EMPTY;
        this.regExp = RegExp.compile(this.mask.replaceAll("_", "\\\\d"));

        this.setValue(mask);
        this.setMaxLength(mask.length());

        this.addFocusHandler(focusEvent -> {
            if (this.mask.equals(getText())) setText(startValue);
        });

        this.addBlurHandler(blurEvent -> {
            String backgroundColor = BCK_WHITE_COLOR;
            if (EMPTY.equals(getText()) || startValue.equals(getText())) {
                setText(this.mask);
            } else if (!isValidValue()) {
                backgroundColor = BCK_RED_COLOR;
            }
            getElement().getStyle().setBackgroundColor(backgroundColor);
        });

        this.addKeyPressHandler(event -> {

            String value = getValue();
            if (!isKeyNumerique(event.getNativeEvent().getKeyCode())
                    || !autoriseCaractereSaisi(value, event.getCharCode())) {
                event.stopPropagation();
                this.cancelKey();
                return;
            }
            setValue(modifieValeurSaisie(value, event.getCharCode()));
        });

        this.addKeyUpHandler(event -> {
            if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE) return;
            setValue(modifieValeurSaisie(getValue(), '_'));
        });
    }

    @Override
    public String getValue() {
        return this.mask.equals(super.getValue()) ? EMPTY : super.getValue();
    }

    protected abstract boolean autoriseCaractereSaisi(String value, char charCode);

    protected abstract String modifieValeurSaisie(String value, char charCode);

    private boolean isKeyNumerique(int keyCode) {
        return (keyCode >= KeyCodes.KEY_ZERO && keyCode <= KeyCodes.KEY_NINE);
    }

    private boolean isValidValue() {
        return regExp.exec(getText()) != null;
    }
}
