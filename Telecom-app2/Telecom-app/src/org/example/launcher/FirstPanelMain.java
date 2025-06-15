package org.example.launcher;

import org.example.utils.FirstPanel;

import javax.swing.*;

public class FirstPanelMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("통신사 변경 가이드 프로그램");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FirstPanel firstPanel = new FirstPanel();
        frame.add(firstPanel);

        frame.setVisible(true);
    }
}