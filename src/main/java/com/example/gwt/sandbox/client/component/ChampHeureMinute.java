package com.example.gwt.sandbox.client.component;

import java.util.logging.Logger;

public class ChampHeureMinute extends ChampNumeriqueFormate {

    private static final Logger LOGGER = Logger.getLogger(ChampHeureMinute.class.getName());

    private static final Character SEPARATOR = ':';

    public ChampHeureMinute() {
        super("__:__");
    }

    @Override
    protected boolean autoriseCaractereSaisi(String value, char charCode) {

        if (value.isEmpty() && !isCharAutorisePourDizaineHeure(charCode)) {
            LOGGER.finest("la valeur pour la dizaine heure n'est pas admise " + charCode);
            return false;
        }

        if (value.equals("2") && !isCharAutorisePourUniteHeureQuandDizaineHeureEst2(charCode)) {
            LOGGER.finest("la valeur pour l'unité heure n'est pas admise " + charCode);
            return false;
        }

        if ((value.length() == 2 && charCode != SEPARATOR) || value.length() == 3) {
            if (!isCharAutorisePourDizaineMinute(charCode) || value.equals("24:")) {
                LOGGER.finest("la valeur pour la dizaine minute n'est pas admise " + charCode);
                return false;
            }
        }
        return true;
    }

    @Override
    protected String modifieValeurSaisie(String value, char charCode) {

        if (value.equals("24")) return value + SEPARATOR + "00";

        if (value.equals("24:")) return value + "00";

        return value.length() == 2 ? value + SEPARATOR : value;
    }

    private boolean isCharAutorisePourDizaineHeure(char charCode) {
        return charCode >= '0' && charCode <= '2';
    }

    private boolean isCharAutorisePourUniteHeureQuandDizaineHeureEst2(char charCode) {
        return charCode >= '0' && charCode <= '4';
    }

    private boolean isCharAutorisePourDizaineMinute(char charCode) {
        return charCode >= '0' && charCode <= '5';
    }
}
