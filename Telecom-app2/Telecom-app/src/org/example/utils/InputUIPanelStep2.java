package org.example.utils;

import org.example.model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.utils.ValidationUtils.*;

public class InputUIPanelStep2 extends JPanel {
    private InputUI parentFrame;

    private JRadioButton disabilityYes, disabilityNo;
    private JLabel disabilityErrorLabel;

    private JRadioButton basicLivingYes, basicLivingNo;
    private JLabel basicLivingErrorLabel;

    private JRadioButton housingEducationYes, housingEducationNo;
    private JLabel housingEducationErrorLabel;

    private JRadioButton basicPensionYes, basicPensionNo;
    private JLabel basicPensionErrorLabel;

    private JLabel generalErrorLabel;

    public InputUIPanelStep2(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        centerPanel.add(createBenefitPanel("장애인/국가유공자",
                disabilityYes = new JRadioButton("예"),
                disabilityNo = new JRadioButton("아니오"),
                disabilityErrorLabel = createErrorLabel()));

        centerPanel.add(createBenefitPanel("생계/의료 급여 수급자",
                basicLivingYes = new JRadioButton("예"),
                basicLivingNo = new JRadioButton("아니오"),
                basicLivingErrorLabel = createErrorLabel()));

        centerPanel.add(createBenefitPanel("주거/교육급여 수급자 및 차상위 계층",
                housingEducationYes = new JRadioButton("예"),
                housingEducationNo = new JRadioButton("아니오"),
                housingEducationErrorLabel = createErrorLabel()));

        centerPanel.add(createBenefitPanel("기초연금수급자",
                basicPensionYes = new JRadioButton("예"),
                basicPensionNo = new JRadioButton("아니오"),
                basicPensionErrorLabel = createErrorLabel()));

        generalErrorLabel = createErrorLabel();
        centerPanel.add(generalErrorLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JButton nextBtn = new JButton("다음");
        nextBtn.setPreferredSize(new Dimension(80, 30));
        nextBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);

        nextBtn.addActionListener(e -> {
            boolean isValid = validateAllSelections();
            if (isValid) {
                applyToCustomer(parentFrame.customer);
                parentFrame.showPanel("step3");
            } else {
                generalErrorLabel.setText("모든 항목을 선택해주세요.");
            }
        });

        bottomPanel.add(nextBtn);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createBenefitPanel(String labelText, JRadioButton yesButton, JRadioButton noButton, JLabel errorLabel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        ButtonGroup group = new ButtonGroup();
        group.add(yesButton);
        group.add(noButton);
        panel.add(label);
        panel.add(yesButton);
        panel.add(noButton);
        panel.add(errorLabel);
        return panel;
    }


    private JLabel createErrorLabel() {
        JLabel label = new JLabel(" ");
        label.setForeground(Color.RED);
        label.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        return label;
    }

    private boolean validateAllSelections() {
        boolean valid = true;

        if (!disabilityYes.isSelected() && !disabilityNo.isSelected()) {
            disabilityErrorLabel.setText("선택해주세요.");
            valid = false;
        } else {
            disabilityErrorLabel.setText(" ");
        }

        if (!basicLivingYes.isSelected() && !basicLivingNo.isSelected()) {
            basicLivingErrorLabel.setText("선택해주세요.");
            valid = false;
        } else {
            basicLivingErrorLabel.setText(" ");
        }

        if (!housingEducationYes.isSelected() && !housingEducationNo.isSelected()) {
            housingEducationErrorLabel.setText("선택해주세요.");
            valid = false;
        } else {
            housingEducationErrorLabel.setText(" ");
        }

        if (!basicPensionYes.isSelected() && !basicPensionNo.isSelected()) {
            basicPensionErrorLabel.setText("선택해주세요.");
            valid = false;
        } else {
            basicPensionErrorLabel.setText(" ");
        }

        return valid;
    }

    private void applyToCustomer(Customer customer) {
        customer.setDisabled(disabilityYes.isSelected());
        customer.setLivingSupport(basicLivingYes.isSelected());
        customer.setHousingSupport(housingEducationYes.isSelected());
        customer.setPensioner(basicPensionYes.isSelected());
    }
}
