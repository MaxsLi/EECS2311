package venn;

import controllers.Listener;
import views.MainFrame;

public class Main {
	public static void main(String[] args) {
		Listener listener = new Listener();
		MainFrame mainFrame = new MainFrame(listener);
		listener.set(mainFrame);
	}
}
