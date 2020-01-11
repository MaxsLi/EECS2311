package venn;

import controllers.Listener;
import models.VennSet;
import views.MainFrame;

public class Main {
	public static void main(String[] args) {
		VennSet vennSet = new VennSet();
		Listener listener = new Listener();
		MainFrame mainFrame = new MainFrame(listener);
		listener.set(vennSet, mainFrame);
	}
}
