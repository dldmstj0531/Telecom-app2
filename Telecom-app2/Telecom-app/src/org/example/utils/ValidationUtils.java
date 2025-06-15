package org.example.utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ValidationUtils {

    // 스타일 설정
    public static class UITheme {
        public static final Color ERROR_BORDER_COLOR = Color.RED;
        public static final Color ERROR_TEXT_COLOR = Color.RED;
        public static final Font ERROR_FONT = new Font("Monospaced", Font.PLAIN, 11);
        public static final Border DEFAULT_TEXTFIELD_BORDER = UIManager.getLookAndFeelDefaults().getBorder("TextField.border");
    }

    // 전화번호 유효성 검사
    public static boolean validatePhoneNumber(JTextField field, JLabel errorLabel) {
        String input = field.getText().trim();
        if (!input.matches("^010\\d{8}$")) {
            applyErrorStyle(field, errorLabel, "010으로 시작하는 11자리 번호를 입력해주세요.");
            return false;
        } else {
            resetFieldStyle(field, errorLabel);
            return true;
        }
    }

    // 나이 유효성 검사
    public static boolean validateAge(JTextField field, JLabel errorLabel) {
        try {
            int age = Integer.parseInt(field.getText().trim());
            if (age < 0 || age > 150) {
                applyErrorStyle(field, errorLabel, "나이를 정확히 입력해주세요.");
                return false;
            } else {
                resetFieldStyle(field, errorLabel);
                return true;
            }
        } catch (NumberFormatException e) {
            applyErrorStyle(field, errorLabel, "숫자로 입력해주세요.");
            return false;
        }
    }

    // 현재 이용 중 통신사 선택 유효성 검사
    public static boolean validateCurrentTelecom(JRadioButton skt, JRadioButton kt, JRadioButton lgu, JLabel errorLabel) {
        if (!skt.isSelected() && !kt.isSelected() && !lgu.isSelected()) {
            errorLabel.setText("이용 중인 통신사를 선택해주세요.");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }

    // 약정 기간 선택 유효성 검사
    public static boolean validateContractPeriod(JRadioButton twelve, JRadioButton twentyFour, JLabel errorLabel) {
        if (!twelve.isSelected() && !twentyFour.isSelected()) {
            errorLabel.setText("약정 기간을 선택해주세요.");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }

    // 요금제 개월 수 유효성 검사
    public static boolean validateUsageMonth(JComboBox<?> comboBox, JLabel errorLabel) {
        if (comboBox.getSelectedIndex() == 0) {
            errorLabel.setText("이용 개월 수를 선택해주세요.");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }

    // 이동할 통신사 유효성 검사
    public static boolean validateMoveTelecom(JRadioButton skt2, JRadioButton kt2, JRadioButton lgu2, JLabel errorLabel) {
        if (!skt2.isSelected() && !kt2.isSelected() && !lgu2.isSelected()) {
            errorLabel.setText("이동할 통신사를 선택해주세요.");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }

    // 희망 요금제 가격대 선택 유효성 검사
    public static boolean validateDesiredPlan(JComboBox<?> comboBox, JLabel errorLabel) {
        if (comboBox.getSelectedIndex() == 0) {
            errorLabel.setText("희망 요금제를 선택해주세요.");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }

    // 2차 패널 전체 검사
    public static boolean validateSecondPage(
            JLabel telecomError, JLabel contractError, JLabel usageError, JLabel generalError) {

        if (!telecomError.getText().isEmpty() ||
                !contractError.getText().isEmpty() ||
                !usageError.getText().isEmpty()) {
            generalError.setText("입력값을 확인해주세요 (빨간 항목).");
            return false;
        }
        generalError.setText("");
        return true;
    }

    // 3차 패널 전체 검사
    public static boolean validateThirdPage(JLabel telecomError, JLabel desiredError, JLabel generalError) {
        if (!telecomError.getText().isEmpty() || !desiredError.getText().isEmpty()) {
            generalError.setText("입력값을 확인해주세요 (빨간 항목).");
            return false;
        }
        generalError.setText("");
        return true;
    }

    // 현재 요금제 선택 유효성 검사
    public static boolean validateSelectedPlan(JComboBox<?> comboBox, JLabel errorLabel) {
        if (comboBox.getSelectedIndex() == -1 || comboBox.getSelectedItem() == null) {
            errorLabel.setText("현재 사용 중인 요금제를 선택해주세요.");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }

    // 오류 스타일 적용
    public static void applyErrorStyle(JComponent component, JLabel errorLabel, String message) {
        if (component instanceof JTextField) {
            component.setBorder(new LineBorder(UITheme.ERROR_BORDER_COLOR, 2));
        }
        errorLabel.setText(message);
        errorLabel.setForeground(UITheme.ERROR_TEXT_COLOR);
        errorLabel.setFont(UITheme.ERROR_FONT);
    }

    // 오류 스타일 제거
    public static void resetFieldStyle(JTextField field, JLabel errorLabel) {
        field.setBorder(UITheme.DEFAULT_TEXTFIELD_BORDER);
        errorLabel.setText("");
    }
}
