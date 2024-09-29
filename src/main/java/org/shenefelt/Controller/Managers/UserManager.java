package org.shenefelt.Controller.Managers;

import org.apache.commons.text.WordUtils;
import org.shenefelt.Controller.TableMangers.UserTableManager;
import org.shenefelt.Helpers.InputValidator;
import org.shenefelt.Model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.lang.System.out;

public class UserManager
{
    private final ArrayList<Employee> ALL_Employees = new ArrayList<>(); // store a local copy to avoid excess calls to DB
    private final static ArrayList<Employee> NEW_EMPLOYEES = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());


    public UserManager() throws SQLException
    {
        ALL_Employees.addAll(UserTableManager.getUsers()); // load our users from the database.
    }


    public ArrayList<Employee> getUsers() { return ALL_Employees; }


    public boolean addNewUser(boolean isAdmin) throws SQLException
    {
        ALL_Employees.add(collectUserData());
        return pushLocalUpdate(ALL_Employees.get(ALL_Employees.size() - 1));
    }

    private void createAdminUser() { }


    private boolean pushLocalUpdate(Employee U) throws SQLException { return UserTableManager.addUser(U); }


    /**
     * Collect data for a new user, all inputs are sanitized.
     * @return
     */
    private Employee collectUserData()
    {
        logger.info("Collecting user data..");
        Scanner sc = new Scanner(System.in);
        Employee temp = new Employee();
        String S, X, Y;
        int I = 0;

        out.println("Enter Users First Name: ");
        temp.setFirstName(WordUtils.capitalizeFully(sc.nextLine()));
        out.println("Enter Users Last Name: ");
        temp.setLastName(WordUtils.capitalizeFully(sc.nextLine()));
        out.println("Enter Users Job Title: ");
        temp.setJobRole(WordUtils.capitalizeFully(sc.nextLine()));
        out.println("Users Company ID: ");
        temp.setCompanyID(sc.nextInt());
        logger.info("Employee data collected.. Validating inputs.");

        return temp;
    }

    /**
     * Updadate
     * TODO: this needs to be changed. we need to make sure that the username is formatted properly
     * @param userID
     * @param username
     * @return
     * @throws SQLException
     */
    public boolean updateUsernameInDB(int userID, String username) throws SQLException
    {
        if(UserTableManager.updateUsernameAndEmail(userID,username))
        {
            ALL_Employees.clear();
            ALL_Employees.addAll(UserTableManager.getUsers());
            return true;
        }

        return false;
    }

    /**
     * search for a user by user ID
     * @return the user ID we want to select
     */
    public int searchUserByID()
    {
        int input = 0;
        for(Employee u : ALL_Employees)
            u.displayNameWithID();

        out.println("Please Enter the ID of The Employee Above: ");
        input = new Scanner(System.in).nextInt();

        if(!InputValidator.validateUserSelectionByID(input))
            return searchUserByID();

        return input;
    }

    /**
     * Display all users.
     * @throws SQLException if users cannot be loaded into the list
     */
    public void displayAllUsers() throws SQLException
    {
        // ensure that our list is actually loaded with users
        if(ALL_Employees.isEmpty())
            ALL_Employees.addAll(UserTableManager.getUsers());

        for(Employee u : ALL_Employees)
            out.println(u.toString());


    }

    /**
     * Display all known admin users
     */
    public void displayAdminUsers()
    {
        if(ALL_Employees.isEmpty())
        {
            out.println("There are no admins in the list");
            return;
        }

        for(Employee u : ALL_Employees)
        {
            if(u.isAdmin()) {
                out.println(u); // overridden Employee.toString() allows us to just pass the user and it will be called implictly.

            }
        }

    }

    public void changeUsersCompany(Employee U)
    {
        if(U == null) return;


        out.println("Please Select a Company ID from Above");
        int input = new Scanner(System.in).nextInt();

    }

    /**
     * Get the number of users in the list
     * @return the total number of users in the list.
     */
    public int getNumUsers() { return ALL_Employees.size(); }


    /**
     * change the users hire status inside the DB
     * @param U the user we are going to update
     * @param hireStatus the status we are going to set
     * @return true if we successfully modified the DB false - if redundant or SQLException
     * @throws SQLException DB Connection Error
     */
    public boolean changeHireStatus(Employee U, int hireStatus) throws SQLException
    {
        if(U == null) return false;

        if(U.getHireStatus() == hireStatus)  // check for redundancy
            return false;

        if(hireStatus == 1)
            U.hire();

        if(hireStatus == 2)
            U.terminate();

        return UserTableManager.updateHireStatus(U.getUserID(),U.getHireStatus());
    }


    // we can get rid of this it is redundant to the above method as we are using an int flag.
    public boolean terminateUser(Employee U) throws SQLException
    {
        if(changeHireStatus(U, 1))
        {
            out.println("Terminated: " + U.getFullName());
            return true;
        }

        return false;
    }

    /**
     * Update the users hired status to active
     * @param U the Employee we will update
     * @return true if updated, false if the record was unable to update.
     * @throws SQLException database error.
     */

    // remove this and condense.
    public boolean hireUser(Employee U) throws SQLException
    {
        if(changeHireStatus(U, 2))
        {
            out.println("Hired: " + U.getFullName());
            updateLocal(U);
            return true;
        }

        return false;
    }


    private boolean updateLocal(Employee U)
    {
        if(!hasUser(U)) return false;


        return ALL_Employees.set(ALL_Employees.indexOf(U), U) == null;
    }





    /**
     * Check to see if the user is present
     * @param U the user we wish to check for
     * @return true if the user is found false if else.
     */
    public boolean hasUser(Employee U) { return ALL_Employees.contains(U); }

    /**
     * Get a user from the list with their database ID.
     * @param dbID the ID we wish to call
     * @return the user if they are located null if they are not
     */
    public Employee getUser(int dbID)
    {
        if(!InputValidator.validateUserSelectionByID(dbID))
            return null;

        Employee[] temp = new Employee[1]; // store the result in a single array vs an atomic value.

        // seek out the user with a matching id.
        ALL_Employees.forEach(e->{
            if(e.getUserID() == dbID)
                temp[0] = e;
        });

        return temp[0];
    }


    public boolean updateUserFullName() throws SQLException {
        Scanner sc = new Scanner(System.in);
        Employee temp = new Employee();
        out.println("Enter Users First Name: ");
        temp.setFirstName(WordUtils.capitalizeFully(sc.nextLine()).strip());
        out.println("Enter Users Last Name: ");
        temp.setLastName(WordUtils.capitalizeFully(sc.nextLine()).strip());
        out.println("Users Company ID: ");
        temp.setCompanyID(sc.nextInt());

        return UserTableManager.updateUserFullName(temp);

    }


    public ArrayList<Employee> getAllCompanyUsers(int companyID)
    {
        ArrayList<Employee> temp = new ArrayList<>();

        ALL_Employees.forEach(e->{
            if(e.getCompanyID() == companyID)
                temp.add(e);
        });

        return temp;
    }

}
