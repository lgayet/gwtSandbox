package com.example.gwt.sandbox.server;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.*;

public class GestionSectionService {

    private static final String SECTION = "section";

    private static final String TEMPLATE_SECTION =
        "<div>" +
            "<button id=\"{0}Show\" class=\"section-button\" onclick=\"showAndHideSection(''{0}'', true)\" title=\"Ouvrir {1}\"> <b>{1}</b> &#9660; </button>\n" +
            "<button id=\"{0}Hide\" class=\"section-button\" style=\"display:none;\" onclick=\"showAndHideSection(''{0}'', false)\" title=\"Fermer {1}\"> <b>{1}</b> &#9650; </button>\n" +
            "<div id=\"{0}\" class=\"section\" style=\"display:none;\">" +
                "{2}" +
                "{3}" +
            "</div>" +
        "</div>";

    private DocumentBuilder db;

    private static class Section {
        private final String titre;
        private final String contenu;
        private final List<Section> sectionsEnfant;

        public Section(String titre, String contenu, List<Section> sectionsEnfant) {
            this.titre = titre;
            this.contenu = contenu;
            this.sectionsEnfant = sectionsEnfant;
        }

        public Section(String titre, String contenu) {
            this(titre, contenu, new ArrayList<>());
        }

        static public String write(List<Section> sections) {
            return sections.stream()
                    .map(Section::write)
                    .reduce(StringUtils.EMPTY, (s1, s2) -> s1 + "<br>" + s2);
        }

        public String write() {
            return MessageFormat.format(TEMPLATE_SECTION,
                    "section" + UUID.randomUUID(),
                    titre,
                    contenu,
                    write(sectionsEnfant));
        }
    }

    public GestionSectionService() {
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String transformHtml(String html) throws IOException, SAXException {

        // Traitement du html pour qu'il soit lu comme du XML correct
        String htmlNormalise = html
                .replaceAll("<br>", "<br/>")
                .replaceAll("<br/></div><div>", "<br/>")
                .replaceAll("</div><div>", "<br/>")
                .replaceAll("<div>", "")
                .replaceAll("</div>", "")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&nbsp;", "")
                .replaceAll("<br/><" + SECTION, "<" + SECTION);

        if (!htmlNormalise.startsWith("<html>"))
            htmlNormalise = "<html>" + htmlNormalise + "</html>";

        // Lecture du html
        Document doc = db.parse(new InputSource(new StringReader(htmlNormalise)));
        doc.getDocumentElement().normalize();

        // Recherche des sections sur le premier niveau uniquement
        List<Section> sections = parserSectionsEnfant(doc.getDocumentElement().getChildNodes());

        // Réécriture du html avec remplacement des sections
        return "<html>" + getTexteJusquaPremiereBaliseSection(doc.getDocumentElement()) + Section.write(sections) + "</html>";
    }

    /**
     * Permet de récupérer toutes les sections enfant d'une liste de noeud
     */
    private static List<Section> parserSectionsEnfant(NodeList nodes) {
        List<Section> sections = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(SECTION)) {
                sections.add(
                    new Section(
                            ((Element) node).getAttribute("titre"),
                            // On supprime le premier saut de ligne car il est remplacé par une balise div
                            getTexteJusquaPremiereBaliseSection_sansPremierRetourALaLigne(node),
                            parserSectionsEnfant(node.getChildNodes())));
            }
        }
        return sections;
    }

    /**
     * Permet de récupérer le texte concaténé element par element jusqu'au premier element section trouvé
     */
    private static String getTexteJusquaPremiereBaliseSection(Node node) {
        NodeList list = node.getChildNodes();
        StringBuilder textContent = new StringBuilder();
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
                textContent.append(child.getTextContent());
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (!child.getNodeName().equals(SECTION))
                    textContent.append("<").append(child.getNodeName()).append(">");
                else
                    break;
            }
        }
        return textContent.toString();
    }

    private static String getTexteJusquaPremiereBaliseSection_sansPremierRetourALaLigne(Node node) {
        String texte = getTexteJusquaPremiereBaliseSection(node);
        return texte.startsWith("<br>") ? texte.substring(4) : texte;
    }
}
