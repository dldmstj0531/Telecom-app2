package org.example.model;

public class Discount {
    private int id;
    private Plan plan;
    private String name;
    private String discountAmount;
    private String condition; // 할인 적용 조건
    private String extraBenefit;  // ✅ 추가

    // Getter
    public int getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public String getName() {
        return name;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public String getCondition() {
        return condition;
    }
    public String getExtraBenefit() { return extraBenefit; }  // ✅ 추가

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    public void setExtraBenefit(String extraBenefit) { this.extraBenefit = extraBenefit; }  // ✅ 추가
}
