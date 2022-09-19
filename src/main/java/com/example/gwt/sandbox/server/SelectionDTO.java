package com.example.gwt.sandbox.server;

import com.example.gwt.sandbox.client.Colonne;
import com.example.gwt.sandbox.shared.Salarie;
import com.example.gwt.sandbox.shared.Selection;
import com.example.gwt.sandbox.shared.Tache;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Random;

public class SelectionDTO implements Serializable {

    private transient ArrayList<Tache> aTaches = new ArrayList<>();
    private LocalDate debut;
    public SelectionDTO(){
        
    }


    
    public Selection construitSelection(int anneeDebut, int moisDebut, int anneeFin, int moisFin){
        Selection selection = new Selection(anneeDebut, moisDebut, anneeFin, moisFin);
        debut = LocalDate.of(anneeDebut,moisDebut,1);
        LocalDate fin = LocalDate.of(anneeFin,moisFin,1);
        fin = fin.plusMonths(1);
        fin = fin.plusDays(-1);//TODO pour se placer sur le dernier jour de moisFin
        int nbJours = (int) (fin.toEpochDay() - debut.toEpochDay()  + 1);
        String s = nbJours+"";
        Colonne[] tCols = new Colonne[nbJours];
        LocalDate d = debut;
        for(int i = 0; i < nbJours; i ++){
                tCols[i] = new Colonne(i,d.getYear(), d.getMonthValue(), d.getDayOfMonth(), d.getDayOfWeek().getValue());
                d = d.plusDays(1);
        }
        System.out.println("nbJours= "+nbJours);
        selection.setNbJours(nbJours);
        selection.setTCols(tCols);
        Salarie[] tSals = creationSalaries(selection, nbJours);
        aTaches.add(generationTache(selection,tSals[0]));
//        for(Salarie sal: tSals){
//            for(int i = 0; i < 10; i++) {
//                aTaches.add(generationTache(selection, sal));
//            }
//            sal.creTTaches();
//        }
        selection.setTSals(tSals);
        selection.setTTache(aTaches.toArray(new Tache[aTaches.size()]));
        return selection;

    }

    
    public Salarie[] creationSalaries(Selection selection, int nbJours){
        String[]noms = {"Salarié 1","Salarié 2","Salarié 3","Salarié 4","Salarié 5"};
        Salarie[] tSals = new Salarie[noms.length];
        for(int i = 0; i< noms.length; i++){
            tSals[i] = new Salarie(selection, i, noms[i], nbJours);
        }
        return tSals;
    }


    private Tache generationTache(Selection selection, Salarie salarie){
        LocalDateTime dRef = LocalDateTime.of(debut.getYear(), debut.getMonthValue(), debut.getDayOfMonth(),0,0);
        dRef = dRef.withDayOfMonth(1);
        dRef = positionnerPremierLundi(dRef);//TODO pour pouvoir ajouter les joursSemaine si le mois ne commence pas par un lundi
        LocalDateTime d;
        LocalDateTime d2;
        LocalDate d3;
        d = dRef;
        d = d.plusDays(genererInt(1,6));
        d = d.plusWeeks(genererInt(0,13));
        d = d.plusHours(genererInt(6,19));
        d = d.plusMinutes(genererInt(0,6) * 10);
        d2 = d;
        d2 = d2.plusHours(genererInt(1,7));
        d2 = d2.plusMinutes(genererInt(0,6) * 10);
        d3 = LocalDate.of(d.getYear(), d.getMonthValue(), d.getDayOfMonth());
        int numCol = (int)(d3.toEpochDay() - debut.toEpochDay());
        Tache t = new Tache(selection.getAndIncrNumTache(), salarie.getNumSal(), d.getYear(), d.getMonthValue(), d.getDayOfYear(), d.getMinute(), d.getSecond(), d.getLong(ChronoField.NANO_OF_DAY),d2.getYear(), d2.getMonthValue(), d2.getDayOfYear(), d2.getMinute(), d2.getSecond(), d2.getLong(ChronoField.NANO_OF_DAY), numCol);
        salarie.ajoutTaches(t);
        return t;
    }

    private LocalDateTime positionnerPremierLundi(LocalDateTime dat){
        LocalDateTime d = dat;
        for(int i = 0; i < 7; i ++){
            if(d.getDayOfWeek().getValue() == 1)return d;
            d = d.plusDays(1);
        }
        return null;
    }

    private int genererInt(int borneInf, int borneSup){
        Random random = new Random();
        int nb;
        nb = borneInf+random.nextInt(borneSup-borneInf);
        return nb;
    }
}
