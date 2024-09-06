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
    private String email;
    private String username;
    private int hireStatus;
    private boolean isAdmin;


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

    public User(int ID,
                String fName,
                String lName,
                String fullName,
                int cID,
                String jobName,
                String userN,
                String emailAddress,
                int hireStat)
    {
        userID = ID;
        firstName = fName;
        lastName = lName;
        wholeName = fullName;
        companyID = cID;
        jobRole = jobName;
        email = emailAddress;
        username = userN;
        hireStatus = hireStat;
    }

    public User(int ID,
                String fName,
                String lName,
                String fullName,
                int cID,
                String jobName,
                String userN,
                String emailAddress,
                int hireStat,
                boolean admin)
    {
        userID = ID;
        firstName = fName;
        lastName = lName;
        wholeName = fullName;
        companyID = cID;
        jobRole = jobName;
        email = emailAddress;
        username = userN;
        hireStatus = hireStat;
        isAdmin = admin;
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


    public boolean isAdmin() { return isAdmin; }

    public void setAdmin(boolean admin) { isAdmin = admin; }

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

    public void setFullName(String fullName)
    {
        wholeName = fullName.trim();
    }

    public String getUserName()
    {
        return firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase();
    }

    public String getEmail()
    {
        return firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase() + "@gshenefelt.com";
    }

    public int getHireStatus() { return hireStatus; }


    public String display()
    {
        return getFullName() + " ID: " + getUserID() + "\n\tEmail: " + getEmail() + "\n\tUsername: " + getUserName()
                + "\n\tJob Role: " + getJobRole() + "\n\tHire Status: " + (hireStatus == 1 ? "Active" : "Terminated")
                + ("\n\tAdmin? " + (isAdmin ? " Yes" : " No"));
    }

    public void displayNameWithID()
    {
        out.printf("ID: %d - %s %s\n", userID, firstName, lastName );
    }


    public boolean terminate()
    {
        hireStatus = 2;
        return true;
    }

    public boolean hire()
    {
        hireStatus = 1;
        return true;
    }




}
