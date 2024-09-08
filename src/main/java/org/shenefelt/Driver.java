package org.shenefelt;

import org.shenefelt.Controller.Managers.UserManager;
import org.shenefelt.Model.User;

import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.out;

public class Driver
{
    private UserManager userManager = new UserManager();
    public Driver() throws SQLException { }

    public void start() throws SQLException {
        menu();
    }

    private void menu() throws SQLException {
        boolean next = true;

        do {
            switch(getMenuChoice())
            {
                case 1:
                    out.println("Add User");
                    userManager.addNewUser(false);
                    break;
                case 2:
                    out.println("Update User");
                    break;
                case 3:
                    out.println("Delete User");
                    userMenu();
                    return; // you need to return each time you call a new menu or it will loop twice.
                case 4:
                    out.println("List All Users");
                    userManager.displayAllUsers();
                    out.println("Total Number of Users: " + userManager.getNumUsers());
                    break;
                case 5:
                    out.println("Exit");
                    return;
                case 6:
                    out.println("List All Admin Users");
                    out.println("going");

                    userManager.displayAdminUsers();
                    break;
                default:
                    out.println("Not a valid menu choice");
                    break;
            }
        } while(next);
    }

    private static int getMenuChoice()
    {
        out.println("1. Add User");
        out.println("2. Update User");
        out.println("3. Delete User");
        out.println("4. List All Users");
        out.println("5. Exit");
        out.println("6. List All Admin Users");

        out.println("Enter choice (e.x. 1, 2, 3, 4): ");

        return new Scanner(System.in).nextInt();
    }

    // User Menu Operations
    private void userMenu() throws SQLException {
        boolean next = true;

        do {
            switch(getUserMenuChoice())
            {
                case 1:
                    out.println("Hire User");

                    break;
                case 2:
                    out.println("Terminate User");
                    userManager.terminateUser(userManager.getUsers().get(userManager.searchUserByID()));
                    break;
                case 3:
                    out.println("Main Menu");
                    menu();
                    return;
                case 4:
                    out.println("Display Admins");
                    userManager.displayAdminUsers();

                default:
                    out.println("Not a valid menu choice");
            }
        } while(next);
    }


    private static int getUserMenuChoice()
    {
        out.println("1. Add New User");
        out.println("2. Change Employment Status");
        out.println("3. Change Employee Company");
        out.println("4. Update User");
        out.println("5. Display All Users from Company");
        out.println("6. Display All Employees (All Companies)");
        out.println("7. Display All Admin Users from Company");
        out.println("8. Display All Admin Users (All Companies)");

        out.println("Enter choice (e.x. 1, 2, 3): ");
        return new Scanner(System.in).nextInt();
    }


    // END


    // Item Menu Operations
    private void itemMenu() throws SQLException
    {
        boolean next = true;
        do {
            switch(getItemMenuChoice())
            {
                case 1:
                    out.println("Add Item");
                    break;
                case 2:
                    out.println("Update Item");
                    break;
            }
        } while(next);
    }

    private static int getItemMenuChoice()
    {
        out.println("1. Add Item");
        out.println("2. Update Item");
        out.println("3. Delete Item");
        out.println("4. List All Items");
        out.println("5. Back");
        return new Scanner(System.in).nextInt();
    }


    // END


}
