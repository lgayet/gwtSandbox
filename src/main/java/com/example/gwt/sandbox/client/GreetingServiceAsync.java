package com.example.gwt.sandbox.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
  void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

  void transformHtmlWithSection(String html, AsyncCallback<String> callback);
}
