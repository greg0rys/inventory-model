package org.shenefelt.Model;


import static java.lang.System.out;

@SuppressWarnings("ALL")
public class User
{

    private int companyID;
    private int userID;
    private String firstName;
    private String lastName;
    private String wholeName;
    private String jobRole;


    public User() {}

    public User(int ID,
                String fName,
                String lName,
                String fullName,
                int cID,
                String jobName)
    {
        userID = ID;
        firstName = fName;
        lastName = lName;
        wholeName = fullName;
        companyID = cID;
        jobRole = jobName;
    }

    /**
     * Users being inserted to the database without ID or full name
     * When adding the fullname to the insert use the method getFullname()
     * @param fName
     * @param lName
     * @param jobName
     * @param cID
     */
    public User(String fName, String lName, String jobName, int cID)
    {
        firstName = fName;
        lastName = lName;
        jobRole = jobName;
        companyID = cID;
        wholeName = getFullName();

    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }



    public String getFullName()
    {
        return firstName + " " + lastName;
    }

    public String getUserName()
    {
        return firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase();
    }

    public String getEmail()
    {
        return firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase() + "@gshenefelt.com";
    }

    public String display()
    {
        return getFullName() + " ID: " + getUserID() + "\n\tEmail: " + getEmail() + "\n\tUsername: " + getUserName();
    }

    public void displayNameWithID()
    {
        out.printf("ID: %d - %s %s\n", userID, firstName, lastName );
    }

    public void setFullName(String fullName)
    {
        wholeName = fullName.trim();
    }


}
