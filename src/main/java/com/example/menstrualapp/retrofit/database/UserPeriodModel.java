package com.example.menstrualapp.retrofit.database;

public class UserPeriodModel {
    private int id;
    private int userid;
    private int periodCycle;
    private int periodLength;
    private String periodDate;
    private String periodStress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getPeriodCycle() {
        return periodCycle;
    }

    public void setPeriodCycle(int periodCycle) {
        this.periodCycle = periodCycle;
    }

    public int getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(int periodLength) {
        this.periodLength = periodLength;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }

    public String getPeriodStress() {
        return periodStress;
    }

    public void setPeriodStress(String periodStress) {
        this.periodStress = periodStress;
    }

    public UserPeriodModel(int userid, int periodCycle, int periodLength, String periodDate, String periodStress) {
        this.userid = userid;
        this.periodCycle = periodCycle;
        this.periodLength = periodLength;
        this.periodDate = periodDate;
        this.periodStress = periodStress;
    }

    public UserPeriodModel() {
    }

    @Override
    public String toString() {
        return "UserPeriodModel{" +
                "id=" + id +
                ", userid=" + userid +
                ", periodCycle=" + periodCycle +
                ", periodLength=" + periodLength +
                ", periodDate=" + periodDate +
                ", periodStress=" + periodStress +
                '}';
    }
}
