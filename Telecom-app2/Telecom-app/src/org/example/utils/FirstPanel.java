package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FirstPanel extends JPanel {
    private InputUI frame;

    public FirstPanel() {
        setLayout(new BorderLayout());

        // 상단 타이틀 영역
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("통신사 변경 가이드 프로그램");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // 중앙 안내 영역
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel mainTitle = new JLabel("통신사 변경을 고려 중이신가요?");
        mainTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 35));
        mainTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(mainTitle);

        JLabel subText = new JLabel("[프로그램 이름]을 통해 통신사 변경 시 알아두어야 하는 정보를 무료로 확인해보세요.");
        subText.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        subText.setForeground(Color.GRAY);
        subText.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(subText);

        JButton startButton = new JButton("통신사 변경하기");
        startButton.setBackground(new Color(230, 74, 25));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        startButton.setPreferredSize(new Dimension(160, 40));
        startButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 현재 창 닫기
                SwingUtilities.getWindowAncestor(FirstPanel.this).dispose();

                // 새로운 입력 화면 창 열기
                InputUI inputUI = new InputUI();
                inputUI.setTitle("입력 화면");
                inputUI.setSize(700, 700);
                inputUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                inputUI.setVisible(true);
            }
        });

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(startButton);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JLabel createBoxedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Malgun Gothic", Font.BOLD, 12));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setOpaque(true);
        label.setBackground(new Color(245, 245, 245));
        label.setPreferredSize(new Dimension(60, 25));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}
