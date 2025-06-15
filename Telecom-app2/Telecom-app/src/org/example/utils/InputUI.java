package org.example.utils;

import org.example.model.Customer;
import org.example.model.Discount;
import org.example.model.Plan;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputUI extends JFrame {

    public Customer customer;
    public int calculatedPenalty;
    //public List<Plan> recommendedPlans;
    public List<Discount> benefitList;

    public JPanel cardPanel; // 외부에서 접근 가능하도록 public
    public int currentDataAmount;
    public int currentCallMinutes;
    private CardLayout cardLayout;
    protected Map<String, JPanel> panels = new HashMap<>();
    private ComparePanel comparePanel;

    // ✅ 음악 thread 추가
    private MusicPlayer musicPlayer;

    public InputUI() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        // ✅ MusicPlayer 인스턴스 생성 (아직 start 안함)
        musicPlayer = new MusicPlayer();

        // ✅ 동일 인스턴스로 등록
        InputUIPanelStep1 step1Panel = new InputUIPanelStep1(this);
        InputUIPanelStep2 step2Panel = new InputUIPanelStep2(this);
        InputUIPanelStep3 step3Panel = new InputUIPanelStep3(this);
        //MyBenefitPanel myBenefitPanel = new MyBenefitPanel(this);

        // ✅ 패널 등록 (LoadingPanel은 나중에 생성)
        panels.put("FirstPanel", new FirstPanel());
        panels.put("step1", step1Panel);
        panels.put("step2", step2Panel);
        panels.put("step3", step3Panel);
        panels.put("Loading", null); // 나중에 동적 생성
        panels.put("result", new ResultPanel(this));
        //panels.put("mybenefit", myBenefitPanel);
        panels.put("change1", new ChangePanel1(this));
        panels.put("change2", new ChangePanel2(this));
        panels.put("change3", new ChangePanel3(this));
        panels.put("notice", new NoticePanel(this));
        panels.put("finish", new FinishPanel(this));
        //panels.put("compare", new ComparePanel(this));

        // ✅ 카드 레이아웃에 추가 (null 패널은 제외)
        for (Map.Entry<String, JPanel> entry : panels.entrySet()) {
            if (entry.getValue() != null) {
                cardPanel.add(entry.getValue(), entry.getKey());
            }
        }

        add(cardPanel);

        // ✅ 첫 시작 패널 명시
        showPanel("step1");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);

        if (name.equals("notice")) {
            ((NoticePanel) panels.get("notice")).onShow();
        }
    }

    public JPanel getPanel(String name) {
        return panels.get(name);
    }

    // ✅ LoadingPanel을 나중에 생성해서 카드에 추가
    public void addAndShowLoadingPanel() {
        LoadingPanel loadingPanel = new LoadingPanel(this);
        panels.put("Loading", loadingPanel);
        cardPanel.add(loadingPanel, "Loading");
        showPanel("Loading");
    }

    public int getCurrentDataAmount() {
        System.out.println("📤 getCurrentDataAmount() 호출됨: " + currentDataAmount);
        return currentDataAmount;
    }

    public int getCurrentCallMinutes() {
        return currentCallMinutes;
    }


    public void showComparePanel() {
        this.comparePanel = new ComparePanel(this);
        cardPanel.add(comparePanel, "compare");
        cardLayout.show(cardPanel, "compare");
    }

    public void setCurrentDataAmount(int i) {
        this.currentDataAmount = i;
        System.out.println("📌 currentDataAmount 설정됨: " + i);
    }

    public void setCurrentCallMinutes(int i) {
        this.currentCallMinutes = i;
    }

    public void showBenefitPanel() {
        MyBenefitPanel benefitPanel = new MyBenefitPanel(this);
        benefitPanel.displayBenefits();
        panels.put("mybenefit", benefitPanel);
        cardPanel.add(benefitPanel, "mybenefit");
        showPanel("mybenefit");
    }

    // ✅ MusicPlayer 제어 메소드 추가 (패널에서 접근용)
    public void startMusic() {
        musicPlayer.start();
    }

    public void stopMusic() {
        musicPlayer.stopMusic();
    }
}