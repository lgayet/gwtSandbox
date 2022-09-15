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
  private Text labelCentre;
  private double largeurPanel = Double.valueOf(LARGEUR_PANEL+"");
  private double hauteurPanel = Double.valueOf(HAUTEUR_PANEL+"");
  private double hauteurEntetesColonnes = Double.valueOf(HAUTEUR_ENTETES_COLONNES);
  private double largeurEntetesSalaries = Double.valueOf(LARGEUR_ENTETE_SALARIES);
  private double nbCols = 1.0D;
//  Informations pour affichage des dates
  public final String[]tJours = {"XXX","Lun","Mar","Mer","Jeu","Ven","Sam","Dim"};
  public final String[]tMois ={"XXX","Jan","Fev","Mar","Avr","Mai","Juin","Juil","Aou","Sep","Oct","Nov","Dec"};
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
  private Line[]tLVCols = new Line[0];
  private Line[]tLVHor = new Line[0];
  private Text[]tLVLabel = new Text[0];
//  les objets servant au dépôt et au déplacement des tâches
  private Selection selection;
  private int nbJoursSelection;
  private int nbJoursAffiches;
  private int indicePremiereCol = 0;
  private Colonne[] tCols;//TODO: dimension= nbJours de la selection
  private Salarie[] salaries = new Salarie[0];

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
//        nbJoursSelection = selection.getNbJours();
//        tCols = selection.getTCols();

    }
  });
  }

  public void setChoixAffichage(ChoixAffichage choixAffichage){
    System.out.println("coucou setChoixAffichage avec "+choixAffichage);
    labelCentre.setText(choixAffichage.toString());
    if( choixAffichage != this.choixAffichage)modifChoixAffichage(choixAffichage);
  }


  private void modifChoixAffichage(ChoixAffichage choixAffichage){
    this.choixAffichage = choixAffichage;
    for(Line l: tLVCols){
      canvas.remove(l);
    }
    for(Line l: tLVHor){
      canvas.remove(l);
    }
    for(Text t: tLVLabel){
      canvas.remove(t);
    }
    if(choixAffichage == ChoixAffichage.JOUR){
      tLVCols = new Line[0];//TODO reste à afficher les heures
      double largeurHoraire = (largeurPanel - largeurEntetesSalaries ) / PLAGE_HORARAIRE;
      tLVHor = new Line[(int) PLAGE_HORARAIRE];
      tLVLabel = new Text[(int) PLAGE_HORARAIRE];
      int i = 0;
      for(double h = 1.0; h < PLAGE_HORARAIRE; h +=1.0){
        int decal = h+ (int) HEURE_DEBUT_JOUR  < 10 ? 3 : 6;
        tLVHor[i] = ajoutLigne(LARGEUR_ENTETE_SALARIES + (int) (h* largeurHoraire), HAUTEUR_ENTETES_COLONNES *2-20, LARGEUR_ENTETE_SALARIES + (int) (h * largeurHoraire), HAUTEUR_PANEL, OPACITY);
        tLVLabel[i] = ajoutLabel(LARGEUR_ENTETE_SALARIES + (int) (h* largeurHoraire) - decal, HAUTEUR_ENTETES_COLONNES *2 - 30, (int) h+ HEURE_DEBUT_JOUR+ "" , 12, 0.5);
        i ++;
      }

    }else if(choixAffichage == ChoixAffichage.SEMAINE) {
      tLVCols = new Line[6];
      double largCol = (largeurPanel - largeurEntetesSalaries) / 7.0;
      for (int i = 0; i < 6; i++) {
        int x = (int) (largeurEntetesSalaries + (largCol * Double.valueOf((i+1)+"")));
        tLVCols[i] = ajoutLigne(x, HAUTEUR_ENTETES_COLONNES , x, HAUTEUR_PANEL, OPACITY);
      }
    }
      else if(choixAffichage == ChoixAffichage.MOIS){//TODO: il faudra etablir le nombre de jours enj fonction du mois
        tLVCols = new Line[30];
        double largCol = (largeurPanel - largeurEntetesSalaries)/ 31.0;
        for(int i = 0; i< 30; i ++ ){
          int x = (int)(largeurEntetesSalaries + (largCol * Double.valueOf((i+1)+"")));
          tLVCols[i]= ajoutLigne(x, HAUTEUR_ENTETES_COLONNES, x, HAUTEUR_PANEL, OPACITY);
        }
    }
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
    l.setStrokeOpacity(OPACITY);
    canvas.add(l);
    return l;
  }

  private void setStyleBordures(Element element){
    element .getStyle().setBorderColor("black");
    element.getStyle().setBorderStyle(Style.BorderStyle.SOLID);
    element.getStyle().setBorderWidth(1, Style.Unit.PX);

  }
}
