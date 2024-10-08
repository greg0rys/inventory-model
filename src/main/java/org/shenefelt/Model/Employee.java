package org.shenefelt.Model;


import org.apache.commons.text.WordUtils;
import org.shenefelt.Helpers.InputValidator;
import org.shenefelt.Model.TimeKeeping.EmployeeTimeCard;

import static java.lang.System.out;

@SuppressWarnings("ALL")
public class Employee
{


    private String firstName;
    private String lastName;
    private String jobRole;
    private int hireStatus;
    private boolean isAdmin;
    private double payRate;
    private int numHoursWorked;
    private EmployeeTimeCard timeCard;

    // system generated fields
    private int companyID;
    private int userID;
    private String wholeName;
    private String email;
    private String username;
    private double overTimeRate;
    private int numSickHours; // rounded up to next 10th
    private int numVacationHours; // these will get rounded up



    public Employee() {}

    public Employee(String first, String last, String jobName, int hiredStat, boolean admin,
                    double rateOfPay, int workedHours, int cID, int uID, String fullName,
                    String emailAddress, String userName, double otRate)
    {
        firstName = first;
        lastName = last;
        jobRole = jobName;
        hireStatus = hiredStat;
        isAdmin = admin;
        payRate = rateOfPay;
        numHoursWorked = workedHours;
        companyID = cID;
        userID = uID;
        wholeName = fullName;
        email = emailAddress;
        username = userName;
        overTimeRate = otRate;
    }

    // new user before DB commit (e.g. will generate fields.
    public Employee(String first, String last, String jobName, int hireStat, boolean admin,
                    double rateOfPay, int workedHours, int totalSickHours, int totalPTOHours)
    {
        firstName = first;
        lastName = last;
        jobRole = jobName;
        hireStatus = hireStat;
        isAdmin = admin;
        payRate = rateOfPay;
        numSickHours = totalSickHours;
        numVacationHours = totalPTOHours;

        populateGeneratedFields();
        numHoursWorked = workedHours;
        wholeName = getFullName();
    }

    private void populateGeneratedFields()
    {
        wholeName = getFullName();
        username = generateUserName();
        email = getEmail();
        overTimeRate = (payRate * 1.5);
    }


    public Employee(String first, String last, String wholeName, String jobName, String email)
    {
        this.wholeName = wholeName;
        this.jobRole = jobName;
        this.email = email;
    }



    public Employee(int ID,
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

    public Employee(int ID,
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


    public Employee(int ID,
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
     * @param fName users first name
     * @param lName users last name
     * @param jobName users job role
     * @param cID the company ID the users associates with.
     */
    public Employee(String fName, String lName, String jobName, int cID)
    {
        firstName = fName;
        lastName = lName;
        jobRole = jobName;
        companyID = cID;
        wholeName = getFullName();

    }


    /**
     * Check to see if this user is an admin
     * @return true if the user is an admin false if they are not.
     */
    public boolean isAdmin() { return isAdmin; }

    /**
     * Manually change the users admin status
     * @param admin true if they are an admin false if else.
     */
    public void setAdmin(boolean admin) { isAdmin = admin; }

    /**
     * Get the users associated company id
     * @return the id of the company the user works at.
     */
    public int getCompanyID()
    {
        return companyID;
    }

    /**
     * Manually set the users company ID
     * @param companyID the id of the company the user works at.
     */
    public void setCompanyID(int companyID)
    {
        this.companyID = companyID;
    }

    /**
     * Get the Id of this user - their id is generated by the database.
     * @return the database id of the user.
     */
    public int getUserID()
    {
        return userID;
    }

    /**
     * Manually set the users ID - this is only used rarely and is not apart of standard operation
     * @param userID the users new ID.
     */
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    /**
     * Get the users first name
     * @return the first name of the user.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Set the users first name
     * @param firstName the users first name.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Get the users last name
     * @return the last name of the user.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Set the users last name
     * @param lastName the last name of the user.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Get the users job role
     * @return the job role of the user
     */
    public String getJobRole()
    {
        return jobRole;
    }

    /**
     * Set the users job role
     * @param jobRole the users new job role.
     */
    public void setJobRole(String jobRole)
    {
        this.jobRole = jobRole;
    }

    /**
     * Get the users full name by concat the first + last name.
     * @return the users full name
     */
    public String getFullName()
    {
        return WordUtils.capitalizeFully(firstName + " " + lastName);
    }


    /**
     * Set the users full name manually, but this will conflict with the getFullName method
     * @param fullName the users new full name.
     */
    public void setFullName(String fullName)
    {
        if(!InputValidator.validateFullName(fullName))
        {
            wholeName = " ";
            return;
        }
        wholeName = fullName.trim();
    }

    /**
     * Creates the users username first name first inital full lastname ex: yshenef
     * Also creates it for new database entries.
     * @return a string containing the users u
     */
    public String getUserName()
    {
        return firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase();
    }

    /**
     * Creates the users email address. This is used in 2 ways, one it generates it for the new users when
     * added to the database.
     * two it is used in formatted print statements.
     * @return the users email address e.x. gshenefelt1@gregoryshenefelt.com
     */
    public String getEmail()
    {
        return firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase() + "@gshenefelt.com";
    }

    public String generateUserName()
    {
        return firstName.toLowerCase().charAt(0) + "." + lastName.toLowerCase();
    }

    /**
     * get the users current hire status
     * @return 1 if they are employed 2 if they are not.
     */
    public int getHireStatus() { return hireStatus; }

    /**
     * Get the total number of hours this employee worked in the pay period
     * @return the number of hours worked or zero if no hours worked.
     */
    public int getWorkedHours() { return (numHoursWorked > 0) ? numHoursWorked : 0; }

    public double getPayRate() { return payRate; }
    public double getOverTimeRate() { return overTimeRate; }
    public EmployeeTimeCard getTimeCard() { return timeCard; }

    public void setHoursWorked(int hours)
    {
        numHoursWorked = hours;
    }

    /**
     * Formatted display of tghe user only showing user id first + last name
     */
    public void displayNameWithID()
    {
        out.printf("ID: %d - %s %s\n", userID, firstName, lastName );
    }

    /**
     * formatted toString override allows us to just pass objects to out.println();
     * @return the object represented as a string.
     */
    @Override
    public String toString()
    {
        return WordUtils.capitalizeFully(getFullName()) + "\n\tID: " + getUserID() + "\n\tEmail: " + getEmail() + "\n" +
                "\tUsername: " + getUserName()
                + "\n\tJob Role: " + getJobRole() + "\n\tHire Status: " + (hireStatus == 1 ? "Active" : "Terminated")
                + ("\n\tAdmin? " + (isAdmin ? " Yes" : " No"));
    }

    /**
     * See if two objects are the same. This is helpful when searching for a user in the local data struct.
     * @param o the object we are comparing too.
     * @return true or false depending on the return of user id == user id
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return userID == employee.userID;
    }

    /**
     * set the user to terminated status
     * @return true if were ablet to terminate the user; false if we cannot update it.
     */
    public boolean terminate()
    {
        hireStatus = 2;
        return true;
    }

    /**
     * set the user to hired status.
     * @return true if we were able to update the hire status; false if we cannot update it.
     */
    public boolean hire()
    {
        hireStatus = 1;
        return true;
    }




}
