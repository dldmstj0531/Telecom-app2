package org.example.model;

public class Plan {
    private int id;
    private Agency agency;
    private String name;
    private int monthlyFee;
    private int contractMonths; // 약정 개월 수
    private int dataAmount;     // GB
    private int smsAmount;
    private int callMinutes;

    // Getter
    public int getId() {
        return id;
    }

    public Agency getAgency() {
        return agency;
    }

    public String getName() {
        return name;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }

    public int getContractMonths() {
        return contractMonths;
    }

    public int getDataAmount() {
        return this.dataAmount;
    }

    public int getSmsAmount() {
        return smsAmount;
    }

    public int getCallMinutes() {
        return callMinutes;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthlyFee(int monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public void setContractMonths(int contractMonths) {
        this.contractMonths = contractMonths;
    }

    public void setDataAmount(int dataAmount) {
        this.dataAmount = dataAmount;
    }

    public void setSmsAmount(int smsAmount) {
        this.smsAmount = smsAmount;
    }

    public void setCallMinutes(int callMinutes) {
        this.callMinutes = callMinutes;
    }
}
