package org.example.utils;

import javax.swing.*;
import java.awt.*;

public class ChangePanel3 extends JPanel {
    private InputUI parentFrame;
    public ChangePanel3(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("통신사 변경 가이드 프로그램");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel stepLabel = new JLabel("3단계: 개통 및 통신사 변경 완료");
        stepLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        stepLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(stepLabel);

        JLabel descLabel = new JLabel("기존 회선은 자동 해지되며, 변경 완효 무자 수신 후부터 새 통신사 이용이 시작됩니다.");
        descLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(descLabel);

        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(createInfoBox("유심 장착 후 개통 진행(온라인 진행 혹은 자동 개통)", new String[]{
                "준비한 유심을 단말기에 장착하면 자동으로 개통이 진행됩니다.",
                "• 온라인 개통 : 통신사 앱 또는 개통 전용 웹사이트 접속",
                "• 자동 개통 : 유심 장착 후 일정 시간 내 자동 활성화",
                "   개통이 시작되면 기존 회선은 자동 해지됩니다."
        }));

        centerPanel.add(Box.createVerticalStrut(15));

        centerPanel.add(createInfoBox("개통 완료 메세지 수신", new String[]{
                "• 개통이 완료되면 문자로 안내 메세지가 발송됩니다.",
                "• 메세지 수신 후 바로 새 통신사 회선을 사용할 수 있습니다."
        }));

        centerPanel.add(Box.createVerticalStrut(15));

        centerPanel.add(createInfoBox("변경된 통신사 회선으로 정상 통화 및 데이터 이용 가능", new String[]{
                "• 개통 후 기존 회선은 자동 해지되며, 새 통신사 회선으로 전환됩니다.",
                "• 정상적으로 통화 및 데이터 사용이 가능합니다."
        }));

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 하나의 패널만 사용

        JButton backBtn = new JButton("뒤로 가기");
        backBtn.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        backBtn.setBackground(Color.LIGHT_GRAY);
        backBtn.setForeground(Color.BLACK);
        backBtn.addActionListener(e -> parentFrame.showPanel("change2"));

        JButton nextBtn = new JButton("다음");
        nextBtn.setPreferredSize(new Dimension(80, 30));
        nextBtn.addActionListener(e -> parentFrame.showPanel("finish"));

        bottomPanel.add(backBtn);
        bottomPanel.add(nextBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createInfoBox(String title, String[] lines) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        box.add(titleLabel);
        box.add(Box.createVerticalStrut(8));

        for (String line : lines) {
            JLabel label = new JLabel(line);
            label.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
            box.add(label);
        }

        return box;
    }
}