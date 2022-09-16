package com.example.gwt.sandbox.client;

import com.example.gwt.sandbox.shared.Selection;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.shape.Text;

import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test implements EntryPoint {

  public static MoveContext MOVE_CONTEXT = new MoveContext();
  private final int LARGEUR_PANEL = 1900;
  private final int HAUTEUR_PANEL = 600;
  private final int HAUTEUR_ENTETES_COLONNES = 50;
  private final int LARGEUR_ENTETE_SALARIES = 250;
  private final DrawingArea canvas = new DrawingArea(LARGEUR_PANEL, HAUTEUR_PANEL);
  private final double OPACITY = 0.3D;
  private ChoixAffichage choixAffichage;
  private double largeurPanel = Double.valueOf(LARGEUR_PANEL+"");
  private double hauteurPanel = Double.valueOf(HAUTEUR_PANEL+"");
  private double hauteurEntetesColonnes = Double.valueOf(HAUTEUR_ENTETES_COLONNES);
  private double largeurEntetesSalaries = Double.valueOf(LARGEUR_ENTETE_SALARIES);
  private double nbCols = 1.0D;
//  Informations pour affichage des dates
  public final String[]tJours = {"XXX","Lun","Mar","Mer","Jeu","Ven","Sam","Dim"};
  public final String[]tJoursLong = {"XXX","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
  public final String[]tMois ={"XXX","Jan","Fev","Mar","Avr","Mai","Juin","Juil","Aou","Sep","Oct","Nov","Dec"};
  public final String[]tMoisLong = {"XXX","Janvier","Février","Mars","Avril","Mai","Juin","Juilet","Août","Septembre","Octobre","Novembre","Décembre"};
//  pour construire les objets
  /*
    TODO Marc: 13/9/2022: première implémentation: selection de 3 mois pleins
   */
//  déplacement dans la selection
  private int numJourCourant;
  private int numSemCourante;
  private int numMoisCourant;
  private int anneeCourante;
//  Les choix d'affichage ENTREPRISE/UTILISATEUR
  private final double HEURE_DEBUT_JOUR = 6.0;
  private final double HEURE_FIN_JOUR = 22.5;
  private final double PLAGE_HORARAIRE = HEURE_FIN_JOUR - HEURE_DEBUT_JOUR;
  // les contenus modifiables du tableau
  private Text labelCentre;
  private Line[]tLVCols = new Line[0];
  private Line[]tLVHor = new Line[0];
  private Text[] tLabel = new Text[0];
//  les objets servant au dépôt et au déplacement des tâches
  private Selection selection;
  private int nbJoursSelection;
  private int nbJoursAffiches;
  private int indicePremiereCol =28;
  private double largCol;
  private Colonne[] tCols;//TODO: dimension= nbJours de la selection
  private Salarie[] salaries = new Salarie[0];
  private String label;

  //private static final TemplateSection TEMPLATE_SECTION = GWT.create(TemplateSection.class);

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  private static final Logger logger = Logger.getLogger(Test.class.getName());
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    setStyleBordures(canvas.getElement());
    RootPanel.get("CanvasContainer").add(canvas);

//    Debut de la construction graphique
//  LH_Entete
    ajoutLigne(0, HAUTEUR_ENTETES_COLONNES, LARGEUR_PANEL, HAUTEUR_ENTETES_COLONNES, OPACITY);
//  LH_EnteteCol
    ajoutLigne(0, HAUTEUR_ENTETES_COLONNES * 2, LARGEUR_PANEL, HAUTEUR_ENTETES_COLONNES *2, OPACITY);
//  LV_EnteteSalaries
    ajoutLigne( LARGEUR_ENTETE_SALARIES,50, LARGEUR_ENTETE_SALARIES, HAUTEUR_PANEL , OPACITY);
//    Ajout des boutons choixAffichage
    ButtonChoixAffichage boutJour = new ButtonChoixAffichage( this, canvas, 30, 10,50,30,ChoixAffichage.JOUR, "Jour");
    ButtonChoixAffichage boutSem = new ButtonChoixAffichage( this, canvas, 90, 10,70,30,ChoixAffichage.SEMAINE, "Semaine");
    ButtonChoixAffichage boutMois = new ButtonChoixAffichage( this, canvas, 170, 10,50,30,ChoixAffichage.MOIS, "Mois");
    boutJour.setBoutons(boutSem, boutMois);
    boutSem.setBoutons(boutJour, boutMois);
    boutMois.setBoutons(boutJour, boutSem);
//    Le label affichant la période affichée
    labelCentre = ajoutLabel((((LARGEUR_PANEL-LARGEUR_ENTETE_SALARIES)/2)+LARGEUR_ENTETE_SALARIES),30,"du texte", 20, 1.0);

    canvas.addMouseMoveHandler(event -> {
      MOVE_CONTEXT.move(event.getClientX(), event.getClientY());
    });

    canvas.addMouseUpHandler(event -> {
      MOVE_CONTEXT.stop(event.getClientX(), event.getClientY());
    });

    creeColonnes();

  }

  public void creeColonnes(){
  greetingService.creerSelection(2022,8,2022,10, new AsyncCallback<Selection>() {
    @Override
    public void onFailure(Throwable caught) {

    }

    @Override
    public void onSuccess(Selection result) {
        nbJoursSelection = result.getNbJours();
        tCols = result.getTCols();
    }
  });
  }

  public void setChoixAffichage(ChoixAffichage choixAffichage){
    System.out.println("coucou setChoixAffichage avec "+choixAffichage);
//    labelCentre.setText(choixAffichage.toString());
    if( choixAffichage != this.choixAffichage)modifChoixAffichage(choixAffichage);
  }


  private void modifChoixAffichage(ChoixAffichage choixAffichage){
    this.choixAffichage = choixAffichage;
    canvas.remove(labelCentre);
    for(Line l: tLVCols){
      if(l != null)canvas.remove(l);
    }
    for(Line l: tLVHor){
      if(l != null)canvas.remove(l);
    }
    for(Text t: tLabel){
      if(t != null)canvas.remove(t);
    }
    if(choixAffichage == ChoixAffichage.JOUR){
      Colonne c = tCols[indicePremiereCol];
      c.setPositionX(LARGEUR_ENTETE_SALARIES);
      labelCentre = ajoutLabel((LARGEUR_PANEL - LARGEUR_ENTETE_SALARIES) / 2 + LARGEUR_ENTETE_SALARIES - 100,30,tJoursLong[c.getNumJourSem()]+" "+c.getNumJourMois()+" "+tMoisLong[c.getNumMois()]+" "+c.getAnnee(), 20, 1.0);

      tLVCols = new Line[0];//TODO reste à afficher les heures
      double largeurHoraire = (largeurPanel - largeurEntetesSalaries ) / PLAGE_HORARAIRE;
      tLVHor = new Line[(int) PLAGE_HORARAIRE];
      tLabel = new Text[(int) PLAGE_HORARAIRE];
      tLabel[0] = labelCentre;
      int i = 0;
      for(double h = 1.0; h < PLAGE_HORARAIRE; h +=1.0){
        int decalX = h+ (int) HEURE_DEBUT_JOUR  < 10 ? 3 : 6;
        tLVHor[i] = ajoutLigne(c.getPositionX() + (int) (h* largeurHoraire), HAUTEUR_ENTETES_COLONNES  + 30, c.getPositionX() + (int) (h * largeurHoraire), HAUTEUR_PANEL, OPACITY);
        tLabel[i] = ajoutLabel(c.getPositionX() + (int) (h* largeurHoraire) - decalX, HAUTEUR_ENTETES_COLONNES + 20, (int) h+ HEURE_DEBUT_JOUR+ "" , 12, 0.5);
        i ++;
      }

    }else if(choixAffichage == ChoixAffichage.SEMAINE) {
      indicePremiereCol = (indicePremiereCol - tCols[indicePremiereCol].getNumJourSem() + 1) > 0 ? indicePremiereCol - tCols[indicePremiereCol].getNumJourSem() + 1 : 1;
      Colonne c = tCols[indicePremiereCol];
      c.setPositionX(LARGEUR_ENTETE_SALARIES);
      Colonne cFin = tCols[indicePremiereCol+6];
      boolean anneeFin = cFin.getAnnee() != c.getAnnee();
      boolean moisFin = cFin.getNumMois() != c.getNumMois();
      int decalX = anneeFin ? 150 : moisFin ? 100 : 50;
      String text = anneeFin ? (tMoisLong[c.getNumMois()]+"  " + c.getAnnee() +"  -  "+ tMoisLong[cFin.getNumMois()] + "  "+ cFin.getAnnee() ) : ( moisFin ?  tMoisLong[c.getNumMois()] +"  -  " + tMoisLong[cFin.getNumMois()]+ "  "+ c.getAnnee() : tMoisLong[c.getNumMois()]+"  "+c.getAnnee());

      labelCentre = ajoutLabel((LARGEUR_PANEL - LARGEUR_ENTETE_SALARIES) / 2 + LARGEUR_ENTETE_SALARIES - decalX,30, text , 20, 1.0);
      tLVCols = new Line[6];
      tLabel = new Text[7];
      largCol = (largeurPanel - largeurEntetesSalaries) / 7.0;
      double decal2X = 20.0;
      Colonne c2;
      for (int i = 0; i < 7; i++) {
        double x = largeurEntetesSalaries + (largCol * i );// TODO: pour dessiner la ligne à gauche de la colonne
        c2 = tCols[c.getNumCol()+ i];
        c2.setPositionX((int)x);
        if(i > 0)tLVCols[i] = ajoutLigne(c2.getPositionX(), HAUTEUR_ENTETES_COLONNES , c2.getPositionX(), HAUTEUR_PANEL, OPACITY);
        tLabel[i] = ajoutLabel((int)( c2.getPositionX() + largCol / 2.0 - decal2X ), HAUTEUR_ENTETES_COLONNES + 30 , tJours[tCols[indicePremiereCol+i].getNumJourSem()]+ " "+tCols[indicePremiereCol+i].getNumJourMois() , 14, 0.5D);
      }
    }
      else if(choixAffichage == ChoixAffichage.MOIS){//TODO: il faudra etablir le nombre de jours enj fonction du mois
        Colonne c = getPremierJourMois(tCols[indicePremiereCol].getAnnee(), tCols[indicePremiereCol].getNumMois());
        Colonne c2;
        int nbJoursMois = getNbJoursMois(c.getAnnee(), c.getNumMois());
        labelCentre = ajoutLabel((LARGEUR_PANEL - LARGEUR_ENTETE_SALARIES) / 2 + LARGEUR_ENTETE_SALARIES - 50,30, tMoisLong[c.getNumMois()]+"  "+c.getAnnee() , 20, 1.0);
        tLVCols = new Line[nbJoursMois -1];
        tLabel = new Text[nbJoursMois * 2 ];
        largCol = (largeurPanel - largeurEntetesSalaries)/ nbJoursMois;
        for(int i = 0; i< nbJoursMois; i++ ){
          double x = largeurEntetesSalaries + (largCol * (i));
          c2 = tCols[c.getNumCol() + i];
          c2.setPositionX((int)x);
          double opacite = c2.getNumJourMois() > 1 && c2.getNumJourSem() == 1.0 ? 0.5 : OPACITY;
          int decalX = c2.getNumJourMois() >= 10 ? 7 : 5;
          if(i > 0)tLVCols[i]= ajoutLigne(c2.getPositionX(), HAUTEUR_ENTETES_COLONNES, (int)x, HAUTEUR_PANEL, opacite);
          tLabel[i * 2] = ajoutLabel((int)(c2.getPositionX() + largCol * 0.5 - 10), HAUTEUR_ENTETES_COLONNES + 20, tJours[c2.getNumJourSem()], 12, OPACITY);
          tLabel[i * 2 + 1] = ajoutLabel((int)(c2.getPositionX() + largCol * 0.5 - decalX) , HAUTEUR_ENTETES_COLONNES + 40, c2.getNumJourMois()+"", 12,OPACITY);
        }
    }
  }

  private int getNbJoursMois(int annee, int numMois){
    int nbJoursMois = 0;
    for(Colonne c: tCols){
      if(c.getAnnee() == annee && c.getNumMois() == numMois)nbJoursMois ++;
    }
    return nbJoursMois;
  }

  private Colonne getPremierJourMois(int annee, int numMois){
    for(Colonne c: tCols){
      if(c.getAnnee() == annee && c.getNumMois() == numMois && c.getNumJourMois() == 1)return c;
    }
    return null;
  }

  private Text ajoutLabel(int x, int y, String text, int size, double opacity){
    Text t = new Text(x,y,text);
    t.setFontSize(size);
    t.setStrokeOpacity(opacity);
    canvas.add(t);
    return t;
  }

  private Line ajoutLigne(int x1, int y1, int x2, int y2, double opacity){
    Line l = new Line(x1,y1,x2,y2);
    l.setStrokeOpacity(opacity);
    canvas.add(l);
    return l;
  }

  private void setStyleBordures(Element element){
    element .getStyle().setBorderColor("black");
    element.getStyle().setBorderStyle(Style.BorderStyle.SOLID);
    element.getStyle().setBorderWidth(1, Style.Unit.PX);

  }
}
