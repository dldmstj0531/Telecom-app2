package org.example.utils;

import org.example.dao.PlanDAO;
import org.example.model.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ComparePanel extends JPanel {
    private InputUI parentFrame;
    private JComboBox<String> planComboBox;
    private JPanel chartPanel;

    private Plan currentPlan;
    private List<Plan> allPlans;

    public ComparePanel(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 데이터 로딩
        PlanDAO planDAO = new PlanDAO();
        int moveAgencyId = parentFrame.customer.getDesiredAgency().getId();
        allPlans = planDAO.getPlansByAgencyId(moveAgencyId);

        int planFee = parentFrame.customer.getCurrentPlan().getMonthlyFee();
        int dataAmount = parentFrame.customer.getCurrentPlan().getDataAmount();
        int callMinutes = parentFrame.customer.getCurrentPlan().getCallMinutes();  // 이건 callMinutes 있으면

        currentPlan = new Plan();
        currentPlan.setMonthlyFee(planFee);
        currentPlan.setCallMinutes(callMinutes);

        System.out.println("👉 parentFrame.getCurrentDataAmount() = " + parentFrame.getCurrentDataAmount());
        currentPlan.setDataAmount(dataAmount);
        System.out.println("👉 currentPlan.getDataAmount() after set = " + currentPlan.getDataAmount());

        // 중앙 컨텐츠 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        centerPanel.setBackground(Color.WHITE);

        JLabel mainTitle = new JLabel("추천 요금제를 현재 요금제와 비교해드립니다.");
        mainTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
        centerPanel.add(mainTitle);
        centerPanel.add(Box.createVerticalStrut(15));

        JLabel subTitle = new JLabel("현재 요금제와 비교하고자 하는 요금제를 선택해주세요.");
        subTitle.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        centerPanel.add(subTitle);
        centerPanel.add(Box.createVerticalStrut(15));

        // 콤보박스
        planComboBox = new JComboBox<>();
        for (Plan plan : allPlans) {
            planComboBox.addItem(plan.getName());
        }
        planComboBox.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        centerPanel.add(planComboBox);
        centerPanel.add(Box.createVerticalStrut(30));
        planComboBox.setBackground(Color.WHITE);

        // 차트 패널 설정
        chartPanel = new JPanel();
        chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.X_AXIS));
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        chartPanel.setMaximumSize(new Dimension(600, 300));
        centerPanel.add(chartPanel);

        add(centerPanel, BorderLayout.CENTER);

        // 하단 버튼
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);
        JButton backBtn = new JButton("뒤로 가기");
        backBtn.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        backBtn.setBackground(Color.LIGHT_GRAY);
        backBtn.setForeground(Color.BLACK);
        leftPanel.add(backBtn);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        // 이벤트 처리
        planComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedName = (String) planComboBox.getSelectedItem();
                Plan selectedPlan = findPlanByName(selectedName);
                if (selectedPlan != null) {
                    updateChart(currentPlan, selectedPlan);
                }
            }
        });

        backBtn.addActionListener(e -> parentFrame.showPanel("recommend1"));

        if (!allPlans.isEmpty()) {
            planComboBox.setSelectedIndex(0);
            Plan selectedPlan = findPlanByName((String) planComboBox.getSelectedItem());

            // updateChart 호출 바로 직전에 삽입
            System.out.println("📦 updateChart 직전 currentPlan.getDataAmount() = " + currentPlan.getDataAmount());

            if (selectedPlan != null) {
                updateChart(currentPlan, selectedPlan);
            }
        }

        System.out.println("📦 이동 통신사 ID: " + moveAgencyId);
        System.out.println("📦 불러온 요금제 수: " + allPlans.size());
        for (Plan p : allPlans) {
            System.out.println(" - 요금제 이름: " + p.getName());
        }
    }

    private Plan findPlanByName(String name) {
        if (name == null) return null;
        for (Plan plan : allPlans) {
            if (name.equals(plan.getName())) return plan;
        }
        return null;
    }

    private void updateChart(Plan current, Plan selected) {
        chartPanel.removeAll();

        JPanel priceBar = createAnimatedBar("가격", current.getMonthlyFee(), selected.getMonthlyFee(), false);
        JPanel dataBar = createAnimatedBar("데이터 제공량", current.getDataAmount(), selected.getDataAmount(), true);

        priceBar.setAlignmentY(Component.TOP_ALIGNMENT);
        dataBar.setAlignmentY(Component.TOP_ALIGNMENT);

        chartPanel.add(Box.createHorizontalGlue());
        chartPanel.add(priceBar);
        chartPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        chartPanel.add(dataBar);
        chartPanel.add(Box.createHorizontalGlue());

        chartPanel.revalidate();
        chartPanel.repaint();

        System.out.println("✅ 차트 업데이트됨");
        System.out.println("현재 요금제: " + current.getMonthlyFee() + ", " + current.getDataAmount());
        System.out.println("선택 요금제: " + selected.getMonthlyFee() + ", " + selected.getDataAmount());
        System.out.println("📊 updateChart 확인: current = " + current + ", selected = " + selected);
    }
    // ✅ 막대 그래프 애니메이션을 적용한 패널 생성
    private JPanel createAnimatedBar(String title, int currentValue, int selectedValue, boolean isDataBar) {
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(Color.WHITE);

        BarPanel barPanel = new BarPanel(currentValue, selectedValue, isDataBar);
        barPanel.setPreferredSize(new Dimension(200, 250));
        barPanel.setBackground(Color.WHITE);

        outerPanel.add(barPanel, BorderLayout.CENTER);
        outerPanel.add(new JLabel(title, SwingConstants.CENTER), BorderLayout.SOUTH);

        barPanel.startAnimation();  // 애니메이션 시작

        return outerPanel;
    }

    // ✅ 내부 클래스: 애니메이션 바패널
    class BarPanel extends JPanel {
        int currentValue, selectedValue;
        int currentHeight = 0, selectedHeight = 0;
        boolean isDataBar;

        public BarPanel(int currentValue, int selectedValue, boolean isDataBar) {
            this.currentValue = currentValue;
            this.selectedValue = selectedValue;
            this.isDataBar = isDataBar;
        }

        public void startAnimation() {
            int visualUnlimited = 200;
            final int adjustedCurrent = (currentValue < 0) ? visualUnlimited : currentValue;
            final int adjustedSelected = (selectedValue < 0) ? visualUnlimited : selectedValue;
            final int max = Math.max(Math.max(adjustedCurrent, adjustedSelected),1);

            Timer timer = new Timer(15, null);
            final int[] step = {0};
            int steps = 50;

            timer.addActionListener(e -> {
                step[0]++;
                double ratio = step[0] / (double) steps;
                currentHeight = (int) ((getHeight() - 50) * ((adjustedCurrent * ratio) / max));
                selectedHeight = (int) ((getHeight() - 50) * ((adjustedSelected * ratio) / max));
                repaint();
                if (step[0] >= steps) timer.stop();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int barWidth = width / 3;

            g.setColor(new Color(242, 164, 140));
            g.fillRect(width / 4 - barWidth / 2, height - currentHeight - 30, barWidth, currentHeight);

            g.setColor(new Color(230, 74, 25));
            g.fillRect(width * 3 / 4 - barWidth / 2, height - selectedHeight - 30, barWidth, selectedHeight);

            g.setColor(Color.BLACK);

            String currentText = isDataBar ? (currentValue < 0 ? "무제한" : currentValue + "GB") : currentValue + "원";
            String selectedText = isDataBar ? (selectedValue < 0 ? "무제한" : selectedValue + "GB") : selectedValue + "원";

            g.drawString(currentText, width / 4 - 20, height - currentHeight - 35);
            g.drawString(selectedText, width * 3 / 4 - 20, height - selectedHeight - 35);

            g.drawString("현재 요금제", width / 4 - 30, height - 10);
            g.drawString("선택 요금제", width * 3 / 4 - 30, height - 10);
        }
    }
}