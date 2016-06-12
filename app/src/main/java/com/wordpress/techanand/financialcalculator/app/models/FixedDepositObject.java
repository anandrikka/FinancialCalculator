package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 6/10/2016.
 */
public class FixedDepositObject {
    double fdAmount, time, roi, interest, maturityAmount, payoutAmount;
    String timeUnit, compoundingUnit, payoutUnit;
    boolean isStandardFD, isInterestPayoutFD;

    public boolean isStandardFD() {
        return isStandardFD;
    }

    public void setIsStandardFD(boolean standardFD) {
        isStandardFD = standardFD;
    }

    public boolean isInterestPayoutFD() {
        return isInterestPayoutFD;
    }

    public void setIsInterestPayoutFD(boolean interestPayoutFD) {
        isInterestPayoutFD = interestPayoutFD;
    }

    public double getFdAmount() {
        return fdAmount;
    }

    public void setFdAmount(double fdAmount) {
        this.fdAmount = fdAmount;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public double getPayoutAmount() {
        return payoutAmount;
    }

    public void setPayoutAmount(double monthlyAmount) {
        this.payoutAmount = monthlyAmount;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getCompoundingUnit() {
        return compoundingUnit;
    }

    public void setCompoundingUnit(String compoundingUnit) {
        this.compoundingUnit = compoundingUnit;
    }

    public String getPayoutUnit() {
        return payoutUnit;
    }

    public void setPayoutUnit(String payoutUnit) {
        this.payoutUnit = payoutUnit;
    }
}
