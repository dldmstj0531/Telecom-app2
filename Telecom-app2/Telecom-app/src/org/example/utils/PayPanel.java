package org.example.utils;

import org.example.logic.BenefitChecker;
import org.example.logic.PenaltyCalculator;
import org.example.model.Customer;
import org.example.model.Plan;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;

public class PayPanel extends JPanel {
    private InputUI parentFrame;
    private PieChart pieChart;
    private XChartPanel<PieChart> chartPanel;

    public PayPanel(InputUI parentFrame) {
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

        JLabel title = new JLabel("예상 위약금 내역을 아래에서 확인하세요.");
        title.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("입력하신 정보를 바탕으로 계산된 금액입니다.");
        subtitle.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(title);
        centerPanel.add(subtitle);

        // ✅ 데이터 가져오기
        Customer customer = parentFrame.customer;
        Plan plan = customer.getCurrentPlan();

        String telecom = switch (customer.getCurrentAgency().getId()) {
            case 1 -> "SKT";
            case 2 -> "KT";
            case 3 -> "LG U+";
            default -> "알 수 없음";
        };

        String[][] inputInfo = {
                {"현재 이용 중인 통신사", telecom},
                {"현재 이용 중인 요금제 약정 기간", plan.getContractMonths() + "개월"},
                {"현재 이용 중인 요금제 이용 기간", customer.getMonthsUsingPlan() + "개월"},
                {"현재 이용 중인 요금제 금액", String.format("%,d원", plan.getMonthlyFee())}
        };

        centerPanel.add(createSectionLabel("1. 입력하신 정보는 다음과 같습니다."));
        centerPanel.add(createInfoBox(inputInfo));

        int usedMonths = customer.getMonthsUsingPlan();
        int remainingMonths = plan.getContractMonths() - customer.getMonthsUsingPlan();
        double discountRate = BenefitChecker.calculateTotalDiscount(customer);
        int discountedMonthlyFee = (int)(plan.getMonthlyFee() * (1 - discountRate));
        int totalDiscount = (plan.getMonthlyFee() - discountedMonthlyFee) * remainingMonths;

        int penalty = PenaltyCalculator.calculatePenalty(customer);

        String[][] penaltyInfo = {
                {"A. 남은 약정 기간", remainingMonths + "개월"},
                {"B. 총 할인 적용 금액", String.format("%,d원", totalDiscount)},
                {"C. 선택 약정 위약금", String.format("%,d원", penalty)}
        };

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(createSectionLabel("2. 예상 위약금 정보를 확인하세요."));
        centerPanel.add(createInfoBox(penaltyInfo));

        // ✅ 원 그래프 생성 (초기값 0으로 시작)
        pieChart = new PieChartBuilder().width(700).height(500).title("약정 이용 기간 비율").build();
        pieChart.addSeries("사용한 개월 수", 0);
        pieChart.addSeries("남은 개월 수", 0);

        chartPanel = new XChartPanel<>(pieChart);
        chartPanel.setPreferredSize(new Dimension(700, 500));
        chartPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(chartPanel);

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backBtn = new JButton("뒤로 가기");
        backBtn.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        backBtn.setBackground(Color.LIGHT_GRAY);
        backBtn.setForeground(Color.BLACK);
        backBtn.addActionListener(e -> parentFrame.showPanel("result"));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // ✅ 애니메이션 시작
        animatePieChart(usedMonths, remainingMonths);
    }

    private void animatePieChart(int usedMonths, int remainingMonths) {
        int steps = 50;
        int[] currentStep = {0};
        Timer timer = new Timer(20, null);

        timer.addActionListener(e -> {
            currentStep[0]++;
            double ratio = currentStep[0] / (double) steps;
            int used = (int) (usedMonths * ratio);
            int remain = (int) (remainingMonths * ratio);

            pieChart.updatePieSeries("사용한 개월 수", used);
            pieChart.updatePieSeries("남은 개월 수", remain);
            chartPanel.repaint();

            if (currentStep[0] >= steps) timer.stop();
        });

        timer.start();
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return label;
    }

    private JPanel createInfoBox(String[][] data) {
        JPanel box = new JPanel();
        box.setLayout(new GridLayout(data.length, 2, 10, 10));
        box.setBackground(new Color(255, 245, 245));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 200, 200)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        for (String[] row : data) {
            JLabel keyLabel = new JLabel(row[0]);
            keyLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
            JLabel valueLabel = new JLabel(row[1]);
            valueLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
            valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            box.add(keyLabel);
            box.add(valueLabel);
        }

        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, box.getPreferredSize().height));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        return box;
    }
}

