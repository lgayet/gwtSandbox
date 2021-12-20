package com.example.gwt.sandbox.client.component;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.TextBox;

import java.util.logging.Logger;

public abstract class ChampNumeriqueFormate extends TextBox {

    public ChampNumeriqueFormate(int nbCharMax) {
        this.addKeyPressHandler(event -> {

            int keyCode = event.getNativeEvent().getKeyCode();

            String value = getValue();

            if (!isKeyNumerique(keyCode) || !autoriseCaractereSaisi(value, event.getCharCode())) {
                event.stopPropagation();
                this.cancelKey();
                return;
            }

            setValue(modifyValueAvantSaisie(value));
        });

        this.addKeyUpHandler(event -> {

            if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE) return;

            setValue(modifyValueApresSaisie(getValue()));
        });

        setMaxLength(nbCharMax);
    }

    protected abstract boolean autoriseCaractereSaisi(String value, char charCode);

    protected abstract String modifyValueAvantSaisie(String value);

    protected abstract String modifyValueApresSaisie(String value);


    private boolean isKeyNumerique(int keyCode) {
        return (keyCode >= KeyCodes.KEY_ZERO && keyCode <= KeyCodes.KEY_NINE);
    }
}