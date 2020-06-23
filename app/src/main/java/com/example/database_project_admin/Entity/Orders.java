package com.example.database_project_admin.Entity;

public class Orders
{
    String id,salesman;
    ShopDetails shop;
    Sku sku;
    int quantity;

    public Orders()
    {
    }

    public Orders(String id, String salesman, ShopDetails shop, Sku sku, int quantity)
    {
        this.id = id;
        this.salesman = salesman;
        this.shop = shop;
        this.sku = sku;
        this.quantity = quantity;
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
