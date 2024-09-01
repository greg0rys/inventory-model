package org.shenefelt.Controller.Managers;

import org.shenefelt.Controller.UserTableManager;
import org.shenefelt.Model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

public class UserManager
{
    private final ArrayList<User> ALL_USERS = new ArrayList<>();
    private boolean hasLocalUpdates;


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


    public boolean changeHireStatus(User U, int hireStatus) throws SQLException {
        if(U.getHireStatus() == hireStatus)
            return false;
        if(hireStatus == 1)
            U.hire();
        if(hireStatus == 2)
            U.terminate();

        return UserTableManager.updateHireStatus(U.getUserID(),U.getHireStatus());
    }


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
    public boolean hireUser(User U) throws SQLException
    {
        if(changeHireStatus(U, 2))
        {
            out.println("Hired: " + U.getFullName());
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

}
