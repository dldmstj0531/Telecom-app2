package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePanel1 extends JPanel {
    private InputUI parentFrame;
    public ChangePanel1(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("통신사 변경 가이드 프로그램");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel stepLabel = new JLabel("1단계: 본인 인증 및 신청서 작성");
        stepLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        stepLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(stepLabel);

        JLabel descLabel = new JLabel("변경은 반드시 본인 명의 인증이 필요하며, 정확한 정보 입력이 중요합니다.");
        descLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(descLabel);

        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(createInfoBox("휴대폰 본인 인증 진행", new String[]{
                "가입자 본인 여부 확인을 위해, 아래 방법 중 하나로 인증을 진행해주세요.",
                "• 휴대폰 번호 입력 후 인증번호 수신",
                "• PASS앱 또는 카카오페이에 인증 연동 가능",
                "• 본인 명의가 아닐 경우, 변경 신청이 제한될 수 있습니다.",
                "",
                "인증은 보통 1분 이내에 완료되며,",
                "정확한 정보를 입력하셔야 통신사 변경이 원활히 진행됩니다."
        }));

        centerPanel.add(Box.createVerticalStrut(15));

        centerPanel.add(createInfoBox("약관 동의 및 통신사 변경 신청서 작성", new String[]{
                "변경 신청에 필요한 약관 동의 및 간단한 신청서 작성을 진행합니다.",
                "• 약관 확인 및 동의 체크",
                "• 희망 요금제 및 부가서비스 선택"
        }));

        centerPanel.add(Box.createVerticalStrut(15));

        centerPanel.add(createInfoBox("신청 내역 확인", new String[]{
                "작성한 변경 내용 및 정보가 정확한지 확인해주세요.",
                "• 수정 사항이 있다면 다시 돌아가서 변경 가능",
                "• 최종 확인 후 접수"
        }));

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 하나의 패널만 사용

        JButton backBtn = new JButton("뒤로 가기");
        backBtn.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        backBtn.setBackground(Color.LIGHT_GRAY);
        backBtn.setForeground(Color.BLACK);
        backBtn.addActionListener(e -> parentFrame.showPanel("result"));

        JButton nextBtn = new JButton("다음");
        nextBtn.setPreferredSize(new Dimension(80, 30));
        nextBtn.addActionListener(e -> parentFrame.showPanel("change2"));

        bottomPanel.add(backBtn);
        bottomPanel.add(nextBtn);

        add(bottomPanel, BorderLayout.SOUTH); // 단 하나만 추가

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
