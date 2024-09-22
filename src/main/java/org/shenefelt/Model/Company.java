package org.shenefelt.Model;

import org.shenefelt.Helpers.InputValidator;

import java.util.ArrayList;

public class Company
{
    private String locationName;
    private int dbID;
    private String companyName;
    private String companyLocation;
    private int numberOfEmployees;
    private ArrayList<User> staff;



    public Company()
    {
        locationName = " ";
        companyName = " ";
        companyLocation = " ";
        dbID = 0;
        numberOfEmployees = 0;
        staff = new ArrayList<>();

    }

    public Company(int ID, String cName, String cLocation, String lName, int empNum)
    {
        dbID = ID;
        companyName = cName;
        companyLocation = cLocation;
        locationName = lName;
        numberOfEmployees = empNum;
        staff = new ArrayList<>();

    }

    public Company(int ID, String cName, String cLocation, String lName, int empNum, ArrayList<User> employees)
    {
        dbID = ID;
        companyName = cName;
        companyLocation = cLocation;
        locationName = lName;
        numberOfEmployees = empNum;
        staff = employees;

    }

    public int getDbID() { return dbID; }
    public String getCompanyName() { return companyName; }
    public String getCompanyLocation() { return companyLocation; }
    public String getLocationName() { return locationName; }
    public int getNumberOfEmployees() { return numberOfEmployees; }

    /**
     * Set the companies name ensure non-null string is passed in
     * @param newName the new company name.
     */
    public void setCompanyName(String newName)
    {

        companyName = (InputValidator.nonEmptyString(newName) ? "no company provided"
                                                               : newName);
    }

    /**
     * set company location name ensures non-null string is passed in.
     * @param newLocation new city & state location.
     *                    e.g., Portland, Oregon.
     */
    public void setCompanyLocation(String newLocation)
    {
        // cleaner conditional with inversion
        companyLocation = (InputValidator.nonEmptyString(newLocation) ?
                "no location provided" : newLocation.trim());
    }

    /**
     * Set the companies location alias e.g., Medplace 121 - PDX
     * @param newAlias the new company alias
     */
    public void setLocationName(String newAlias)
    {

        locationName = (InputValidator.nonEmptyString(newAlias.trim()) ?
                (companyName + " - No Alias") :
                (companyName + " " + newAlias.trim()));
    }


    public void setStaff(ArrayList<User> newStaff)
{
    staff = newStaff;
}

public ArrayList<User> getStaff()
    {
        return staff;
    }





    @Override
    public String toString()
    {
        return "\nCompany Name: " + companyName + "\nCompany Location: " + companyLocation
                + "\nLocation Name: " + locationName + "\nNumber of Employees: " + numberOfEmployees;
    }


}
