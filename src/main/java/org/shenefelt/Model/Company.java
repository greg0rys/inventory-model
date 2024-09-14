package org.shenefelt.Model;

import org.shenefelt.Helpers.InputValidator;

public class Company
{
    private String locationName;
    private int dbID;
    private String companyName;
    private String companyLocation;



    public Company()
    {
        locationName = " ";
        companyName = " ";
        companyLocation = " ";
        dbID = 0;

    }

    public Company(int ID, String cName, String cLocation, String lName)
    {
        dbID = ID;
        companyName = cName;
        companyLocation = cLocation;
        locationName = lName;

    }

    public int getDbID() { return dbID; }
    public String getCompanyName() { return companyName; }
    public String getCompanyLocation() { return companyLocation; }
    public String getLocationName() { return locationName; }

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
     * set company location name ensure non-null string is passed in.
     * @param newLocation new city & state location.
     *                    e.g. Portland, Oregon.
     */
    public void setCompanyLocation(String newLocation)
    {
        // cleaner conditional with inversion
        companyLocation = (InputValidator.nonEmptyString(newLocation) ?
                "no location provided" : newLocation.trim());
    }

    /**
     * Set the companies location alias e.g. Medplace 121 - PDX
     * @param newAlias the new company alias
     */
    public void setLocationName(String newAlias)
    {
        locationName = (InputValidator.nonEmptyString(newAlias.trim()) ?  (companyName + " - No Alias") :
                (companyName + " " + newAlias.trim()));
    }





    @Override
    public String toString()
    {
        return "\nCompany Name: " + companyName + "\nCompany Location: " + companyLocation
                + "\nLocation Name: " + locationName;
    }


}
