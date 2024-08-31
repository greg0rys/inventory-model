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

        out.println("Enter choice (e.x. 1, 2, 3, 4): ");

        return new Scanner(System.in).nextInt();
    }

    private static int getUserMenuChoice()
    {
        out.println("1. Hire User");
        out.println("2. Terminate User");
        out.println("3. Back");

        out.println("Enter choice (e.x. 1, 2, 3): ");
        return new Scanner(System.in).nextInt();
    }

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

                default:
                    out.println("Not a valid menu choice");
            }
        } while(next);
    }
}
