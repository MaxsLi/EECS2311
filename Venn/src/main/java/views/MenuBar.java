package views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

import controllers.Listener;

public class MenuBar extends JMenuBar {

    private final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
    private Listener listener;

    public MenuBar(Listener listener) {
        super();
        this.listener = listener;
        this.createFileMenu();
    }

    private void createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        this.add(fileMenu);

        JMenuItem addCircleMenuItem = new JMenuItem("Add Circle");
        addCircleMenuItem.setActionCommand(MainFrame.ADD_CIRCLE);
        addCircleMenuItem.addActionListener(listener);
        addCircleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));

        JMenuItem resetDiagramMenuItem = new JMenuItem("Reset Diagram");
        resetDiagramMenuItem.setActionCommand(MainFrame.RESET_DIAGRAM);
        resetDiagramMenuItem.addActionListener(listener);
        resetDiagramMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand(MainFrame.EXIT);
        exitMenuItem.addActionListener(listener);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));

        fileMenu.add(addCircleMenuItem);
        fileMenu.add(resetDiagramMenuItem);
        fileMenu.add(exitMenuItem);
    }
}