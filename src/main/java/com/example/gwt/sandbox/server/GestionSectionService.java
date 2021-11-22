package com.example.gwt.sandbox.server;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GestionSectionService {

    private static final String TEMPLATE_SECTION =
        "<div>" +
            "<button id=\"{0}Show\" class=\"section-button\" onclick=\"showAndHideSection(''{0}'', true)\" title=\"Ouvrir {1}\"> <b>{1}</b> &#9660; </button>\n" +
            "<button id=\"{0}Hide\" class=\"section-button\" style=\"display:none;\" onclick=\"showAndHideSection(''{0}'', false)\" title=\"Fermer {1}\"> <b>{1}</b> &#9650; </button>\n" +
            "<div id=\"{0}\" class=\"section\" style=\"display:none;\">" +
                "<br>" +
                "{2}" +
                "<br>" +
                "{3}" +
            "</div>" +
        "</div>";

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

    public String transformHtml(String html) {

        List<Section> sections =
            Arrays.asList(
                new Section(
                    "Section 1",
                    "Le contenu de la première section",
                    Arrays.asList(
                            new Section("Section 1.1","Le contenu de la section 1.1"),
                            new Section("Section 1.2",
                                    "Le contenu de la section 1.2",
                                    Arrays.asList(
                                            new Section("Section 1.2.1", "Le contenu de la section 1.2.1"),
                                            new Section("Section 1.2.1", "Le contenu de la section 1.2.2"))
                            )
                    )
                ),
                new Section("Section 2", "du texte, encore du texte"));

        return Section.write(sections);
    }
}
