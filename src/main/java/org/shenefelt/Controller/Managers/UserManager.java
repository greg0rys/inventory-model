package org.shenefelt.Controller.Managers;

import org.apache.commons.text.WordUtils;
import org.shenefelt.Controller.TableMangers.UserTableManager;
import org.shenefelt.Helpers.InputValidator;
import org.shenefelt.Model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.lang.System.out;

public class UserManager
{
    private final ArrayList<User> ALL_USERS = new ArrayList<>(); // store a local copy to avoid excess calls to DB
    private final static ArrayList<User> NEW_USERS = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());


    public UserManager() throws SQLException
    {
        ALL_USERS.addAll(UserTableManager.getUsers()); // load our users from the database.
    }


    public ArrayList<User> getUsers() { return ALL_USERS; }


    public boolean addNewUser(boolean isAdmin) throws SQLException
    {
        ALL_USERS.add(collectUserData());
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
        temp.setFirstName(WordUtils.capitalizeFully(sc.nextLine()));
        out.println("Enter Users Last Name: ");
        temp.setLastName(WordUtils.capitalizeFully(sc.nextLine()));
        out.println("Enter Users Job Title: ");
        temp.setJobRole(WordUtils.capitalizeFully(sc.nextLine()));
        out.println("Users Company ID: ");
        temp.setCompanyID(sc.nextInt());
        logger.info("User data collected.. Validating inputs.");

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
            ALL_USERS.clear();
            ALL_USERS.addAll(UserTableManager.getUsers());
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
        for(User u : ALL_USERS)
            u.displayNameWithID();

        out.println("Please Enter the ID of The User Above: ");
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
        if(ALL_USERS.isEmpty())
            ALL_USERS.addAll(UserTableManager.getUsers());

        for(User u : ALL_USERS)
            out.println(u.toString());


    }

    /**
     * Display all known admin users
     */
    public void displayAdminUsers()
    {
        if(ALL_USERS.isEmpty())
        {
            out.println("There are no admins in the list");
            return;
        }

        for(User u : ALL_USERS)
        {
            if(u.isAdmin()) {
                out.println(u); // overridden User.toString() allows us to just pass the user and it will be called implictly.

            }
        }

    }

    /**
     * Get the number of users in the list
     * @return the total number of users in the list.
     */
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


    private boolean updateLocal(User U)
    {
        if(!hasUser(U)) return false;


        return ALL_USERS.set(ALL_USERS.indexOf(U), U) == null;
    }





    /**
     * Check to see if the user is present
     * @param U the user we wish to check for
     * @return true if the user is found false if else.
     */
    public boolean hasUser(User U) { return ALL_USERS.contains(U); }

    /**
     * Get a user from the list with their database ID.
     * @param dbID the ID we wish to call
     * @return the user if they are located null if they are not
     */
    public User getUser(int dbID)
    {
        if(!InputValidator.validateUserSelectionByID(dbID))
            return null;

        User[] temp = new User[1]; // store the result in a single array vs an atomic value.

        // seek out the user with a matching id.
        ALL_USERS.forEach(e->{
            if(e.getUserID() == dbID)
                temp[0] = e;
        });

        return temp[0];
    }


    public boolean updateUserFullName() throws SQLException {
        Scanner sc = new Scanner(System.in);
        User temp = new User();
        out.println("Enter Users First Name: ");
        temp.setFirstName(WordUtils.capitalizeFully(sc.nextLine()).strip());
        out.println("Enter Users Last Name: ");
        temp.setLastName(WordUtils.capitalizeFully(sc.nextLine()).strip());
        out.println("Users Company ID: ");
        temp.setCompanyID(sc.nextInt());

        return UserTableManager.updateUserFullName(temp);

    }



}
