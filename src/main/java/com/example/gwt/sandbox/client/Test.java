package com.example.gwt.sandbox.client;

import com.example.gwt.sandbox.client.component.ChampHeureMinute;
import com.example.gwt.sandbox.client.component.ChampNumeriqueFormate;
import com.example.gwt.sandbox.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test implements EntryPoint {

  private static final String HTML =
    "<html>" +
    "&lt;section section 1&gt;<br>" +
      "contenu section 1 <br>" +
      "&lt;section section 1.1&gt;<br>" +
        "contenu section 1.1<br>" +
      "&lt;/section&gt;<br>" +
    "&lt;/section&gt;<br>" +
    "&lt;section section 2&gt;<br>" +
      "contenu section 2<br>" +
      "&lt;section section 2.1&gt;<br>" +
        "contenu section 2.1<br>" +
      "&lt;/section&gt;<br>" +
    "&lt;/section&gt;<br>" +
    "</html>";

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

    //Champ time
    final ChampNumeriqueFormate champTime = new ChampHeureMinute();
    RootPanel.get("htmlPanelContainer").add(champTime);

    // Create the text area and toolbar
    /*RichTextArea area = new RichTextArea();
    area.ensureDebugId("cwRichText-area");
    area.setSize("100%", "14em");
    RichTextToolbar toolbar = new RichTextToolbar(area);
    toolbar.ensureDebugId("cwRichText-toolbar");
    toolbar.setWidth("100%");

    // Add the components to a panel
    Grid grid = new Grid(2, 20);
    grid.setStyleName("cw-RichText");
    grid.setWidget(0, 0, toolbar);
    grid.setWidget(1, 0, area);
    RootPanel.get("richTextContainer").add(grid);
    area.setHTML(HTML);

    RootPanel.get("htmlPanelContainer").add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant("Pour l'instant c'est vide")));*/

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
    closeButton.addClickHandler(event -> {
      dialogBox.hide();
      sendButton.setEnabled(true);
      sendButton.setFocus(true);
    });

    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler {
      /**
       * Fired when the user clicks on the sendButton.
       */
      public void onClick(ClickEvent event) {
        sendNameToServer();

        /*greetingService.transformHtmlWithSection(area.getHTML(), new AsyncCallback<String>() {
          @Override
          public void onFailure(Throwable throwable) {}

          @Override
          public void onSuccess(String html) {
            RootPanel.get("htmlPanelContainer").remove(0);
            RootPanel.get("htmlPanelContainer").add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(html)));
          }
        });*/
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
