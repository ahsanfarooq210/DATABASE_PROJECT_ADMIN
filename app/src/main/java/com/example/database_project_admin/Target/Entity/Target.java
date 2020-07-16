package com.example.database_project_admin.Target.Entity;

import com.example.database_project_admin.Entity.Sku;


public class Target {
   String TARGET_ID;
   Sku SKU;
   int TARGET;
   String salesmenEmail;
   String startDateString ,endDateString;
   String SKU_ID;

    public Target() {
    }

    public Target(String TARGET_ID, Sku SKU,String SKU_ID,String salesmenEmail, int TARGET, String startDateString ,String endDateString) {
        this.TARGET_ID = TARGET_ID;
        this.SKU = SKU;
        this.TARGET = TARGET;
        this.SKU_ID=SKU_ID;
        this.salesmenEmail=salesmenEmail;
        this.startDateString = startDateString ;
        this.endDateString =endDateString;

    }

    public String getSalesmenEmail() {
        return salesmenEmail;
    }

    public void setSalesmenEmail(String salesmenEmail) {
        this.salesmenEmail = salesmenEmail;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public String getSKU_ID() {
        return SKU_ID;
    }

    public void setSKU_ID(String SKU_ID) {
        this.SKU_ID = SKU_ID;
    }

    public String getTARGET_ID() {
        return TARGET_ID;
    }

    public void setTARGET_ID(String TARGET_ID) {
        this.TARGET_ID = TARGET_ID;
    }

    public Sku getSKU() {
        return SKU;
    }

    public void setSKU(Sku SKU) {
        this.SKU = SKU;
    }

    public int getTARGET() {
        return TARGET;
    }

    public void setTARGET(int TARGET) {
        this.TARGET = TARGET;
    }


}
