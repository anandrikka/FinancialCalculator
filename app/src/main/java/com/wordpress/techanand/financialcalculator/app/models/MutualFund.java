package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 6/9/2016.
 */
public class MutualFund {

    private double amount, totalPeriod, annualReturns, totalReturns, totalInvestment, wealthGained, monthlySIP;
    boolean isMonthlySIP, isTargetAmount;
    String periodUnit;

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }

    public double getMonthlySIP() {
        return monthlySIP;
    }

    public void setMonthlySIP(double monthlySIP) {
        this.monthlySIP = monthlySIP;
    }

    public boolean isTargetAmount() {
        return isTargetAmount;
    }

    public void setIsTargetAmount(boolean targetAmount) {
        isTargetAmount = targetAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(double totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public double getAnnualReturns() {
        return annualReturns;
    }

    public void setAnnualReturns(double annualReturns) {
        this.annualReturns = annualReturns;
    }

    public double getTotalReturns() {
        return totalReturns;
    }

    public void setTotalReturns(double totalReturns) {
        this.totalReturns = totalReturns;
    }

    public double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public double getWealthGained() {
        return wealthGained;
    }

    public void setWealthGained(double wealthGained) {
        this.wealthGained = wealthGained;
    }

    public boolean isMonthlySIP() {
        return isMonthlySIP;
    }

    public void setIsMonthlySIP(boolean monthlySIP) {
        isMonthlySIP = monthlySIP;
    }
}
