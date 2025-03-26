package main.swing.controller;

import main.swing.model.*;
import main.swing.generator.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

@SuppressWarnings("serial")
public class ControllerStatMenu extends JPanel implements ActionListener {

    private SortingList sortingList;
    private SortingResultJsonGenerator srj;
    private URI uri;

    public ControllerStatMenu(SortingList sortingList, SortingResultJsonGenerator srj) {
        super();
        this.sortingList = sortingList;
        this.srj = srj;
        String[] stats = {"global", "generate"};
        this.add(new Menu(this, stats, "Statistics"));
        this.setBackground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            String item = ((JMenuItem) e.getSource()).getText();
            if ("global".equals(item)) {
                try {
                    WebServer web = new WebServer("http://localhost:8080/dist/web/view/index.html");
                    this.uri = new URI(web.getURL());
                    Desktop.getDesktop().browse(this.uri);
                } catch (BindException bind) {
                    try {
                        Desktop.getDesktop().browse(this.uri);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException("Failed to open the URL: " + ex.getMessage(), ex);
                }
            } else if ("generate".equals(item)) {
                Thread generateTests = new Thread(() -> {
                    try {
                        this.srj.executeTests();
                    } catch (Exception ex) {
                        throw new RuntimeException("Failed to execute tests: " + ex.getMessage(), ex);
                    }
                });
                generateTests.start();
            } else {
                throw new RuntimeException("Invalid item: " + item);
            }
        }
    }

}
