package org.example.utils;

import org.example.dao.CustomerDAO;
import org.example.model.Agency;
import org.example.model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.utils.ValidationUtils.*;

public class InputUIPanelStep3 extends JPanel {
    private InputUI parentFrame;

    private JRadioButton skt2Radio;
    private JRadioButton kt2Radio;
    private JRadioButton lgu2Radio;
    private JLabel moveTelecomErrorLabel;

    private JComboBox<String> desiredCombo;
    private JLabel desiredErrorLabel;

    private JLabel generalErrorLabel;

    public InputUIPanelStep3(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel moveTelecomLabel = new JLabel("이동할 통신사 선택");
        moveTelecomLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        centerPanel.add(moveTelecomLabel);

        skt2Radio = new JRadioButton("SKT");
        kt2Radio = new JRadioButton("KT");
        lgu2Radio = new JRadioButton("LG U+");
        ButtonGroup moveGroup = new ButtonGroup();
        moveGroup.add(skt2Radio);
        moveGroup.add(kt2Radio);
        moveGroup.add(lgu2Radio);
        JPanel movePanel = new JPanel();
        movePanel.add(skt2Radio);
        movePanel.add(kt2Radio);
        movePanel.add(lgu2Radio);
        centerPanel.add(movePanel);

        moveTelecomErrorLabel = new JLabel("");
        moveTelecomErrorLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        moveTelecomErrorLabel.setForeground(Color.RED);
        centerPanel.add(moveTelecomErrorLabel);
        centerPanel.add(Box.createVerticalStrut(20));

        JLabel planLabel = new JLabel("희망하는 요금제 가격대를 선택해 주세요.");
        planLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        centerPanel.add(planLabel);

        desiredCombo = new JComboBox<>();
        desiredCombo.addItem("선택하세요");
        desiredCombo.addItem("~ 30,000원");
        desiredCombo.addItem("30,001원 ~ 50,000원");
        desiredCombo.addItem("50,001원 ~ 70,000원");
        desiredCombo.addItem("70,0001원 ~");
        desiredCombo.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        centerPanel.add(desiredCombo);

        desiredErrorLabel = new JLabel("");
        desiredErrorLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        desiredErrorLabel.setForeground(Color.RED);
        centerPanel.add(desiredErrorLabel);
        centerPanel.add(Box.createVerticalStrut(20));

        generalErrorLabel = new JLabel("");
        generalErrorLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        generalErrorLabel.setForeground(Color.RED);
        centerPanel.add(generalErrorLabel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nextBtn = new JButton("다음");
        bottomPanel.add(nextBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isMoveValid = validateMoveTelecom(skt2Radio, kt2Radio, lgu2Radio, moveTelecomErrorLabel);
                boolean isDesiredValid = validateDesiredPlan(desiredCombo, desiredErrorLabel);
                boolean isPageValid = validateThirdPage(moveTelecomErrorLabel, desiredErrorLabel, generalErrorLabel);

                if (isMoveValid && isDesiredValid && isPageValid) {

                    // ✅ 이동통신사 저장
                    Agency desiredAgency = new Agency();
                    if (skt2Radio.isSelected()) {
                        desiredAgency.setId(1);
                    } else if (kt2Radio.isSelected()) {
                        desiredAgency.setId(2);
                    } else if (lgu2Radio.isSelected()) {
                        desiredAgency.setId(3);
                    } else {
                        desiredAgency.setId(0);
                    }
                    parentFrame.customer.setDesiredAgency(desiredAgency);


                    // ✅ 희망 가격 저장
                    String selected = (String) desiredCombo.getSelectedItem();
                    if (selected.contains("~ 30,000")) {
                        parentFrame.customer.setDesiredPrice(30000);
                    } else if (selected.contains("30,001")) {
                        parentFrame.customer.setDesiredPrice(50000);
                    } else if (selected.contains("50,001")) {
                        parentFrame.customer.setDesiredPrice(70000);
                    } else {
                        parentFrame.customer.setDesiredPrice(100000);
                    }

                    // ✅ DB 저장만 추가 (최소 수정 핵심)
                    //CustomerDAO dao = new CustomerDAO();
                    //dao.create(parentFrame.customer);

                    parentFrame.addAndShowLoadingPanel();
                } else {
                    generalErrorLabel.setText("입력값을 다시 확인해주세요.");
                }
            }
        });
        //System.out.println("⚠️ ComparePanel로 이동 전 확인");
        //System.out.println(" - 이동 통신사 ID: " + parentFrame.customer.getDesiredAgency().getId());

    }
}