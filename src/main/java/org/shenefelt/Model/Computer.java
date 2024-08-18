/**
 * The super class for all computer objects.
 */
package org.shenefelt.Model;

import org.shenefelt.Constants.AvailStatus;

import static java.lang.System.out;

public class Computer
{
    private AvailStatus availability;
    private String model, make, owner, deviceType;
    private int serialNum, dbID;

    public Computer() { /* default no args */ }


    public Computer(int ID,
                    String type,
                    AvailStatus status,
                    String pcModel,
                    String pcMake,
                    String pcOwner,
                    int pcSerialNum)
    {
        availability = status;
        deviceType = type;
        model = pcModel;
        make = pcMake;
        owner = pcOwner;
        serialNum = pcSerialNum;
        dbID = ID;
    }

    /**
     * new PC constructor it will not have a database ID until called back from the database.
     * @param status
     * @param pcModel
     * @param pcMake
     * @param pcOwner
     * @param pcSerialNum
     */
    public Computer(
                    AvailStatus status,
                    String type,
                    String pcModel,
                    String pcMake,
                    String pcOwner,
                    int pcSerialNum)
    {
        deviceType = type;
        availability = status;
        model = pcModel;
        make = pcMake;
        owner = pcOwner;
        serialNum = pcSerialNum;
    }

    public void display()
    {
        String seperator = " ********** "; // added for printing the whole list of PCS.
        out.println(seperator);
        out.println("Database ID: " + dbID);
        out.println("Availability: " + availability.toString());
        out.println("Make: " + make);
        out.println("Model: " + model);
        out.println("Serial Number: " + serialNum);
        out.println("Owner: " + owner);
        out.println(seperator);
    }

    public String getStatusString()
    {
        return availability.toString();
    }

    public String getModel() { return model;}
    public String getMake() {return make;}
    public int getSerialNum() {return serialNum;}
    public String getOwner() {return owner;}

    public void templateString()
    {
        out.println(getStatusString()
                    + getMake()
                    + getModel()
                    + getOwner()
                    + getSerialNum()
        );
    }

    public String getDeviceType() { return deviceType; }
 // private db methods.


}
