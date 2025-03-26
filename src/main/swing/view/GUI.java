package main.swing.view;

import main.swing.generator.SortingResultJsonGenerator;
import main.swing.model.*;
import main.swing.controller.*;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Florian Pépin
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {
    
    private SortingList sl;
    private SortingResultJsonGenerator srj;
    private AnimationStrategy animation;
    
    public GUI(SortingList sl, SortingResultJsonGenerator srj) {
        super("Sorting Algorithms");
        this.sl = sl;
        this.srj = srj;
        this.animation = new VBarAnimationView(sl);

        this.setSize(1200, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.refresh();
        this.setVisible(true);
    }

    public void setAnimation(AnimationStrategy a) {
        this.sl.removeModelListener(this.animation);
        this.animation = a;
        this.refresh();
    }

    public void refresh() {
        this.getContentPane().removeAll();
        this.showActionPanel();
        this.showAnimation();
        this.revalidate();
        this.repaint();
    }

    public void showActionPanel() {
        JPanel actionPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        JPanel containerPanel = new JPanel();
        actionPanel.setBackground(Color.WHITE);
        menuPanel.setBackground(Color.WHITE);
        containerPanel.setBackground(Color.WHITE);
        actionPanel.setLayout(new GridLayout(1, 3));
        menuPanel.setLayout(new GridLayout(1, 3, 0, 0));
        containerPanel.setLayout(new BorderLayout());

        ControllerSortMenu csm = new ControllerSortMenu(this.sl, this);
        ControllerAnimationMenu cam = new ControllerAnimationMenu(this.sl, this);

        menuPanel.add(csm);
        menuPanel.add(cam);
        menuPanel.add(new ControllerStatMenu(this.sl, this.srj));

        containerPanel.add(menuPanel, BorderLayout.NORTH);
        containerPanel.add(new LoadingView(this.srj), BorderLayout.SOUTH);

        actionPanel.add(containerPanel);
        actionPanel.add(new ControllerSlider(this.animation));
        actionPanel.add(new ControllerButtons(this.sl, csm, cam));

        this.add(actionPanel, BorderLayout.NORTH);
    }

    public void showAnimation() {
        JPanel sortPanel = new JPanel();
        sortPanel.setBackground(Color.BLACK);
        sortPanel.setLayout(new BorderLayout());
        sortPanel.add(new StatisticView(this.sl), BorderLayout.NORTH);
        sortPanel.add(this.animation, BorderLayout.CENTER);
        this.add(sortPanel, BorderLayout.CENTER);
    }
    
}
