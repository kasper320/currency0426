package com.sdaacademy.kasperek.andrzej.urlreader;

/**
 * Created by RENT on 2017-04-26.
 */

public class Currency {
    private String baseCurrency;
    private String targetCurrency;
    private double valueOfCurrency;


    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getValueOfCurrency() {
        return valueOfCurrency;
    }

    public void setValueOfCurrency(double valueOfCurrency) {
        this.valueOfCurrency = valueOfCurrency;
    }

    @Override
    public String toString() {
        return baseCurrency + " to " + targetCurrency + " = " + valueOfCurrency;
    }
}
