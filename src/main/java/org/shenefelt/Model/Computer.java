/**
 * The super class for all computer objects.
 */
package org.shenefelt.Model;

import org.shenefelt.Constants.AvailStatus;

import static java.lang.System.out;

public class Computer
{
    private String availability;
    private String model, make, owner, deviceType;
    private int dbID,userID,companyID, serialNum;

    public Computer() { /* default no args */ }

    /**
     * for those that are read from the CSV or called from the database
     * @param pcMake maker of PC
     * @param pcModel model of PC
     * @param type {laptop,desktop,server}
     * @param status {deployed,destroyed,etc..}
     * @param company_id ID of owner company
     * @param user_id ID of assigned user
     */
    public Computer(
                    String pcMake,
                    String pcModel,
                    String type,
                    String status,
                    int company_id,
                    int user_id
                    )
    {
        availability = status;
        deviceType = type;
        model = pcModel;
        make = pcMake;
        userID = user_id;
        companyID = company_id;

    }


    public Computer(
            int ID,
            String type,
            String availStat,
            String pcModel,
            String pcMake,
            String pcOwner,
            int pcSerialNum,
            int cID,
            int uID)
    {
        dbID = ID;
        deviceType = type;
        availability = availStat;
        model = pcModel;
        make = pcMake;
        owner = pcOwner;
        serialNum = pcSerialNum;
        companyID = cID;
        userID = uID;
    }


    public void display()
    {
        out.println("Database ID: " + dbID);
        out.println("Availability: " + availability);
        out.println("Make: " + make);
        out.println("Model: " + model);
        out.println("Company ID: " + companyID);
        out.println("User ID: " + userID);

        insertSeperator();
    }

    public void display(String type)
    {
        insertSeperator();

        out.println("Database ID: " + dbID);
        out.println("Availability: " + availability);
        out.println("Make: " + make);
        out.println("Model: " + model);
        out.println("Device Type: " + type);
        out.println("Owner: " + companyID);
        out.println("User: " + userID);
        out.println();

        insertSeperator();
    }

    private void insertSeperator()
    {
        out.println(" ********** ");
    }

    public String getStatusString()
    {
        return availability.toString();
    }

    public String getModel() { return model;}
    public String getMake() {return make;}
    public String getOwner() {return owner;}



    public String getDeviceType() { return deviceType; }
 // private db methods.

    public int getDbID() { return dbID; }

    public void setMake(String newMake)
    { make = newMake; }

}
