package org.shenefelt;

import org.shenefelt.Controller.UserManager;
import org.shenefelt.Controller.UserTableManager;

import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.out;

public class Driver
{
    private UserManager userManager = new UserManager();
    public Driver() { }

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
                    break;
                case 4:
                    out.println("List All Users");
                    userManager.displayAllUsers();
                    break;
                case 5:
                    out.println("Exit");
                    next = false;
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
        out.println("4. Exit");
        out.println("Enter choice (e.x. 1, 2, 3, 4): ");

        return new Scanner(System.in).nextInt();
    }
}
