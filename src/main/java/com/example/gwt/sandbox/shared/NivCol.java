package com.example.gwt.sandbox.shared;

import java.io.Serializable;

public class NivCol implements Serializable {

    private int numCol;
    private int[] numsTache = new int[0];


    public NivCol() {
    }

    public NivCol(int numCol) {
        this.numCol = numCol;

    }

    public int getNumCol() {
        return numCol;
    }

    public int[] getNumsTache() {
        return numsTache;
    }

    public void ajoutTache(int numTache){
       int[] t = new int[numsTache.length + 1];
       for(int i = 0; i < numsTache.length; i ++){
           t[i] = numsTache[i];
       }
       t[numsTache.length] = numTache;
       numsTache = t;
    }
}


