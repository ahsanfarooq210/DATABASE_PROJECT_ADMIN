package com.example.database_project_admin.Entity;

public class SalesmanId
{
    private String id,email,password;

    public SalesmanId()
    {
    }

    public SalesmanId(String id, String email, String password)
    {
        this.id = id;
        this.email = email;
        this.password = password;

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString() {
        return email;
    }
}
