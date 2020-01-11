package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controllers.Listener;

public class MenuBar extends JMenuBar {

    private Listener listener;

    public MenuBar(Listener listener) {
        super();
        this.listener = listener;
        this.createFileMenu();
    }

    private void createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        this.add(fileMenu);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand(MainFrame.EXIT);
        exitMenuItem.addActionListener(listener);

        fileMenu.add(exitMenuItem);
    }
}