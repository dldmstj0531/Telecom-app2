package org.example.utils;

import javax.swing.*;
import java.awt.*;

public class ChangePanel2 extends JPanel {
    private InputUI parentFrame;
    public ChangePanel2(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("통신사 변경 가이드 프로그램");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel stepLabel = new JLabel("2단계: 신청 접수 및 유심 배송 / 준비");
        stepLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        stepLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(stepLabel);

        JLabel descLabel = new JLabel("통신사에 따라 유심 재사용 가능 여부가 다를 수 있으며, 일부는 필요합니다.");
        descLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(descLabel);

        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(createInfoBox("온라인 또는 오프라인 접수 완료", new String[]{
                "가입자 본인 여부 확인을 위해, 아래 방법 중 하나로 인증을 진행해주세요.",
                "• 온라인 신청 : 통신사 홈페이지 앱, 제휴 사이트",
                "• 오프라인 신청 : 가까운 대리점 방문",
                "   신청 후, 접수 완료 안내 메세지를 꼭 확인해 주세요."
        }));

        centerPanel.add(Box.createVerticalStrut(15));

        centerPanel.add(createInfoBox("기존 유심 사용 혹은 새 유심 배송 받기", new String[]{
                "기존 유심 사용",
                "• 기존에 사용하던 유심이 재사용 가능한 경우, 별도의 유심 배송 없이 그대로 이용할 수 있습니다.",
                "• 유심 재사용 가능 여부는 통신사 또는 단말기 상태에 따라 달라질 수 있으니, 신청시 확인해 주세요."
        }));

        centerPanel.add(Box.createVerticalStrut(15));

        centerPanel.add(createInfoBox("베송받은 유심 준비 또는 대리점 방문 준비", new String[]{
                "유심 수령 후 안내 메세지를 참고하여 단말기에 직접 장착해 주세요.",
                "직접 개통이 어려운 경우, 가까운 대리점을 방문해 주세요.",
                "• 유심 장착 및 개통을 직원이 도와드립니다.",
                "• 신분증을 꼭 지참해 주세요."
        }));

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 하나의 패널만 사용

        JButton backBtn = new JButton("뒤로 가기");
        backBtn.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        backBtn.setBackground(Color.LIGHT_GRAY);
        backBtn.setForeground(Color.BLACK);
        backBtn.addActionListener(e -> parentFrame.showPanel("change1"));

        JButton nextBtn = new JButton("다음");
        nextBtn.setPreferredSize(new Dimension(80, 30));
        nextBtn.addActionListener(e -> parentFrame.showPanel("change3"));

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
