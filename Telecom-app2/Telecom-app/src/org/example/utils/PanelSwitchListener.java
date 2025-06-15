package org.example.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelSwitchListener implements ActionListener {
    private InputUI frame;
    private String targetPanel;

    public PanelSwitchListener(InputUI frame, String targetPanel) {
        this.frame = frame;
        this.targetPanel = targetPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.showPanel(targetPanel);
    }
}
