package com.example.gwt.sandbox.client;

import com.example.gwt.sandbox.shared.calendar.Selection;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
  void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

  void transformHtmlWithSection(String html, AsyncCallback<String> callback);

  void creerSelection(int anneDebut, int moisDebut, int anneeFin, int moisFin, AsyncCallback<Selection> callback);

}
