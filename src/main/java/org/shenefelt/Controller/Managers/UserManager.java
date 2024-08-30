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

    public UserManager()
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

    public void displayAllUsers()
    {
        for(User u : ALL_USERS)
            u.display();
    }


}
