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

    public MyBenefitPanel(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("나의 혜택 살펴보기", SwingConstants.CENTER);
        title.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

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

        JScrollPane scrollPane = new JScrollPane(benefitTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("뒤로 가기");
        backButton.addActionListener(e -> parentFrame.showPanel("result"));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void displayBenefits() {
        tableModel.setRowCount(0);

        Customer customer = parentFrame.customer;
        List<Discount> benefits = BenefitChecker.checkBenefits(customer);

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

        addGraphsWithAnimation(totalDiscount, customer.getCurrentPlan().getMonthlyFee());
    }

    private void addGraphsWithAnimation(double discountRate, int originalFee) {
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel && "graphPanel".equals(comp.getName())) {
                remove(comp);
            }
        }

        JPanel graphPanel = new JPanel();
        graphPanel.setName("graphPanel");
        graphPanel.setLayout(new GridLayout(1, 2));
        graphPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        graphPanel.setBackground(Color.WHITE);

        // ---------------- 바 차트 ----------------
        CategoryChart barChart = new CategoryChartBuilder()
                .width(400).height(300)
                .title("할인 전후 요금 비교")
                .xAxisTitle("").yAxisTitle("금액(원)").build();

        barChart.getStyler().setSeriesColors(new Color[]{new Color(52, 152, 219), new Color(46, 204, 113)});
        barChart.getStyler().setChartBackgroundColor(Color.WHITE);
        barChart.getStyler().setPlotBackgroundColor(Color.WHITE);
        barChart.getStyler().setLegendVisible(false);
        barChart.getStyler().setAxisTitleFont(new Font("Malgun Gothic", Font.BOLD, 14));

        barChart.addSeries("요금", List.of("할인 전", "할인 후"), List.of(originalFee, originalFee));
        JPanel barPanel = new XChartPanel<>(barChart);
        graphPanel.add(barPanel);

        // ---------------- 꺾은선 그래프 ----------------
        XYChart lineChart = new XYChartBuilder().width(400).height(300)
                .title("할인율 변화에 따른 요금")
                .xAxisTitle("할인율(%)").yAxisTitle("금액(원)").build();

        lineChart.getStyler().setSeriesColors(new Color[]{new Color(52, 152, 219), new Color(241, 196, 15)});
        lineChart.getStyler().setChartBackgroundColor(Color.WHITE);
        lineChart.getStyler().setPlotBackgroundColor(Color.WHITE);
        lineChart.getStyler().setMarkerSize(6);
        lineChart.getStyler().setLegendFont(new Font("Malgun Gothic", Font.PLAIN, 12));

        int[] discountSteps = {0, 10, 20, 25, 30, 40};
        List<Integer> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for (int d : discountSteps) {
            xData.add(d);
            yData.add((int)(originalFee * (1 - d / 100.0)));
        }

        lineChart.addSeries("요금 변동", xData, yData).setMarker(SeriesMarkers.CIRCLE);
        int appliedPercent = (int)(discountRate * 100);
        int appliedFee = (int)(originalFee * (1 - discountRate));
        lineChart.addSeries("현재 할인", List.of(appliedPercent), List.of(appliedFee)).setMarker(SeriesMarkers.DIAMOND).setLineStyle(null);
        JPanel linePanel = new XChartPanel<>(lineChart);
        graphPanel.add(linePanel);

        add(graphPanel, BorderLayout.EAST);
        revalidate();
        repaint();

        // ------------------- 애니메이션 시작 -------------------
        animateBarChart(barChart, originalFee, appliedFee, barPanel);
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



