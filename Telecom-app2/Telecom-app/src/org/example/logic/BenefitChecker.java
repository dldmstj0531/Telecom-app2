package org.example.logic;

import org.example.dao.DiscountDAO;
import org.example.model.Customer;
import org.example.model.Discount;

import java.util.ArrayList;
import java.util.List;

public class BenefitChecker {

    public static List<Discount> checkBenefits(Customer customer) {
        List<Discount> applicableDiscounts = new ArrayList<>();
        List<Discount> discountList = DiscountDAO.getAllDiscounts();

        for (Discount discount : discountList) {
            String condition = discount.getCondition();  // 이제 DB에서 바로 가져온 값

            if (condition.equals("isDisabled") && customer.isDisabled()) {
                applicableDiscounts.add(discount);
            }
            if (condition.equals("isLivingSupport") && customer.isLivingSupport()) {
                applicableDiscounts.add(discount);
            }
            if (condition.equals("isHousingSupport") && customer.isHousingSupport()) {
                applicableDiscounts.add(discount);
            }
            if (condition.equals("isPensioner") && customer.isPensioner()) {
                applicableDiscounts.add(discount);
            }
        }
        return applicableDiscounts;
    }

    public static double calculateTotalDiscount(Customer customer) {
        double maxDiscount = 0.0;
        List<Discount> discountList = DiscountDAO.getAllDiscounts();

        for (Discount discount : discountList) {
            String condition = discount.getCondition();
            boolean conditionMet = false;

            if (condition.equals("isDisabled") && customer.isDisabled()) conditionMet = true;
            if (condition.equals("isLivingSupport") && customer.isLivingSupport()) conditionMet = true;
            if (condition.equals("isHousingSupport") && customer.isHousingSupport()) conditionMet = true;
            if (condition.equals("isPensioner") && customer.isPensioner()) conditionMet = true;

            if (conditionMet) {
                String discountText = discount.getDiscountAmount().replaceAll("[^0-9]", "");
                double rate = Double.parseDouble(discountText) / 100.0;
                if (rate > maxDiscount) {
                    maxDiscount = rate;  // 👉 최대 할인으로 계속 갱신
                }
            }
        }
        return maxDiscount;
    }

    // ✅ 부가혜택 추출
    public static List<String> getExtraBenefits(Customer customer) {
        List<String> extraList = new ArrayList<>();
        List<Discount> discountList = checkBenefits(customer);

        for (Discount discount : discountList) {
            if (discount.getExtraBenefit() != null && !discount.getExtraBenefit().isBlank()) {
                extraList.add(discount.getExtraBenefit());
            }
        }
        return extraList;
}}
