package views;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.VennSet;


public class DiagramPanel extends JPanel{

    private List<VennSet> vennSetList = new ArrayList<>();
    private Random random = new Random();

    public DiagramPanel() {
        super();
    }

    public void addCircle(VennSet vennSet) {
        vennSetList.add(vennSet);
        repaint();
    }

    public void resetDiagram() {
        vennSetList.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (VennSet vennSet : vennSetList) {
            new VennCircle(random.nextInt(this.getWidth()), random.nextInt(this.getHeight())).draw(g);
        }
    }
}