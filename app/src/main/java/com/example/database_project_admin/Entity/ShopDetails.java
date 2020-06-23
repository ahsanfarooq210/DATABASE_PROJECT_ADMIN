package com.example.database_project_admin.Entity;

public class ShopDetails
{
    private String id;
    private double latitude,longitude;
    private String adress,ownerName,ownerCnic,shopName,ownerMobile;

    public ShopDetails()
    {

    }


    public ShopDetails(String id, double latitude, double longitude, String adress, String ownerName, String ownerCnic, String shopName, String ownerMobile)
    {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adress = adress;
        this.ownerName = ownerName;
        this.ownerCnic = ownerCnic;
        this.shopName = shopName;
        this.ownerMobile = ownerMobile;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getAdress()
    {
        return adress;
    }

    public void setAdress(String adress)
    {
        this.adress = adress;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getOwnerCnic()
    {
        return ownerCnic;
    }

    public void setOwnerCnic(String ownerCnic)
    {
        this.ownerCnic = ownerCnic;
    }

    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public String getOwnerMobile()
    {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile)
    {
        this.ownerMobile = ownerMobile;
    }

    @Override
    public String toString()
    {
        return shopName+"\n"+ownerName+"\n"+ownerCnic;
    }
}
