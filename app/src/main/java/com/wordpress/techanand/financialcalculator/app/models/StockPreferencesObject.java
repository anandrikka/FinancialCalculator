package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 6/8/2016.
 */
public class StockPreferencesObject {
    private boolean isFlatRateUsed;
    private double flatBrokerage, brokeragePercent, maxBrokerage, serviceTax, sebiCharges,
            sttCharges, exchangeTxCharges, stampDutyMinimum, stampDutyMaximum, stampDutyPercentage;

    public boolean isFlatRateUsed() {
        return isFlatRateUsed;
    }

    public void setFlatRateUsed(boolean flatRateUsed) {
        isFlatRateUsed = flatRateUsed;
    }

    public double getFlatBrokerage() {
        return flatBrokerage;
    }

    public void setFlatBrokerage(double flatBrokerage) {
        this.flatBrokerage = flatBrokerage;
    }

    public double getBrokeragePercent() {
        return brokeragePercent;
    }

    public void setBrokeragePercent(double brokeragePercent) {
        this.brokeragePercent = brokeragePercent;
    }

    public double getMaxBrokerage() {
        return maxBrokerage;
    }

    public void setMaxBrokerage(double maxBrokerage) {
        this.maxBrokerage = maxBrokerage;
    }

    public double getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(double serviceTax) {
        this.serviceTax = serviceTax;
    }

    public double getSebiCharges() {
        return sebiCharges;
    }

    public void setSebiCharges(double sebiCharges) {
        this.sebiCharges = sebiCharges;
    }

    public double getSttCharges() {
        return sttCharges;
    }

    public void setSttCharges(double sttCharges) {
        this.sttCharges = sttCharges;
    }

    public double getExchangeTxCharges() {
        return exchangeTxCharges;
    }

    public void setExchangeTxCharges(double exchangeTxCharges) {
        this.exchangeTxCharges = exchangeTxCharges;
    }

    public double getStampDutyMinimum() {
        return stampDutyMinimum;
    }

    public void setStampDutyMinimum(double stampDutyMinimum) {
        this.stampDutyMinimum = stampDutyMinimum;
    }

    public double getStampDutyMaximum() {
        return stampDutyMaximum;
    }

    public void setStampDutyMaximum(double stampDutyMaximum) {
        this.stampDutyMaximum = stampDutyMaximum;
    }

    public double getStampDutyPercentage() {
        return stampDutyPercentage;
    }

    public void setStampDutyPercentage(double stampDutyPercentage) {
        this.stampDutyPercentage = stampDutyPercentage;
    }
}
