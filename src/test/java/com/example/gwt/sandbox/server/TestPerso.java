package com.example.gwt.sandbox.server;

import com.example.gwt.sandbox.client.calendar.GButtonChoixAffichage.ChoixAffichage;
import com.example.gwt.sandbox.shared.calendar.Colonne;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;

public class TestPerso {

    boolean booleen;
    int entier;
    Integer entierW;
    Boolean aBoolean = true;
    String[]tJours = {"XXX","Dim","Lun","Mar","Mer","Jeu","Ven","Sam"};
    String[]tMois ={"Jan","Fev","Mar","Avr","Mai","Juin","Juil","Aou","Sep","Oct","Nov","Dec"};
    public static final DateFormat DATEHEURE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("fr"));
    public static final DateFormat DATEIHM = new SimpleDateFormat("dd/MM/yyyy", new Locale("fr"));
    public static final DateFormat DATE = new SimpleDateFormat("yyyy-MM-dd", new Locale("fr"));
    private static final Logger logger = Logger.getLogger(TestPerso.class.getName());

    @Test
    public void testBoolean() {
        assertThat(booleen).isFalse();
    }

    @Test
    public void testEntier() {
        assertThat(entier).isEqualTo(0);
        assertThat(entierW).isNull();
        if(aBoolean == true)aBoolean = false;
    }

    @Test
    public void testDoubleInt(){
        double d = Double.valueOf(100+"");//TODO: OK mais inutile
        String s = "d= "+d;
        double a = (double)100 * 2.0;//TODO OK mais inutile
        double b = 100 * 2.0 / 3.0 + 5;// TODO: les arguments sont passés en double avant calcul
        int c = (int) (100 * 2.0 / 3.0 + 5);// TODO il faut faire une conversion explicite, sinon le compilateur n'accepte pas
        int e = (int) Math.round(100 * 2.0 / 3.0 + 5);
    }

    @Test
    public void testIntDouble(){
        int i = (int) 100.0;
        logger.finest("i= "+i);
    }

    @Test
    public void testCalendard(){
        Calendar cal = Calendar.getInstance(new Locale("fr"));
        cal.setFirstDayOfWeek(2);// TODO: pour que la semaine commence le lundi (numérotée à partir du lundi)
        try {
            cal.setTime(DATEIHM.parse("01/09/2022"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateCourante = DATEHEURE.format(cal.getTime());
        String jour;
        for(int i = 0; i<100; i ++){
            int numJourSem = cal.get(Calendar.DAY_OF_WEEK);
            jour = tJours[numJourSem]+" "+DATEIHM.format(cal.getTime());
            int numJourMois = cal.get(Calendar.DAY_OF_MONTH);
            int numSem = cal.get(Calendar.WEEK_OF_YEAR);
            int numMois = cal.get(Calendar.MONTH);
            int nbJoursMois = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            logger.finest("dateCourante= "+jour+" "+tMois[numMois]+" numSem= "+numSem+" "+cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void testLocalDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = LocalDate.of(2022,9,13);
        String form = "dd.MMMM.yyyy";
        DateTimeFormatter format = DateTimeFormatter.ofPattern(form);
        String jour;
        DayOfWeek jourSem;
        int numJourSem;
        for(int i = 0; i< 100; i ++){
            jour = dateTime.format(format);
            jourSem = dateTime.getDayOfWeek();
            numJourSem = jourSem.getValue();
            dateTime = dateTime.plusDays(-1);
        }
    }

    @Test
    public void test2LocalDate(){//TODO recherche du nombre de jours entre 2 dates
        LocalDate debut = LocalDate.of(2022,8,1);
        LocalDate fin = LocalDate.of(2022,10,31);
        Period inter = fin.until(debut);
        int nbJours = inter.getDays();
    }

    @Test
    public void test2bLocalDate(){//TODO recherche du nombre de jours entre 2 dates
        LocalDate debut = LocalDate.of(2022,8,1);
        LocalDate fin = LocalDate.of(2022,10,31);
        long nbJours = fin.toEpochDay() - debut.toEpochDay();
    }

    @Test
    public void test2cLocalDate(){//TODO recherche du nombre de jours entre 2 dates
        int annedebut = 2022;
        int moisdebut = 8;
        int anneeFin = 2022;
        int moisFin = 10;
        LocalDate debut = LocalDate.of(annedebut,moisdebut,1);
        LocalDate fin = LocalDate.of(anneeFin,moisFin,1);
        fin = fin.plusMonths(1);
        fin = fin.plusDays(-1);//TODO pour se placer sur le dernier jour de moisFin
        int nbJours = (int) ( fin.toEpochDay() - debut.toEpochDay()  + 1);
        String s = nbJours+"";
        Colonne[] tCols = new Colonne[nbJours];
        LocalDate d = debut;
        for(int i = 0; i < nbJours; i ++){
            tCols[i] = new Colonne(i,d.getYear(), d.getMonthValue(), d.getDayOfMonth(), d.getDayOfWeek().getValue());
            d = d.plusDays(1);
        }
    }

    @Test
    public void test3LocalDate(){//TODO donne un résultat faux
        int annedebut = 2022;
        int moisdebut = 8;
        int anneeFin = 2022;
        int moisFin = 10;
        LocalDate debut = LocalDate.of(annedebut,moisdebut,1);
        int nbJours = 0;
        LocalDate d = debut;
        for(int i=0; i<1000; i++){
            nbJours++;
            d = d.plusDays(1);
            if(d.getMonth().getValue() > moisFin)break;
        }
    }

    @Test
    public void testSwitch(){
        ChoixAffichage choixAffichage = ChoixAffichage.JOUR;
        String texte = "";
        switch (choixAffichage){
//      case JOUR: labelCentre.setText((tJours[c.getNumJourSem()]+c.getNumJourMois()+""));break;
            case JOUR: texte = "BIBI";break;
            case MOIS: texte = "BABA";break;
        }
        texte="BUBU";
    }

    @Test
    public void TestIntersect()
    {
        LocalDateTime dd1 = LocalDateTime.of(2022,8,12,8,0);
        long ldd1 = dd1.getLong(NANO_OF_DAY);
        LocalDateTime df1 = LocalDateTime.of(2022,8,12,10,0);
        long ldf1 = df1.getLong(NANO_OF_DAY);
        LocalDateTime dd2 = LocalDateTime.of(2022,8,12,10,0);
        long ldd2 = dd2.getLong(NANO_OF_DAY);
        LocalDateTime df2 = LocalDateTime.of(2022,8,12,16,0);
        long ldf2 = df2.getLong(NANO_OF_DAY);
        boolean b1 = ldd1 < ldf2 && ldf1 > ldd2;
        boolean b2 = ldd2 < ldf1 && ldf2 > ldd1;

        int minutes = (int)MINUTES.between(dd1, df1);

        String str = "1986-04-08 12:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

    }

    @Test
    public void TestGenererInt(){//TODO attention, la borne supérieure est exclue des résultats (tester sur borneSup +1 si on veut l'inclure
        int[] j = new int[100];
        for(int i = 0; i<100; i++){
            j[i] = genererInt(8, 11);
        }
        String s ="fin du test";
    }

    @Test
    public void TestGenerationTacheAleatoire(){
        LocalDateTime dRef = LocalDateTime.of(2022,8,1,0,0); //TODO: le premier jour du mois d'aout est un lundi
        LocalDateTime[] dates = new LocalDateTime[10];
        LocalDateTime d;
        for(int i = 0; i< 10; i++){
            d = dRef;
            d = d.plusDays(genererInt(1,6));
            d = d.plusWeeks(genererInt(0,14));
            d = d.plusHours(genererInt(6,19));
            d = d.plusMinutes(genererInt(0,6)*10);
            dates[i] = d;
        }
        String s ="fin du test";
    }
    @Test
    public void TestPremierLundi(){
        LocalDateTime dRef = LocalDateTime.of(2022,1,15,0,0);
        LocalDateTime[] dates = new LocalDateTime[12];
        LocalDateTime d = dRef;
        for(int i = 0; i< 12; i++){
            dates[i]= positionnerPremierLundi(d);
            d = d.plusMonths(1);
        }
        String s = "fin du test";
    }

    private LocalDateTime positionnerPremierLundi(LocalDateTime dat){
        LocalDateTime d = dat.withDayOfMonth(1);
        for(int i = 0; i < 7; i ++){
            if(d.getDayOfWeek().getValue() == 1){
                return d;
            }
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
