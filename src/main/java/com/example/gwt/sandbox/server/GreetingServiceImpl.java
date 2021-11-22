package com.example.gwt.sandbox.server;

import com.example.gwt.sandbox.client.GreetingService;
import com.example.gwt.sandbox.shared.FieldVerifier;
import com.google.gwt.thirdparty.guava.common.base.Joiner;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

    private final GestionSectionService gestionSectionService;

    private static final String TEMPLATE_SECTION =
        "<div>" +
            "<button id=\"{0}Show\" class=\"section-button\" onclick=\"showAndHideSection(''{0}'', true)\" title=\"Ouvrir {1}\"> <b>{1}</b> &#9660; </button>\n" +
            "<button id=\"{0}Hide\" class=\"section-button\" style=\"display:none;\" onclick=\"showAndHideSection(''{0}'', false)\" title=\"Fermer {1}\"> <b>{1}</b> &#9650; </button>\n" +
            "<div id=\"{0}\" class=\"section\" style=\"display:none;\">" +
                "<br>" +
                "{2}" +
                "<br>" +
                "<br>" +
                "{3}" +
            "</div>" +
        "</div>";

    public GreetingServiceImpl() {
        this.gestionSectionService = new GestionSectionService();
    }

    public String greetServer(String input) throws IllegalArgumentException {
        // Verify that the input is valid.
        if (!FieldVerifier.isValidName(input)) {
            // If the input is not valid, throw an IllegalArgumentException back to
            // the client.
            throw new IllegalArgumentException(
                    "Name must be at least 4 characters long");
        }

        String serverInfo = getServletContext().getServerInfo();
        String userAgent = getThreadLocalRequest().getHeader("User-Agent");

        // Escape data from the client to avoid cross-site script vulnerabilities.
        input = escapeHtml(input);
        userAgent = escapeHtml(userAgent);

        return "Hello, " + input + "!<br><br>I am running " + serverInfo
                + ".<br><br>It looks like you are using:<br>" + userAgent;
    }

    @Override
    public String transformHtmlWithSection(String html) {
        return gestionSectionService.transformHtml(html);
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }
}
