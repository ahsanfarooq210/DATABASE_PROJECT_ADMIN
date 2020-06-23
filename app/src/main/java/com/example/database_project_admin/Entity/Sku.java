package com.example.database_project_admin.Entity;

public class Sku
{
    private String id;
    private SkuCompany company;
    private SkuCatagory catagory;
    String productName;
    int size;

    public Sku()
    {
        id="";
        company=null;
        catagory=null;
        productName="";
        size=0;
    }

    public Sku(String  id, SkuCompany company, SkuCatagory catagory, String productName, int size)
    {
        this.id = id;
        this.company = company;
        this.catagory = catagory;
        this.productName = productName;
        this.size = size;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public SkuCompany getCompany()
    {
        return company;
    }

    public void setCompany(SkuCompany company)
    {
        this.company = company;
    }

    public SkuCatagory getCatagory()
    {
        return catagory;
    }

    public void setCatagory(SkuCatagory catagory)
    {
        this.catagory = catagory;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return company.toString()+
                " " + catagory.toString() +
                " " + productName  +
                " " + size;
    }
}
