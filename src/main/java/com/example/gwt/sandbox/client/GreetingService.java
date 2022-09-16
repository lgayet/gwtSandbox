package com.example.gwt.sandbox.client;

import com.example.gwt.sandbox.shared.Selection;
import com.example.gwt.sandbox.shared.Selection2;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
  String greetServer(String name) throws IllegalArgumentException;

  String transformHtmlWithSection(String html);

  Selection creerSelection(int anneDebut, int moisDebut, int anneeFin, int moisFin);

  Selection2 lireJours();
}
