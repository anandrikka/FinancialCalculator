package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 6/8/2016.
 */
public class StockObject {
    private double buyPrice, sellPrice, quantity;
    String category, exchange;

    private double brokerage, sttCharges, exchangeTxCharges, serviceCharges, sebiCharges, stampDutyCharges, breakEven, profitOrLoss;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public StockObject() {
    }

    public StockObject(double buyPrice, double sellPrice, double quantity) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(double brokerage) {
        this.brokerage = brokerage;
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

    public double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public double getSebiCharges() {
        return sebiCharges;
    }

    public void setSebiCharges(double sebiCharges) {
        this.sebiCharges = sebiCharges;
    }

    public double getStampDutyCharges() {
        return stampDutyCharges;
    }

    public void setStampDutyCharges(double stampDutyCharges) {
        this.stampDutyCharges = stampDutyCharges;
    }

    public double getBreakEven() {
        return breakEven;
    }

    public void setBreakEven(double breakEven) {
        this.breakEven = breakEven;
    }

    public double getProfitOrLoss() {
        return profitOrLoss;
    }

    public void setProfitOrLoss(double profitOrLoss) {
        this.profitOrLoss = profitOrLoss;
    }
}
