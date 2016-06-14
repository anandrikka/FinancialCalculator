package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 6/12/2016.
 */
public class RetirementObject {

    private double currentAge;
    private double retirementAge;
    private double lifeExpectancy;
    private double monthlyExpenses;
    private double inflation;
    private double existingInvestment;
    private double expectedReturns;
    private double workYearsLeft;

    public double getYearsToSurvive() {
        return (lifeExpectancy-retirementAge);
    }

    public void setYearsToSurvive(double yearsToSurvive) {
        this.yearsToSurvive = yearsToSurvive;
    }

    public double getWorkYearsLeft() {
        return (retirementAge-currentAge);
    }

    public void setWorkYearsLeft(double workYearsLeft) {
        this.workYearsLeft = workYearsLeft;
    }

    private double yearsToSurvive;

    public double getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(double currentAge) {
        this.currentAge = currentAge;
    }

    public double getRetirementAge() {
        return retirementAge;
    }

    public void setRetirementAge(double retirementAge) {
        this.retirementAge = retirementAge;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(double monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public double getInflation() {
        return inflation;
    }

    public void setInflation(double inflation) {
        this.inflation = inflation;
    }

    public double getExistingInvestment() {
        return existingInvestment;
    }

    public void setExistingInvestment(double existingInvestment) {
        this.existingInvestment = existingInvestment;
    }

    public double getExpectedReturns() {
        return expectedReturns;
    }

    public void setExpectedReturns(double expectedReturns) {
        this.expectedReturns = expectedReturns;
    }
}
