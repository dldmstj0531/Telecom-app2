package org.example.logic;

import org.example.model.Customer;
import org.example.model.Plan;

public class PenaltyCalculator {

    public static int calculatePenalty(Customer customer) {
        Plan plan = customer.getCurrentPlan();
        int remainingMonths = plan.getContractMonths() - customer.getMonthsUsingPlan();
        int originalMonthlyFee = plan.getMonthlyFee();

        double discountRate = BenefitChecker.calculateTotalDiscount(customer);
        int discountedFee = (int)(originalMonthlyFee * (1 - discountRate));

        return remainingMonths * discountedFee;
    }
}
