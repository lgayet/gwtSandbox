package com.example.gwt.sandbox.server;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.*;

public class GestionSectionService {

    private static final String SECTION = "section";
    private static final String BALISE_OUVRANTE_SECTION = "&lt;" + SECTION + " ";
    private static final String BALISE_FERMANTE = "&gt;";
    private static final String BALISE_FERMANTE_SECTION = "&lt;/" + SECTION + "&gt;";
    private static final String BR = "<br>";

    private static final String TEMPLATE_SECTION =
        "<div>" +
            "<button id=\"{0}Show\" class=\"section-button\" onclick=\"showAndHideSection(''{0}'', true)\" title=\"Ouvrir {1}\"> {1} <i class=\"fa fa-angle-down\" aria-hidden=\"true\"></i> </button>\n" +
            "<button id=\"{0}Hide\" class=\"section-button\" style=\"display:none;\" onclick=\"showAndHideSection(''{0}'', false)\" title=\"Fermer {1}\"> {1} <i class=\"fa fa-angle-up\" aria-hidden=\"true\"></i> </button>\n" +
            "<div id=\"{0}\" class=\"section\" style=\"display:none;\">" +
                "{2}" +
            "</div>" +
        "</div>";

    private interface ISection {
        String write();

        static String write(List<ISection> sections) {
            return sections.stream()
                    .map(ISection::write)
                    .filter(StringUtils::isNotBlank)
                    .reduce(StringUtils.EMPTY, (s1, s2) -> s1 + s2);
        }
    }

    private static class SectionTexte implements ISection {
        private final String contenu;
        private SectionTexte(String contenu) {
            this.contenu = contenu;
        }

        @Override
        public String write() {
            return contenu;
        }
    }

    private static class Section implements ISection {
        private final String titre;
        private final List<ISection> sectionsEnfant;

        public Section(String titre, List<ISection> sectionsEnfant) {
            this.titre = titre;
            this.sectionsEnfant = sectionsEnfant;
        }

        @Override
        public String write() {
            return MessageFormat.format(TEMPLATE_SECTION,
                    "section" + UUID.randomUUID(),
                    titre,
                    ISection.write(sectionsEnfant));
        }
    }

    public String transformHtml(String html) {

        // SI pas de balise section alors on retourne le HTML donné sans transformation
        if (!html.contains(BALISE_OUVRANTE_SECTION))
            return html;

        if (countMatches(html, BALISE_OUVRANTE_SECTION) != countMatches(html, BALISE_FERMANTE_SECTION))
            throw new IllegalStateException("Le texte est mal formaté, le nombre de balises ouvrantes n'est pas le meme que le nombre de balises fermantes");

        // Traitement du html pour garder les retours à la ligne
        String htmlNormalise = html
                .replaceAll("<div> *<br> *</div>", "<br/>")
                .replaceAll("<div>", "")
                .replaceAll("</div>", "");

        // Recherche des sections
        List<ISection> sections = parserSections(htmlNormalise);

        // Réécriture du html avec remplacement des sections
        return ISection.write(sections);
    }

    /**
     * Permet de récupérer toutes les sections de meme niveau
     */
    private static List<ISection> parserSections(String html) {
        List<ISection> sections = new ArrayList<>();
        String texteRestant = html;
        while (isNotBlank(texteRestant)) {

            if (texteRestant.startsWith(BR)) texteRestant = substringAfter(texteRestant, BR);

            boolean sectionFound = contains(texteRestant, BALISE_OUVRANTE_SECTION);
            // Si plus de section trouvée, on récupère le texte restant comme du simple teste et on arrête de boucler
            // en retournant la liste de sections
            if (!sectionFound) {
                sections.add(new SectionTexte(texteRestant));
                return sections;
            } else {
                // Ajout du texte avant la balise section
                sections.add(new SectionTexte(substringBefore(texteRestant, BALISE_OUVRANTE_SECTION)));

                // Recherche des attributs de la section (titre + contenu)
                String titre = substringBetween(texteRestant, BALISE_OUVRANTE_SECTION, BALISE_FERMANTE);
                texteRestant = substringAfter(texteRestant, BALISE_FERMANTE);

                String contenuSection = substringBefore(texteRestant, BALISE_FERMANTE_SECTION);
                if (contenuSection.startsWith(BR)) contenuSection = substringAfter(contenuSection, BR);
                texteRestant = substringAfter(texteRestant, BALISE_FERMANTE_SECTION);
                // Tant que le nombre de balises ouvrantes n'est pas égale au nombre de balises fermantes, on concatene la suite du contenu
                while (countMatches(contenuSection, BALISE_FERMANTE_SECTION) != countMatches(contenuSection, BALISE_OUVRANTE_SECTION) ) {
                    contenuSection = contenuSection + BALISE_FERMANTE_SECTION + substringBefore(texteRestant, BALISE_FERMANTE_SECTION);
                    texteRestant = substringAfter(texteRestant, BALISE_FERMANTE_SECTION);
                }

                // Ajout de la section
                sections.add(new Section(titre, parserSections(contenuSection)));
            }
        }
        return sections;
    }
}
