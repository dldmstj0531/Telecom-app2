package org.example.utils;

import org.example.dao.*;
import org.example.logic.*;
import org.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoadingPanel extends JPanel {
    private InputUI parentFrame;

    public LoadingPanel(InputUI parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // ✅ 사용자 입력 기반으로 Customer 객체 생성
        Customer customer = parentFrame.customer;


        // 입력값 검증 로그 (출력은 유지 가능)
        System.out.println("📦 전달된 입력값 확인:");
        System.out.println(" - 전화번호: " + customer.getPhoneNumber());
        System.out.println(" - 나이: " + customer.getAge());
        System.out.println(" - 현재 통신사: " + customer.getCurrentAgency().getId());
        System.out.println(" - 이용 개월 수: " + customer.getMonthsUsingPlan());
        System.out.println(" - 요금제 금액: " + customer.getCurrentPlan().getMonthlyFee());
        System.out.println(" - 이동 통신사: " + customer.getDesiredAgency().getId());
        System.out.println(" - 희망 가격: " + customer.getDesiredPrice());
        System.out.println(" - 복지혜택: 장애인=" + customer.isDisabled() + ", 생계=" + customer.isLivingSupport()
                + ", 주거=" + customer.isHousingSupport() + ", 기초연금=" + customer.isPensioner());

        // ✅ 입력값 누락 검증 (중복 체크)
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank() ||
                customer.getAge() == 0 || customer.getDesiredPrice() == 0 ||
                customer.getMonthsUsingPlan() == 0 ||
                customer.getCurrentAgency() == null || customer.getCurrentAgency().getId() == 0 ||
                customer.getDesiredAgency() == null || customer.getDesiredAgency().getId() == 0) {

            System.err.println("❌ [LoadingPanel] 고객 정보 누락. 저장 중단됨.");
            return;
        }


        // ✅ 백엔드 로직 실행
        PlanDAO planDAO = new PlanDAO();
        DiscountDAO discountDAO = new DiscountDAO();

        Recommendation rec = new Recommendation(planDAO);
        PenaltyCalculator penaltyCalc = new PenaltyCalculator();

        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.create(customer);  // 드디어 DB에도 정상 저장

        //List<Plan> recommended = rec.recommendPlans(customer);
        int penalty = penaltyCalc.calculatePenalty(customer);
        List<Discount> benefits = BenefitChecker.checkBenefits(customer);

        // ✅ 결과를 parentFrame에 반영
        parentFrame.customer = customer;
        parentFrame.calculatedPenalty = penalty;
        //parentFrame.recommendedPlans = recommended;
        parentFrame.benefitList = benefits;
        parentFrame.showComparePanel();
        parentFrame.currentDataAmount = customer.getDataAmount();

        // ✅ 로딩화면 구성 (기존 동일)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("입력된 정보를 확인 중입니다...");
        title.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
        centerPanel.add(title);

        JLabel info = new JLabel("통신사 변경 시 예상 위약금과 맞춤 요금제 및 적용 가능한 혜택을 계산 중입니다.");
        info.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(info);

        JLabel wait = new JLabel("잠시만 기다려 주세요!");
        wait.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        centerPanel.add(wait);

        int imageWidth = 600;
        int imageHeight = 360;

        try {
            // ✅ 경로 안정화: getResource 이용 (classpath 기준)
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/org/example/utils/bird1.jpg"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(resizedIcon);
            imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            centerPanel.add(imageLabel);
        } catch (Exception e) {
            System.err.println("❌ 이미지 로드 실패: " + e.getMessage());
        }


        JLabel birdInfo = new JLabel("지금 화면에 보이는 새는 'Red Knot'로, 장거리 비행을 견디는 강인한 철새입니다.");
        birdInfo.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        birdInfo.setForeground(Color.DARK_GRAY);
        centerPanel.add(birdInfo);

        JLabel comment = new JLabel("저희 프로그램도 지금 열심히 계산 중이에요!");
        comment.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        comment.setForeground(Color.DARK_GRAY);
        centerPanel.add(comment);

        add(centerPanel, BorderLayout.CENTER);

        // ✅ 약간의 로딩 타임 부여
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showPanel("notice");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}