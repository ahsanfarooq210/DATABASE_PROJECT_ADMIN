package com.example.database_project_admin.Target;

public class Target_SalesMen {
    String TARGET_ID;
    String SKU_ID;
    String SaleMen_ID;
    int achieved;

    public Target_SalesMen() {
    }

    public Target_SalesMen(String TARGET_ID,String SKU_ID, String saleMen_ID, int achieved) {
        this.TARGET_ID = TARGET_ID;
        this.SKU_ID=SKU_ID;
        SaleMen_ID = saleMen_ID;
        this.achieved = achieved;
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

    public String getSaleMen_ID() {
        return SaleMen_ID;
    }

    public void setSaleMen_ID(String saleMen_ID) {
        SaleMen_ID = saleMen_ID;
    }

    public int getAchieved() {
        return achieved;
    }

    public void setAchieved(int achieved) {
        this.achieved = achieved;
    }
}
