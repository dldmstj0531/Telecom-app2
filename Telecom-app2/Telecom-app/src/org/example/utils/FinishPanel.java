package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FinishPanel extends JPanel {
    private InputUI parentFrame;
    public FinishPanel(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("통신사 변경 가이드 프로그램");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel doneLabel = new JLabel("통신사 변경 가이드 프로그램 이용이 완료되었습니다.");
        doneLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
        doneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));

        JLabel infoLabel = new JLabel("안내된 절차에 따라 통신사 변경을 안전하게 진행해 주세요. 이용해 주셔서 감사합니다.");
        infoLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        ImageIcon imageIcon = new ImageIcon("bird1.png");
        Image img = imageIcon.getImage().getScaledInstance(500, -1, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(doneLabel);
        centerPanel.add(infoLabel);
        centerPanel.add(imageLabel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton endButton = new JButton("프로그램 종료");
        endButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        endButton.setBackground(new Color(153, 153, 153));
        endButton.setForeground(Color.WHITE);
        endButton.setPreferredSize(new Dimension(180, 40));
        endButton.addActionListener((ActionEvent e) -> {
            parentFrame.stopMusic();
            System.exit(0);

            add(centerPanel, BorderLayout.CENTER);
        });

        bottomPanel.add(endButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createBoxedLabel(String msg) {
        JLabel label = new JLabel(msg);
        label.setFont(new Font("Malgun Gothic", Font.BOLD, 12));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setOpaque(true);
        label.setBackground(new Color(245, 245, 245));
        label.setPreferredSize(new Dimension(60, 25));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JButton createButton(String msg) {
        JButton button = new JButton(msg);
        button.setBackground(new Color(201, 75, 36));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(160, 36));
        return button;
    }
}
