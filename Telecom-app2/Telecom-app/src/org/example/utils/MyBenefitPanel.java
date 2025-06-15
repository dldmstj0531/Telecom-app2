package org.example.utils;

import org.example.logic.BenefitChecker;
import org.example.model.Customer;
import org.example.model.Discount;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class MyBenefitPanel extends JPanel {

    private JTable benefitTable;
    private DefaultTableModel tableModel;
    private InputUI parentFrame;
    private JPanel centerContentPanel;

    public MyBenefitPanel(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("나의 혜택 살펴보기", SwingConstants.CENTER);
        title.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        centerContentPanel = new JPanel();
        centerContentPanel.setLayout(new BoxLayout(centerContentPanel, BoxLayout.Y_AXIS));
        centerContentPanel.setBackground(Color.WHITE);
        add(centerContentPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("뒤로 가기");
        backButton.addActionListener(e -> parentFrame.showPanel("result"));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void displayBenefits() {
        centerContentPanel.removeAll();

        Customer customer = parentFrame.customer;
        List<Discount> benefits = BenefitChecker.checkBenefits(customer);

        String[] columnNames = {"혜택 항목", "할인율", "추가 혜택"};
        tableModel = new DefaultTableModel(columnNames, 0);
        benefitTable = new JTable(tableModel);
        benefitTable.setRowHeight(28);
        benefitTable.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < benefitTable.getColumnCount(); i++) {
            benefitTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        if (benefits.isEmpty()) {
            tableModel.addRow(new Object[]{"적용 가능한 혜택 없음", "-", "-"});
        } else {
            for (Discount discount : benefits) {
                String extra = discount.getExtraBenefit() != null ? discount.getExtraBenefit() : "-";
                tableModel.addRow(new Object[]{discount.getName(), discount.getDiscountAmount(), extra});
            }
        }

        double totalDiscount = BenefitChecker.calculateTotalDiscount(customer);
        int discountPercent = (int)(totalDiscount * 100);
        tableModel.addRow(new Object[]{"총 할인율", discountPercent + "%", "-"});

        JScrollPane scrollPane = new JScrollPane(benefitTable);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerContentPanel.add(scrollPane);

        JLabel label = new JLabel("유형1: 장애인/국가유공자, 유형2: 생계/의료급여, 유형3: 주거/교육급여 및 차상위, 유형4: 기초연금수급자");
        label.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerContentPanel.add(label);
        centerContentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel graphPanel = createGraphs(totalDiscount, customer.getCurrentPlan().getMonthlyFee(), customer);
        graphPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerContentPanel.add(graphPanel);

        revalidate();
        repaint();
    }

    private JPanel createGraphs(double discountRate, int originalFee, Customer customer) {
        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new GridLayout(1, 2, 20, 20));
        graphPanel.setPreferredSize(new Dimension(900, 400));
        graphPanel.setBackground(Color.WHITE);

        // 바 차트
        CategoryChart barChart = new CategoryChartBuilder()
                .width(400).height(300)
                .title("할인 전후 요금 비교")
                .xAxisTitle("").yAxisTitle("금액(원)").build();

        barChart.getStyler().setSeriesColors(new Color[]{new Color(242, 164, 140), new Color(242, 164, 140)});
        barChart.getStyler().setChartBackgroundColor(Color.WHITE);
        barChart.getStyler().setPlotBackgroundColor(Color.WHITE);
        barChart.getStyler().setLegendVisible(false);
        barChart.getStyler().setAxisTitleFont(new Font("Malgun Gothic", Font.BOLD, 14));

        barChart.addSeries("요금", List.of("할인 전", "할인 후"), List.of(originalFee, originalFee));
        JPanel barPanel = new XChartPanel<>(barChart);
        graphPanel.add(barPanel);

        // 꺾은선 차트
        List<Double> priceList = new ArrayList<>();
        priceList.add((double) originalFee);
        priceList.add(BenefitChecker.calculateFeeWithSpecificBenefit(customer, originalFee, "handicapped"));
        priceList.add(BenefitChecker.calculateFeeWithSpecificBenefit(customer, originalFee, "basicLivelihood"));
        priceList.add(BenefitChecker.calculateFeeWithSpecificBenefit(customer, originalFee, "housingEducation"));
        priceList.add(BenefitChecker.calculateFeeWithSpecificBenefit(customer, originalFee, "pension"));

        double[] xData = {0, 1, 2, 3, 4};
        double[] yData = priceList.stream().mapToDouble(Double::doubleValue).toArray();

        XYChart lineChart = new XYChartBuilder()
                .width(400).height(300)
                .title("혜택 적용 시 월 요금 비교")
                .xAxisTitle("혜택 유형").yAxisTitle("금액(원)").build();

        lineChart.getStyler().setSeriesColors(new Color[]{new Color(230, 74, 25)});
        lineChart.getStyler().setChartBackgroundColor(Color.WHITE);
        lineChart.getStyler().setPlotBackgroundColor(Color.WHITE);
        lineChart.getStyler().setLegendVisible(false);
        lineChart.getStyler().setAxisTitleFont(new Font("Malgun Gothic", Font.BOLD, 14));
        lineChart.getStyler().setMarkerSize(6);
        lineChart.getStyler().setAxisTickLabelsFont(new Font("Malgun Gothic", Font.PLAIN, 10));

        lineChart.addSeries("할인 적용 요금", xData, yData).setMarker(SeriesMarkers.CIRCLE);

        JPanel linePanel = new XChartPanel<>(lineChart);
        graphPanel.add(linePanel);

        int appliedFee = (int)(originalFee * (1 - discountRate));
        animateBarChart(barChart, originalFee, appliedFee, barPanel);

        return graphPanel;
    }

    private void animateBarChart(CategoryChart chart, int originalFee, int discountedFee, JPanel chartPanel) {
        Timer timer = new Timer(20, null);
        int steps = 50;
        int[] currentStep = {0};

        timer.addActionListener(e -> {
            currentStep[0]++;
            double ratio = currentStep[0] / (double) steps;
            int intermediateFee = (int)(originalFee - (originalFee - discountedFee) * ratio);

            chart.updateCategorySeries("요금", List.of("할인 전", "할인 후"), List.of(originalFee, intermediateFee), null);
            chartPanel.repaint();

            if (currentStep[0] >= steps) {
                timer.stop();
            }
        });

        timer.start();
    }
}


