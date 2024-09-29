package org.shenefelt.Model.InventoryItems;

public class Tablet
{
    private String availability, make, model, owner;
    private int dbID, serialNum;
    private double price;

    public String getAvailability()
    {
        return availability;
    }

    public void setAvailability(String availability)
    {
        this.availability = availability;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public int getDbID()
    {
        return dbID;
    }

    public void setDbID(int dbID)
    {
        this.dbID = dbID;
    }

    public int getSerialNum()
    {
        return serialNum;
    }

    public void setSerialNum(int serialNum)
    {
        this.serialNum = serialNum;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public Tablet() { }

    public Tablet(String availability, String make, String model, String owner, int serialNum, double price)
    {
        this.availability = availability;
        this.make = make;
        this.model = model;
        this.owner = owner;
        this.serialNum = serialNum;
        this.price = price;
    }




}
