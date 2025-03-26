package main.swing.view;

import main.swing.utils.*;
import main.swing.generator.*;

import java.awt.*;
import javax.swing.*;

/**
 * Représente le chargement d'une ressource.
 * 
 * @author Florian Pépin
 * @version 1.0
 */
@SuppressWarnings("serial")
public class LoadingView extends JPanel implements ModelListener {

    private SortingResultJsonGenerator srj;
    private JLabel label;

    public LoadingView(SortingResultJsonGenerator srj) {
        this.srj = srj;
        this.setBackground(Color.WHITE);
        this.srj.addModelListener(this);
        this.label = new JLabel("Loading... 0%");
        this.add(this.label);
        this.setVisible(false);
    }

    public void setLabelProgress(int value) {
        if(value == 1) this.setVisible(true);
        if (value == 100) {
            this.label.setText("Loading... Done");
            try {
                Thread.sleep(1000);
                this.setVisible(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.label.setText("Loading... "+value+"%");
        }
    }

    @Override
    public void updatedModel(Object source, String eventType) {
        if(eventType.equals("json")) {
            this.setLabelProgress(this.srj.getProgress());
        }
    }

}