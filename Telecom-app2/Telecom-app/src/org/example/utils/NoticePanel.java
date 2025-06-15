package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoticePanel extends JPanel {
    private InputUI parentFrame;

    public NoticePanel(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // 음악 스레드 시작
        //parentFrame.startMusic();

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("통신사 변경 가이드 프로그램");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        topPanel.add(titleLabel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel guideTitle = new JLabel("통신사 변경, 이렇게 진행됩니다.");
        guideTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        guideTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        guideTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel subGuide = new JLabel("복잡하지 않게, 단계별로 알려드릴게요.");
        subGuide.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        subGuide.setAlignmentX(Component.CENTER_ALIGNMENT);
        subGuide.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        centerPanel.add(guideTitle);
        centerPanel.add(subGuide);

        JPanel warningCard = new JPanel();
        warningCard.setBackground(new Color(255, 243, 240));
        warningCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        warningCard.setLayout(new BoxLayout(warningCard, BoxLayout.Y_AXIS));
        warningCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel warningTitle = new JLabel("[주의 사항]");
        warningTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        warningTitle.setForeground(new Color(204, 0, 0));
        warningTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        warningCard.add(warningTitle);
        warningCard.add(Box.createVerticalStrut(10));

        String[] warnings = {
                "<html><b>1. 단말기 할부금은 계속 납부하셔야 합니다.</b><br>" +
                        "통신사를 변경하더라도, 현재 사용 중인 단말기의 할부금은 기존 통신사에 계속 납부해야 합니다.</html>",

                "<html><b>2. 번호 이동 시 본인 명의가 동일해야 합니다.</b><br>" +
                        "현재 회선과 변경할 회선 모두 본인 명의인 경우에만 번호 이동이 가능합니다. (명의가 다른 경우 신규 가입으로 진행)</html>",

                "<html><b>3. 일시정지/분실 등록 상태에서는 변경이 불가능합니다.</b><br>" +
                        "회선 상태가 일시정지 또는 분실 신고 상태일 경우, 먼저 상태를 정상으로 전환한 뒤 변경 절차를 진행해야 합니다.</html>"
        };

        for (String warning : warnings) {
            JLabel label = new JLabel(warning);
            label.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
            label.setForeground(new Color(102, 0, 0));
            label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            warningCard.add(label);
        }

        JButton agreeButton = new JButton("✔ 네, 위 내용을 숙지했습니다.");
        agreeButton.setFont(new Font("Malgun Gothic", Font.BOLD, 11));
        agreeButton.setBackground(new Color(255, 102, 102));
        agreeButton.setForeground(Color.WHITE);
        agreeButton.setFocusPainted(false);
        agreeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        agreeButton.setMaximumSize(new Dimension(250, 35));
        agreeButton.setPreferredSize(new Dimension(250, 35));

        agreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showPanel("result");
            }
        });

        centerPanel.add(warningCard);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(agreeButton);
        add(centerPanel, BorderLayout.CENTER);
    }

    // ✅ 여기 새로 추가!
    public void onShow() {
        parentFrame.startMusic();
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
}
