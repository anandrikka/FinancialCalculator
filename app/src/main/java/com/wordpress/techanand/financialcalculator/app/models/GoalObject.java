package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 6/12/2016.
 */
public class GoalObject {

    private double todayValue, savedAmount, duration, inflationPercent, returnsPercent, totalInvestment, wealthCreated, totalReturns;

    public double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public double getWealthCreated() {
        return wealthCreated;
    }

    public void setWealthCreated(double wealthCreated) {
        this.wealthCreated = wealthCreated;
    }

    public double getTotalReturns() {
        return totalReturns;
    }

    public void setTotalReturns(double totalReturns) {
        this.totalReturns = totalReturns;
    }

    public double getTodayValue() {
        return todayValue;
    }

    public void setTodayValue(double todayValue) {
        this.todayValue = todayValue;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getInflationPercent() {
        return inflationPercent;
    }

    public void setInflationPercent(double inflationPercent) {
        this.inflationPercent = inflationPercent;
    }

    public double getReturnsPercent() {
        return returnsPercent;
    }

    public void setReturnsPercent(double returnsPercent) {
        this.returnsPercent = returnsPercent;
    }
}
