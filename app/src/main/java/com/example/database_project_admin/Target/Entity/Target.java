package com.example.database_project_admin.Target.Entity;

import com.example.database_project_admin.Entity.Sku;


public class Target {
   String TARGET_ID;
   Sku SKU;
   int TARGET;
   String salesmenEmail;
   String startDateString ,endDateString;
   String skuID;
    String targetStatus;
    public Target() {
    }

    public Target(String TARGET_ID, Sku SKU,String skuID,String salesmenEmail, int TARGET, String startDateString ,String endDateString ,String targetStatus) {
        this.TARGET_ID = TARGET_ID;
        this.SKU = SKU;
        this.TARGET = TARGET;
        this.skuID=skuID;
        this.salesmenEmail=salesmenEmail;
        this.startDateString = startDateString ;
        this.endDateString =endDateString;
        this.targetStatus=targetStatus;

    }

    public String getSkuID() {
        return skuID;
    }

    public void setSkuID(String skuID) {
        this.skuID = skuID;
    }

    public String getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(String targetStatus) {
        this.targetStatus = targetStatus;
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

    public String getskuID() {
        return skuID;
    }

    public void setskuID(String skuID) {
        this.skuID = skuID;
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
