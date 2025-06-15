package org.example.logic;

import org.example.dao.PlanDAO;
import org.example.model.Customer;
import org.example.model.Plan;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {
    private PlanDAO planDAO;

    public Recommendation(PlanDAO planDAO) {
        this.planDAO = planDAO;
    }

    // 기준을 인자로 받는 메서드로 수정
    public List<Plan> recommendPlans(Customer customer, String 기준) {
        List<Plan> allPlans = planDAO.getAllPlans();
        List<Plan> recommendedPlans = new ArrayList<>();

        int desiredAgencyId = customer.getDesiredAgency().getId();
        int desiredPrice = customer.getDesiredPrice();
        int currentDataUsage = customer.getCurrentPlan().getDataAmount();
        int currentCallMinutes = customer.getCurrentPlan().getCallMinutes();

        for (Plan plan : allPlans) {
            boolean matchesAgency = plan.getAgency().getId() == desiredAgencyId;

            if (기준.equals("price")) {
                boolean withinPrice = plan.getMonthlyFee() <= desiredPrice;
                if (matchesAgency && withinPrice) {
                    recommendedPlans.add(plan);
                }
            } else if (기준.equals("data_call")) {
                boolean betterData = plan.getDataAmount() >= currentDataUsage;
                boolean betterCalls = plan.getCallMinutes() >= currentCallMinutes;
                if (matchesAgency && (betterData || betterCalls)) {
                    recommendedPlans.add(plan);
                }
            }
        }
        return recommendedPlans;
    }
}
