package com.example.gwt.sandbox.server;

import com.example.gwt.sandbox.shared.calendar.Colonne;
import com.example.gwt.sandbox.shared.calendar.Salarie;
import com.example.gwt.sandbox.shared.calendar.Selection;
import com.example.gwt.sandbox.shared.calendar.Tache;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class SelectionBuilder implements Serializable {

    private transient ArrayList<Tache> aTaches = new ArrayList<>();
    private LocalDate debut;

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
        aTaches.add(generationTacheFixe(selection,tSals[1], 2022,8,2,8,0,2022,8,2,13,45));
        aTaches.add(generationTacheFixe(selection,tSals[1], 2022,8,2,10,0,2022,8,2,16,0));
        aTaches.add(generationTacheFixe(selection,tSals[1], 2022,8,2,9,0,2022,8,2,11,30));
        aTaches.add(generationTacheFixe(selection,tSals[1], 2022,8,2,16,30,2022,8,2,20,0));
        aTaches.add(generationTacheFixe(selection,tSals[0], 2022,8,3,6,0,2022,8,4,10,0));
        aTaches.add(generationTacheFixe(selection,tSals[0], 2022,8,3,7,30,2022,8,3,8,0));
        aTaches.add(generationTacheFixe(selection,tSals[0], 2022,8,3,10,0,2022,8,3,11,0));
        aTaches.add(generationTacheFixe(selection,tSals[0], 2022,8,3,14,0,2022,8,3,16,0));
        for(Salarie sal: tSals){
            for(int i = 0; i < 50; i++) {
                aTaches.add(generationTache(selection, sal));
            }
        }
        selection.setTSals(tSals);
        for(Salarie sa: tSals){
//            sa.setTInterSal();//TODO a voir en fonction des performances d'affichage
        }
        selection.setTTache(aTaches.toArray(new Tache[aTaches.size()]));
        return selection;

    }

    
    public Salarie[] creationSalaries(Selection selection, int nbJours){
        String[]noms = {"Salarié 1","Salarié 2","Salarié 3","Salarié 4","Salarié 5"};
        Salarie[] tSals = new Salarie[noms.length];
        for(int i = 0; i< noms.length; i++){
            tSals[i] = new Salarie(i, noms[i], nbJours);
        }
        return tSals;
    }

    private Tache generationTacheFixe(Selection selection, Salarie salarie, int annee, int mois, int jour, int heure, int minute, int anneeFin, int moisFin, int jourFin, int hFin, int mnFin){
        LocalDateTime d = LocalDateTime.of(annee,mois,jour,heure,minute);
        LocalDateTime d2 = LocalDateTime.of(anneeFin,moisFin,jourFin,hFin,mnFin);
        LocalDate d3 = LocalDate.of(annee,mois,jour);
        LocalDate d4 = LocalDate.of(anneeFin,moisFin,jourFin);
        int numColDeb = (int)(d3.toEpochDay() - debut.toEpochDay());
        int numColFin = (int)(d4.toEpochDay() - debut.toEpochDay());

        Tache t = selection.ajoutTache(d.getYear(), d.getMonthValue(), d.getDayOfMonth(), d.getHour(), d.getMinute(),
                debut.until(d3).getDays(),
                d2.getYear(), d2.getMonthValue(), d2.getDayOfMonth(), d2.getHour(), d2.getMinute(),
                debut.until(d4).getDays(),
                numColDeb, numColFin);
        salarie.ajoutTaches(t);
        return t;
    }


    private Tache generationTache(Selection selection, Salarie salarie){
        LocalDateTime dRef = LocalDateTime.of(debut.getYear(), debut.getMonthValue(), debut.getDayOfMonth(),0,0);
        dRef = dRef.withDayOfMonth(1);
        dRef = positionnerPremierLundi(dRef);//TODO pour pouvoir ajouter les joursSemaine si le mois ne commence pas par un lundi
        LocalDateTime d;
        LocalDateTime d2;
        LocalDate d3;
        LocalDate d4;
        d = dRef;
        d = d.plusDays(genererInt(0,5));
        d = d.plusWeeks(genererInt(0,13));
        d = d.plusHours(genererInt(6,19));
        d = d.plusMinutes(genererInt(0,6) * 10);
        d2 = d;
        d2 = d2.plusMinutes(genererInt(0,30) * 10);
        d3 = LocalDate.of(d.getYear(), d.getMonthValue(), d.getDayOfMonth());
        d4 = LocalDate.of(d2.getYear(), d2.getMonthValue(), d2.getDayOfMonth());
        int numColDeb = (int)(d3.toEpochDay() - debut.toEpochDay());
        int numColFin = (int)(d4.toEpochDay() - debut.toEpochDay());
        Tache t = selection.ajoutTache(d.getYear(), d.getMonthValue(), d.getDayOfMonth(), d.getHour(), d.getMinute(),
                debut.until(d3).getDays(),
                d2.getYear(), d2.getMonthValue(), d2.getDayOfMonth(), d2.getHour(), d2.getMinute(),
                debut.until(d4).getDays(),
                numColDeb, numColFin);
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
