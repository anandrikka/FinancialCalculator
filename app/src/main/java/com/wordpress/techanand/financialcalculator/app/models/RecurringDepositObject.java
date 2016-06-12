package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 6/11/2016.
 */
public class RecurringDepositObject {

    private double monthlyInvestment, duration, roi, investedAmount, maturityAmount, totalInterest;
    private boolean isMonthly, isByTargetAmount;
    private String durationUnit;

    public double getMonthlyInvestment() {
        return monthlyInvestment;
    }

    public void setMonthlyInvestment(double monthlyInvestment) {
        this.monthlyInvestment = monthlyInvestment;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }

    public double getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public boolean isMonthly() {
        return isMonthly;
    }

    public void setIsMonthly(boolean monthly) {
        isMonthly = monthly;
    }

    public boolean isByTargetAmount() {
        return isByTargetAmount;
    }

    public void setIsByTargetAmount(boolean byTargetAmount) {
        isByTargetAmount = byTargetAmount;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }
}
