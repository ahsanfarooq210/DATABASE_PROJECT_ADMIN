package com.example.database_project_admin.Target.Entity;

import com.example.database_project_admin.Entity.Sku;


public class Target {
   String TARGET_ID;
   Sku SKU;
   int TARGET;
   long TARGET_DATE;
   String SKU_ID;

    public Target() {
    }

    public Target(String TARGET_ID, Sku SKU,String SKU_ID, int TARGET, long TARGET_DATE) {
        this.TARGET_ID = TARGET_ID;
        this.SKU = SKU;
        this.TARGET = TARGET;
        this.SKU_ID=SKU_ID;
        this.TARGET_DATE = TARGET_DATE;

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

    public long getTARGET_DATE() {
        return TARGET_DATE;
    }

    public void setTARGET_DATE(long TARGET_DATE) {
        this.TARGET_DATE = TARGET_DATE;
    }
}
