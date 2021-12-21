package com.example.gwt.sandbox.client.component;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.TextBox;

public abstract class ChampNumeriqueFormate extends TextBox {

    public ChampNumeriqueFormate(int nbCharMax) {
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

        setMaxLength(nbCharMax);
    }

    protected abstract boolean autoriseCaractereSaisi(String value, char charCode);

    protected abstract String modifieValeurSaisie(String value, char charCode);

    private boolean isKeyNumerique(int keyCode) {
        return (keyCode >= KeyCodes.KEY_ZERO && keyCode <= KeyCodes.KEY_NINE);
    }
}
