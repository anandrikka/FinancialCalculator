package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 6/19/2016.
 */
public class InflationObject {

    private double presentValue, inflationRate, numberOfYears, futureValue;

    public double getPresentValue() {
        return presentValue;
    }

    public void setPresentValue(double presentValue) {
        this.presentValue = presentValue;
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(double inflationRate) {
        this.inflationRate = inflationRate;
    }

    public double getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(double numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public double getFutureValue() {
        return futureValue;
    }

    public void setFutureValue(double futureValue) {
        this.futureValue = futureValue;
    }
}
