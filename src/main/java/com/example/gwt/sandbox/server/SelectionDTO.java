package com.example.gwt.sandbox.server;

import com.example.gwt.sandbox.client.Colonne;
import com.example.gwt.sandbox.shared.Selection;
import com.example.gwt.sandbox.shared.Selection2;

import java.io.Serializable;
import java.time.LocalDate;

public class SelectionDTO implements Serializable {

    
    public SelectionDTO(){
        
    }


    
    public Selection construitSelection(int anneeDebut, int moisDebut, int anneeFin, int moisFin){

        LocalDate debut = LocalDate.of(anneeDebut,moisDebut,1);
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
        return new Selection( anneeDebut,  moisDebut, anneeFin, moisFin, nbJours, tCols);

    }

    public Selection2 construitJours(){
        LocalDate debut = LocalDate.of(2022,8,1);
        LocalDate fin = LocalDate.of(2022,10,1);
        fin = fin.plusMonths(1);
        fin = fin.plusDays(-1);//TODO pour se placer sur le dernier jour de moisFin
        int nbJours = (int) (fin.toEpochDay() - debut.toEpochDay()  + 1);
        String s = nbJours+"";
        return new Selection2(s);
    }
    
    
}