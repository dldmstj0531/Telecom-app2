package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultPanel extends JPanel {
    private InputUI parentFrame;

    public ResultPanel(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("통신사 변경 가이드 프로그램");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        centerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel TextLabel = new JLabel("입력하신 정보를 바탕으로");
        TextLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
        TextLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(TextLabel);

        JLabel TextLabel2 = new JLabel("예상 위약금과 추천 요금제 및 혜택이 안내됩니다.");
        TextLabel2.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
        TextLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(TextLabel2);

        JLabel subText = new JLabel("확인할 항목을 선택해 주세요.");
        subText.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        subText.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        subText.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(subText);

        ImageIcon image = new ImageIcon("bird1.png");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(imageLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension buttonSize = new Dimension(220, 40);

        // ✅ 버튼 1: 위약금 확인
        JButton toPayButton = new JButton("예상 위약금 확인");
        toPayButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        toPayButton.setBackground(new Color(230, 74, 25));
        toPayButton.setPreferredSize(buttonSize);
        toPayButton.setForeground(Color.WHITE);
        toPayButton.addActionListener(e -> {
            if (parentFrame.getPanel("pay") == null) {
                PayPanel payPanel = new PayPanel(parentFrame);
                parentFrame.cardPanel.add(payPanel, "pay");
                parentFrame.panels.put("pay", payPanel);
            }
            parentFrame.showPanel("pay");
        });
        buttonPanel.add(toPayButton);

        // ✅ 버튼 2: 추천 요금제 확인
        JButton toRecommendButton = new JButton("추천 요금제 확인");
        toRecommendButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        toRecommendButton.setBackground(new Color(230, 74, 25));
        toRecommendButton.setPreferredSize(buttonSize);
        toRecommendButton.setForeground(Color.WHITE);
        toRecommendButton.addActionListener(e -> {
            if (parentFrame.getPanel("recommend1") == null) {
                RecommendPanel1 panel = new RecommendPanel1(parentFrame);
                parentFrame.cardPanel.add(panel, "recommend1");
                parentFrame.panels.put("recommend1", panel);
            }
            parentFrame.showPanel("recommend1");
        });
        buttonPanel.add(toRecommendButton);

        // ✅ 버튼 3: 나의 혜택 보기 (차트 렌더링 필수)
        JButton toMyBenefit = new JButton("나의 혜택 살펴보기");
        toMyBenefit.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        toMyBenefit.setBackground(new Color(230, 74, 25));
        toMyBenefit.setPreferredSize(buttonSize);
        toMyBenefit.setForeground(Color.WHITE);
        toMyBenefit.addActionListener(e -> {
                parentFrame.showBenefitPanel();
        });
        buttonPanel.add(toMyBenefit);

        // ✅ 버튼 4: 통신사 변경 절차
        JButton goNext = new JButton("바로 통신사 변경 절차 보러 가기");
        goNext.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        goNext.setBackground(new Color(230, 74, 25));
        goNext.setPreferredSize(buttonSize);
        goNext.setForeground(Color.WHITE);
        goNext.addActionListener(e -> parentFrame.showPanel("change1"));
        buttonPanel.add(goNext);

        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
    }
}
