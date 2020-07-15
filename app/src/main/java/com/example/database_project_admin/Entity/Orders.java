package com.example.database_project_admin.Entity;

public class Orders
{
    String id,salesman;
    ShopDetails shop;
    Sku sku;
    int quantity;
    String orderStatus;
    String shop_id;
    String sku_id;
    String company_id;
    String catagory_id;

    public Orders()
    {
    }

    public Orders(String id, String salesman, ShopDetails shop, Sku sku, int quantity,String orderStatus)
    {
        this.id = id;
        this.salesman = salesman;
        this.shop = shop;
        this.sku = sku;
        this.quantity = quantity;
        this.orderStatus=orderStatus;
        this.shop_id=shop.getId();
        this.sku_id=sku.getId();
        this.company_id=sku.getCompany().getId();
        this.catagory_id=sku.getCatagory().getId();
    }

    public String getShop_id()
    {
        return shop_id;
    }

    public void setShop_id(String shop_id)
    {
        this.shop_id = shop_id;
    }

    public String getSku_id()
    {
        return sku_id;
    }

    public void setSku_id(String sku_id)
    {
        this.sku_id = sku_id;
    }

    public String getCompany_id()
    {
        return company_id;
    }

    public void setCompany_id(String company_id)
    {
        this.company_id = company_id;
    }

    public String getCatagory_id()
    {
        return catagory_id;
    }

    public void setCatagory_id(String catagory_id)
    {
        this.catagory_id = catagory_id;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSalesman()
    {
        return salesman;
    }

    public void setSalesman(String salesman)
    {
        this.salesman = salesman;
    }

    public ShopDetails getShop()
    {
        return shop;
    }

    public void setShop(ShopDetails shop)
    {
        this.shop = shop;
    }

    public Sku getSku()
    {
        return sku;
    }

    public void setSku(Sku sku)
    {
        this.sku = sku;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getShopName()
    {
        return shop.getShopName();
    }
}
