package com.example.gwt.sandbox.client.component;

import java.util.logging.Logger;

public class ChampHeureMinute extends ChampNumeriqueFormate {

    private static final Logger LOGGER = Logger.getLogger(ChampHeureMinute.class.getName());
    private static final int NB_CHAR_MAX = 5;

    private static final Character SEPARATOR = ':';

    public ChampHeureMinute() {
        super(5);
    }

    @Override
    protected boolean autoriseCaractereSaisi(String value, char charCode) {

        if (value.isEmpty() && !isCharAutorisePourDizaineHeure(charCode)){
            LOGGER.finest("la valeur pour la dizaine heure n'est pas admise " + charCode);
            return false;
        }

        if (value.equals("2") && !isCharAutorisePourUniteHeureQuandDizaineHeureEst2(charCode)){
            LOGGER.finest("la valeur pour l'unité heure n'est pas admise " + charCode);
            return false;
        }

        if ((value.length() == 2 && charCode != SEPARATOR) || value.length() == 3) {
            if (!isCharAutorisePourDizaineMinute(charCode)) {
                LOGGER.finest("la valeur pour la dizaine minute n'est pas admise " + charCode);
                return false;
            }
        }
        return true;
    }

    @Override
    protected String modifyValueAvantSaisie(String value) {
        return value.length() == 2 ? value + SEPARATOR : value;
    }

    @Override
    protected String modifyValueApresSaisie(String value) {

        if (value.equals("24")) {
            return value + SEPARATOR + "00";
        }

        if (value.length() == 2) {
            return value + SEPARATOR;
        }

        return value;
    }

    private boolean isCharAutorisePourDizaineHeure(char charCode) {
        return (charCode >= '0'  && charCode <= '2');
    }

    private boolean isCharAutorisePourUniteHeureQuandDizaineHeureEst2(char charCode) {
        return (charCode >= '0' && charCode <= '4');
    }

    private boolean isCharAutorisePourDizaineMinute(char charCode) {
        return (charCode >= '0' && charCode <= '5');
    }
}
