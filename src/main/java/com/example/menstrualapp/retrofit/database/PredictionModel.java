package com.example.menstrualapp.retrofit.database;

public class PredictionModel {
    private float nextPeriodDays;
    private String PeriodDate;
    private int nextperiodLength;
    private int nextPeriodindays;
    private int PeriodCycle;

    public PredictionModel(float nextPeriodDays, String periodDate, int nextperiodLength, int nextPeriodindays, int periodCycle) {
        this.nextPeriodDays = nextPeriodDays;
        PeriodDate = periodDate;
        this.nextperiodLength = nextperiodLength;
        this.nextPeriodindays = nextPeriodindays;
        PeriodCycle = periodCycle;
    }

    public float getNextPeriodDays() {
        return nextPeriodDays;
    }

    public void setNextPeriodDays(float nextPeriodDays) {
        this.nextPeriodDays = nextPeriodDays;
    }

    public String getPeriodDate() {
        return PeriodDate;
    }

    public void setPeriodDate(String periodDate) {
        PeriodDate = periodDate;
    }

    public int getNextperiodLength() {
        return nextperiodLength;
    }

    public void setNextperiodLength(int nextperiodLength) {
        this.nextperiodLength = nextperiodLength;
    }

    public int getNextPeriodindays() {
        return nextPeriodindays;
    }

    public void setNextPeriodindays(int nextPeriodindays) {
        this.nextPeriodindays = nextPeriodindays;
    }

    public int getPeriodCycle() {
        return PeriodCycle;
    }

    public void setPeriodCycle(int periodCycle) {
        PeriodCycle = periodCycle;
    }

    @Override
    public String toString() {
        return "PredictionModel{" +
                "nextPeriodDays=" + nextPeriodDays +
                ", PeriodDate='" + PeriodDate + '\'' +
                ", nextperiodLength=" + nextperiodLength +
                ", nextPeriodindays=" + nextPeriodindays +
                ", PeriodCycle=" + PeriodCycle +
                '}';
    }
}
