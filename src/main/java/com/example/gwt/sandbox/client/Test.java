package com.example.gwt.sandbox.client;

import com.example.gwt.sandbox.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test implements EntryPoint {

  private static final String TEMPLATE_SECTION_STR =
    "<div>" +
      "<button id=\"{0}Show\" class=\"section-button\" onclick=\"showAndHideSection('{0}', true)\" title=\"Ouvrir {1}\"> <b>{1}</b> &#9660; </button>\n" +
      "<button id=\"{0}Hide\" class=\"section-button\" style=\"display:none;\" onclick=\"showAndHideSection('{0}', false)\" title=\"Fermer {1}\"> <b>Section 1</b> &#9650; </button>\n" +
      "<div id=\"{0}\" class=\"section\" style=\"display:none;\">" +
      "<br>{2}<br>" +
      "</div>" +
    "</div>";

  /*interface TemplateSection extends SafeHtmlTemplates {
    @Template(TEMPLATE_SECTION_STR)
    SafeHtml create(String identifiantSection, String texteSection, String contenuSection);
  }*/

  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  //private static final TemplateSection TEMPLATE_SECTION = GWT.create(TemplateSection.class);

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    final Button sendButton = new Button("Send");
    final TextBox nameField = new TextBox();
    nameField.setText("GWT User");
    final Label errorLabel = new Label();

    // We can add style names to widgets
    sendButton.addStyleName("sendButton");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    RootPanel.get("nameFieldContainer").add(nameField);
    RootPanel.get("sendButtonContainer").add(sendButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);

    // Focus the cursor on the name field when the app loads
    nameField.setFocus(true);
    nameField.selectAll();

    // Create the text area and toolbar
    /*RichTextArea area = new RichTextArea();
    area.ensureDebugId("cwRichText-area");
    area.setSize("100%", "14em");
    RichTextToolbar toolbar = new RichTextToolbar(area);
    toolbar.ensureDebugId("cwRichText-toolbar");
    toolbar.setWidth("100%");

    // Add the components to a panel
    Grid grid = new Grid(2, 1);
    grid.setStyleName("cw-RichText");
    grid.setWidget(0, 0, toolbar);
    grid.setWidget(1, 0, area);
    RootPanel.get("richTextContainer").add(grid);*/

    String html =
              "<div>" +
                "<button id=\"Section1Show\" class=\"section-button\" onclick=\"showAndHideSection('Section1', true)\" title=\"Ouvrir la section 1\"> <b>Section 1</b> &#9660; </button>\n" +
                "<button id=\"Section1Hide\" class=\"section-button\" style=\"display:none;\" onclick=\"showAndHideSection('Section1', false)\" title=\"Fermer la section 1\"> <b>Section 1</b> &#9650; </button>\n" +
                "<div id=\"Section1\" class=\"section\" style=\"display:none;\">" +
                    "<br>Le texte de la section 1<br>" +
                    "<div>" +
                      "<button id=\"Section11Show\" class=\"section-button\" onclick=\"showAndHideSection('Section11', true)\" title=\"Ouvrir la section 1.1\"> <b>Section 1.1</b> &#9660; </button>\n" +
                      "<button id=\"Section11Hide\" class=\"section-button\" style=\"display:none;\"  onclick=\"showAndHideSection('Section11', false)\" title=\"Fermer la section 1.1\"> <b>Section 1.1</b> &#9650; </button>\n" +
                      "<div id=\"Section11\" class=\"section\" style=\"display:none;\"><br>Le texte de la section 1.1<br></div>" +
                    "</div>" +
                    "<div>" +
                      "<button id=\"Section12Show\" class=\"section-button\" onclick=\"showAndHideSection('Section12', true)\"> <b>Section 1.2</b> &#9660; </button>\n" +
                      "<button id=\"Section12Hide\" class=\"section-button\" style=\"display:none;\" onclick=\"showAndHideSection('Section12', false)\"> <b>Section 1.2</b> &#9650; </button>\n" +
                      "<div id=\"Section12\" class=\"section\" style=\"display:none;\"><br>Le texte de la section 1.2<br></div>" +
                    "</div>" +
                "</div>" +
              "</div>";

    SafeHtml safeHtml = SafeHtmlUtils.fromSafeConstant(html);

    //safeHtml = TEMPLATE_SECTION.create("Section1", "Section 1", "contenu de la section 1");

    HTMLPanel htmlPanel = new HTMLPanel(safeHtml);
    RootPanel.get("htmlPanelContainer").add(htmlPanel);

    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final Label textToServerLabel = new Label();
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler {
      /**
       * Fired when the user clicks on the sendButton.
       */
      public void onClick(ClickEvent event) {
        sendNameToServer();
      }

      /**
       * Fired when the user types in the nameField.
       */
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      /**
       * Send the name from the nameField to the server and wait for a response.
       */
      private void sendNameToServer() {
        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = nameField.getText();
        if (!FieldVerifier.isValidName(textToServer)) {
          errorLabel.setText("Please enter at least four characters");
          return;
        }
        
        // Then, we send the input to the server.
        sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        greetingService.greetServer(textToServer, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            // Show the RPC error message to the user
            dialogBox.setText("Remote Procedure Call - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }

          public void onSuccess(String result) {
            dialogBox.setText("Remote Procedure Call");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(result);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        });
      }
    }

    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    sendButton.addClickHandler(handler);
    nameField.addKeyUpHandler(handler);
  }
}
