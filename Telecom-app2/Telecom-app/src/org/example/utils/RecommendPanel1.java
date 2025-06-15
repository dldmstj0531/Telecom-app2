package org.example.utils;

import org.example.dao.PlanDAO;
import org.example.logic.Recommendation;
import org.example.model.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RecommendPanel1 extends JPanel {
    private InputUI parentFrame;

    public RecommendPanel1(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 전체 중앙 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        centerPanel.setBackground(Color.WHITE);

        // 제목
        JLabel label1 = makeLabel("입력하신 정보를 기반으로 추천 요금제를 안내드립니다.", 24, true);
        centerPanel.add(label1);
        centerPanel.add(makeSpacer(10));

        // 설명
        JLabel label2 = makeLabel("아래 요금제는 통신사, 약정 조건, 요금 정보를 바탕으로 추천된 맞춤 결과입니다.", 14, false);
        centerPanel.add(label2);
        centerPanel.add(makeSpacer(20));

        // 필터 버튼 패널
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);

        JButton filter1 = new JButton("가격 대비 제공 데이터 및 통화량 기준");
        filter1.setBackground(new Color(255, 102, 51));
        filter1.setForeground(Color.WHITE);
        filter1.setFocusPainted(false);

        JButton filter2 = new JButton("요금제 가격 기준");
        filter2.setBackground(new Color(200, 200, 200));
        filter2.setForeground(Color.BLACK);
        filter2.setFocusPainted(false);
        filter2.addActionListener(e -> {
            RecommendPanel2 panel = new RecommendPanel2(parentFrame);
            parentFrame.cardPanel.add(panel, "recommend2");
            parentFrame.panels.put("recommend2", panel);
            parentFrame.showPanel("recommend2");
        });

        filterPanel.add(filter1);
        filterPanel.add(filter2);
        centerPanel.add(filterPanel);
        centerPanel.add(makeSpacer(20));

        // ✅ 추천 결과 불러오기 (data_call 기준)
        Recommendation recommendation = new Recommendation(new PlanDAO());
        List<Plan> plans = recommendation.recommendPlans(parentFrame.customer, "data_call");

        JPanel planListPanel = buildPlanList(plans);
        JScrollPane scrollPane = new JScrollPane(planListPanel);
        scrollPane.setPreferredSize(new Dimension(640, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerPanel.add(scrollPane);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backBtn = new JButton("뒤로 가기");
        backBtn.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        backBtn.setBackground(Color.LIGHT_GRAY);
        backBtn.setForeground(Color.BLACK);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showPanel("result");
            }
        });
        leftPanel.add(backBtn);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nextBtn = new JButton("현재 요금제와 비교하기");
        nextBtn.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        nextBtn.setBackground(Color.LIGHT_GRAY);
        nextBtn.setForeground(Color.BLACK);
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setCurrentDataAmount(-1);
                parentFrame.setCurrentCallMinutes(200);

                parentFrame.showComparePanel();
            }
        });

        rightPanel.add(nextBtn);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);  // ✅ 하단 버튼 부착
    }

    private JPanel buildPlanList(List<Plan> plans) {
        JPanel planListPanel = new JPanel();
        planListPanel.setLayout(new BoxLayout(planListPanel, BoxLayout.Y_AXIS));
        planListPanel.setBackground(Color.WHITE);

        if (plans == null || plans.isEmpty()) {
            JLabel noResult = makeLabel("추천 가능한 요금제가 없습니다.", 14, true);
            noResult.setForeground(Color.RED);
            planListPanel.add(noResult);
        } else {
            int index = 1;
            for (Plan p : plans) {
                String text = String.format("%s (%s원)", p.getName(), String.format("%,d", p.getMonthlyFee()));
                JPanel planRow = makePlanRow(index++, text);
                planListPanel.add(planRow);
                planListPanel.add(makeSpacer(5));
            }
        }
        return planListPanel;
    }

    private JLabel makeLabel(String text, int size, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Malgun Gothic", bold ? Font.BOLD : Font.PLAIN, size));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private Component makeSpacer(int height) {
        return Box.createRigidArea(new Dimension(0, height));
    }

    private JPanel makePlanRow(int index, String text) {
        JPanel row = new JPanel(new BorderLayout());
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        row.setBackground(new Color(255, 245, 240));
        row.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 180)));

        JLabel numLabel = new JLabel(" " + index + " ");
        numLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        numLabel.setForeground(Color.RED);
        numLabel.setPreferredSize(new Dimension(30, 30));
        numLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel descLabel = new JLabel(text);
        descLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));

        row.add(numLabel, BorderLayout.WEST);
        row.add(descLabel, BorderLayout.CENTER);

        return row;
    }
}
