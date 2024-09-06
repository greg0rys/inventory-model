package org.shenefelt.Controller.Managers;

import org.shenefelt.Controller.TableMangers.UserTableManager;
import org.shenefelt.Model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.lang.System.out;

public class UserManager
{
    private final ArrayList<User> ALL_USERS = new ArrayList<>(); // store a local copy to avoid excess calls to DB
    private boolean hasLocalUpdates;
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());


    public UserManager() throws SQLException
    {
        ALL_USERS.addAll(UserTableManager.getUsers()); // load our users from the database.
        hasLocalUpdates = false;
    }

    public boolean hasLocalUpdates() { return hasLocalUpdates; }


    public ArrayList<User> getUsers() { return ALL_USERS; }


    public boolean addNewUser(boolean isAdmin) throws SQLException
    {
        ALL_USERS.add(collectUserData());
        hasLocalUpdates = true;
        return pushLocalUpdate(ALL_USERS.get(ALL_USERS.size() - 1));
    }

    private void createAdminUser() { }


    private boolean pushLocalUpdate(User U) throws SQLException { return UserTableManager.addUser(U); }


    private User collectUserData()
    {
        logger.info("Collecting user data..");
        Scanner sc = new Scanner(System.in);
        User temp = new User();
        out.println("Enter Users First Name: ");
        temp.setFirstName(sc.nextLine());
        out.println("Enter Users Last Name: ");
        temp.setLastName(sc.nextLine());
        out.println("Enter Users Job Title: ");
        temp.setJobRole(sc.nextLine());
        out.println("Users Company ID: ");
        temp.setCompanyID(sc.nextInt());
        logger.info("User data collected.. Validating inputs.");

        return temp;
    }

    public boolean updateUsernameInDB(int userID, String username) throws SQLException
    {
        if(UserTableManager.updateUsername(userID,username))
        {
            ALL_USERS.clear();
            ALL_USERS.addAll(UserTableManager.getUsers());
            return true;
        }

        return false;
    }

    public int searchUserByID()
    {
        for(User u : ALL_USERS)
            u.displayNameWithID();
        out.println("Please Enter the ID of The User Above: ");
        // make sure you handle errors
        return new Scanner(System.in).nextInt();
    }

    public void displayAllUsers() throws SQLException {
        if(ALL_USERS.isEmpty())
            ALL_USERS.addAll(UserTableManager.getUsers());

        for(User u : ALL_USERS)
            out.println(u.display());
    }

    public int getNumUsers() { return ALL_USERS.size(); }


    /**
     * change the users hire status inside the DB
     * @param U the user we are going to update
     * @param hireStatus the status we are going to set
     * @return true if we successfully modified the DB false - if redundant or SQLException
     * @throws SQLException DB Connection Error
     */
    public boolean changeHireStatus(User U, int hireStatus) throws SQLException
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
    public boolean terminateUser(User U) throws SQLException
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
     * @param U the User we will update
     * @return true if updated, false if the record was unable to update.
     * @throws SQLException database error.
     */

    // remove this and condense.
    public boolean hireUser(User U) throws SQLException
    {
        if(changeHireStatus(U, 2))
        {
            out.println("Hired: " + U.getFullName());
            updateLocal(U);
            return true;
        }

        return false;
    }

    public boolean newHire()
    {
        User temp = new User();
        Scanner sc = new Scanner(System.in);
        out.println("Enter Users First Name: ");
        temp.setFirstName(sc.nextLine().trim());
        out.println("Enter Users Last Name: ");
        temp.setLastName(sc.nextLine().trim());
        out.println("Enter Users Job Title: ");
        temp.setJobRole(sc.nextLine().trim());
        out.println("Users Company ID: ");
        temp.setCompanyID(sc.nextInt());


        return false;
    }


    private boolean updateLocal(User U)
    {
        if(U.hashCode() == ALL_USERS.get(ALL_USERS.indexOf(U)).hashCode()) //these are not hte same object if !=
            return false;

        User temp = ALL_USERS.get(ALL_USERS.indexOf(U));
        Logger logger = Logger.getLogger("org.shenefelt.Main");
        logger.info("Hey boo");


        return (ALL_USERS.set(ALL_USERS.indexOf(U), U) == null) ? true : false;
    }


    public void displayAdminUsers()
    {
//        if(ALL_USERS.isEmpty())
//        {
//            out.println("There are no admins in the list");
//            return;
//        }

        out.println("Found admins?");

        ALL_USERS.forEach(u -> {
            u.setAdmin(false);
        });

        out.println(ALL_USERS.get(1).display());
        ALL_USERS.get(1).setAdmin(true);

        int counter = 0;
        for(User u : ALL_USERS)
        {
            if(u.getUserID() % 2 == 0)
                u.setAdmin(true);
            if(u.isAdmin()) {
                out.println(u.display());
                counter++;
            }
        }

        logger.info("Total Admins: " + counter);
    }

}
