package org.example.model;

public class Customer {
    private int id;

    private String phoneNumber;
    private int age;
    private Agency currentAgency;
    private Plan currentPlan;
    private int monthsUsingPlan;
    private int desiredPrice;
    private Agency desiredAgency;
    private int dataAmount;

    // 복지 입력 기능 추가
    public boolean isDisabled;
    public boolean isLivingSupport;
    public boolean isHousingSupport;
    public boolean isPensioner;

    // Getter
    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public Agency getCurrentAgency() {
        return currentAgency;
    }

    public Plan getCurrentPlan() {
        return currentPlan;
    }

    public int getMonthsUsingPlan() {
        return monthsUsingPlan;
    }

    public int getDesiredPrice() {
        return desiredPrice;
    }

    public Agency getDesiredAgency() {
        return desiredAgency;
    }

    public int getDataAmount() {
        return dataAmount;
    }

    // 복지 입력 기능 추가
    public boolean isDisabled() {return isDisabled;}

    public boolean isLivingSupport() {return isLivingSupport;}

    public boolean isHousingSupport() {return isHousingSupport;}

    public boolean isPensioner() {return isPensioner;}

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCurrentAgency(Agency currentAgency) {
        this.currentAgency = currentAgency;
    }

    public void setCurrentPlan(Plan currentPlan) {
        this.currentPlan = currentPlan;
    }

    public void setMonthsUsingPlan(int monthsUsingPlan) {
        this.monthsUsingPlan = monthsUsingPlan;
    }

    public void setDesiredPrice(int desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public void setDesiredAgency(Agency desiredAgency) {
        this.desiredAgency = desiredAgency;
    }

    public void setDataAmount(int dataAmount) {
        this.dataAmount = dataAmount;
    }

    // 복지 입력 기능 추가
    public void setDisabled(boolean disabled) {this.isDisabled = disabled;}

    public void setLivingSupport(boolean livingSupport) {this.isLivingSupport = livingSupport;}

    public void setHousingSupport(boolean housingSupport) {this.isHousingSupport = housingSupport;}

    public void setPensioner(boolean pensioner) {this.isPensioner = pensioner;}
}

