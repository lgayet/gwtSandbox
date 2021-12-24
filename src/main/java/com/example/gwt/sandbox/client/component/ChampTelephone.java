package com.example.gwt.sandbox.client.component;

import java.util.logging.Logger;

public class ChampTelephone extends ChampNumeriqueFormate {

    private static final Logger LOGGER = Logger.getLogger(ChampTelephone.class.getName());

    private static final Character SEPARATOR = ' ';

    public ChampTelephone() {
        super("0_ __ __ __ __", "0");
    }

    @Override
    protected boolean autoriseCaractereSaisi(String value, char charCode) {

        if (value.isEmpty() && !isCharAutorisePourPremierChiffre(charCode)) {
            LOGGER.finest("la valeur pour le premier chiffre n'est pas admise " + charCode);
            return false;
        }

        if (value.length() == 1 && !isCharAutorisePourDeuxiemeChiffre(charCode)) {
            LOGGER.finest("la valeur pour le deuxième chiffre n'est pas admise " + charCode);
            return false;
        }
        return true;
    }

    @Override
    protected String modifieValeurSaisie(String value, char charCode) {

        if (value.isEmpty() && charCode != '0') return "0";

        return (value.length() == 2  || (value.length() > 2 && value.length() < 14 && value.length() % 3 == 2))
                ? value + SEPARATOR
                : value;
    }

    private boolean isCharAutorisePourPremierChiffre(char charCode) {
        return charCode == '0';
    }

    private boolean isCharAutorisePourDeuxiemeChiffre(char charCode) {
        return charCode >= '1' && charCode <= '9';
    }
}
