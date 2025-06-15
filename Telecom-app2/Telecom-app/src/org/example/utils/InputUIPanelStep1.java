package org.example.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import org.example.dao.PlanDAO;
import org.example.model.Agency;
import org.example.model.Customer;
import org.example.model.Plan;

import static org.example.utils.ValidationUtils.*;

public class InputUIPanelStep1 extends JPanel {
    private InputUI parentFrame;

    private JTextField phoneField;
    private JLabel phoneErrorLabel;

    private JTextField ageField;
    private JLabel ageErrorLabel;

    private JRadioButton sktRadio;
    private JRadioButton ktRadio;
    private JRadioButton lguRadio;
    private JLabel telecomErrorLabel;

    private JRadioButton twelveRadio;
    private JRadioButton twentyFourRadio;
    private JLabel contractErrorLabel;

    private JComboBox<String> usageComboBox;
    private JLabel usageMonthErrorLabel;

    private JComboBox<String> planComboBox;
    private List<Plan> currentPlanList;

    private JLabel generalErrorLabel;

    public InputUIPanelStep1(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        centerPanel.add(new JLabel("전화번호 입력"));
        phoneField = new JTextField();
        phoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(phoneField);
        phoneErrorLabel = createErrorLabel();
        centerPanel.add(phoneErrorLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        centerPanel.add(new JLabel("만 나이 입력"));
        ageField = new JTextField();
        ageField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(ageField);
        ageErrorLabel = createErrorLabel();
        centerPanel.add(ageErrorLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        centerPanel.add(new JLabel("이용 중인 통신사 선택"));
        sktRadio = new JRadioButton("SKT");
        ktRadio = new JRadioButton("KT");
        lguRadio = new JRadioButton("LG U+");
        ButtonGroup telecomGroup = new ButtonGroup();
        telecomGroup.add(sktRadio);
        telecomGroup.add(ktRadio);
        telecomGroup.add(lguRadio);
        JPanel telecomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        telecomPanel.add(sktRadio);
        telecomPanel.add(ktRadio);
        telecomPanel.add(lguRadio);
        centerPanel.add(telecomPanel);
        telecomErrorLabel = createErrorLabel();
        centerPanel.add(telecomErrorLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        centerPanel.add(new JLabel("약정 기간 선택"));
        twelveRadio = new JRadioButton("12개월");
        twentyFourRadio = new JRadioButton("24개월");
        ButtonGroup contractGroup = new ButtonGroup();
        contractGroup.add(twelveRadio);
        contractGroup.add(twentyFourRadio);
        JPanel contractPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contractPanel.add(twelveRadio);
        contractPanel.add(twentyFourRadio);
        centerPanel.add(contractPanel);
        contractErrorLabel = createErrorLabel();
        centerPanel.add(contractErrorLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        centerPanel.add(new JLabel("요금제 이용 개월 수"));
        String[] months = new String[25];
        months[0] = "선택";
        for (int i = 1; i <= 24; i++) months[i] = i + "개월";
        usageComboBox = new JComboBox<>(months);
        centerPanel.add(usageComboBox);
        usageMonthErrorLabel = createErrorLabel();
        centerPanel.add(usageMonthErrorLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        centerPanel.add(new JLabel("현재 사용 중인 요금제 선택"));
        planComboBox = new JComboBox<>();
        planComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        centerPanel.add(planComboBox);

        // 통신사 선택에 따른 요금제 자동 로딩
        ActionListener telecomListener = e -> {
            int agencyId = sktRadio.isSelected() ? 1 : ktRadio.isSelected() ? 2 : 3;
            loadPlansByAgencyId(agencyId);
        };
        sktRadio.addActionListener(telecomListener);
        ktRadio.addActionListener(telecomListener);
        lguRadio.addActionListener(telecomListener);

        generalErrorLabel = createErrorLabel();
        centerPanel.add(generalErrorLabel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nextBtn = new JButton("다음");
        bottomPanel.add(nextBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isPhoneValid = validatePhoneNumber(phoneField, phoneErrorLabel);
                boolean isAgeValid = validateAge(ageField, ageErrorLabel);
                boolean isTelecomValid = validateCurrentTelecom(sktRadio, ktRadio, lguRadio, telecomErrorLabel);
                boolean isContractValid = validateContractPeriod(twelveRadio, twentyFourRadio, contractErrorLabel);
                boolean isUsageValid = validateUsageMonth(usageComboBox, usageMonthErrorLabel);
                boolean isPageValid = validateSecondPage(telecomErrorLabel, contractErrorLabel, usageMonthErrorLabel, generalErrorLabel);

                if (isPhoneValid && isAgeValid && isTelecomValid &&
                        isContractValid && isUsageValid && isPageValid) {

                    // ✅ 핵심 수정 시작
                    if (parentFrame.customer == null) {
                        parentFrame.customer = new Customer();
                    }
                    parentFrame.customer.setPhoneNumber(phoneField.getText().trim());
                    parentFrame.customer.setAge(Integer.parseInt(ageField.getText().trim()));
                    parentFrame.customer.setMonthsUsingPlan(usageComboBox.getSelectedIndex());

                    int agencyId = sktRadio.isSelected() ? 1 : ktRadio.isSelected() ? 2 : 3;
                    Agency currentAgency = new Agency();
                    currentAgency.setId(agencyId);
                    parentFrame.customer.setCurrentAgency(currentAgency);

                    int contractMonths = twelveRadio.isSelected() ? 12 : 24;

                    if (currentPlanList == null || currentPlanList.isEmpty()) {
                        generalErrorLabel.setText("통신사 선택 후 요금제를 선택해주세요.");
                        return;
                    }
                    String selectedPlanName = (String) planComboBox.getSelectedItem();
                    Plan selectedPlan = currentPlanList.stream()
                            .filter(p -> p.getName().equals(selectedPlanName))
                            .findFirst()
                            .orElse(null);
                    if (selectedPlan == null) {
                        generalErrorLabel.setText("요금제를 선택해주세요.");
                        return;
                    }
                    Plan plan = new Plan();
                    plan.setId(selectedPlan.getId());
                    plan.setName(selectedPlan.getName());
                    plan.setMonthlyFee(selectedPlan.getMonthlyFee());
                    plan.setContractMonths(contractMonths);
                    plan.setDataAmount(selectedPlan.getDataAmount());
                    parentFrame.customer.setCurrentPlan(plan);

                    parentFrame.showPanel("step2");
                } else {
                    generalErrorLabel.setText("입력값을 다시 확인해주세요.");
                }
            }
        });
    }

    private JLabel createErrorLabel() {
        JLabel label = new JLabel("");
        label.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        label.setForeground(Color.RED);
        return label;
    }

    private void loadPlansByAgencyId(int agencyId) {
        planComboBox.removeAllItems();
        PlanDAO dao = new PlanDAO();
        currentPlanList = dao.getPlansByAgencyId(agencyId);
        for (Plan p : currentPlanList) {
            planComboBox.addItem(p.getName());
        }
    }
}
