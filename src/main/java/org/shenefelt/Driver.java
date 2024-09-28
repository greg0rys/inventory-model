package org.shenefelt;

import org.shenefelt.Controller.Managers.UserManager;
import org.shenefelt.Helpers.InputValidator;

import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.out;

public class Driver
{
    private final UserManager USER_MANAGER = new UserManager();
    private final Scanner scanner = new Scanner(System.in);

    public Driver() throws SQLException { }

    public void start() throws SQLException {
        menu();
    }

    private void menu() throws SQLException
    {
        boolean next = true;

        do {
            switch(getMenuChoice())
            {
                case 1:
                    out.println("Loading User Menu.");
                    userMenu();
                    break;
                case 2:
                    out.println("Loading Company Menu");
                    companyMenu();
                    break;
                case 3:
                    out.println("Loading Inventory Menu");
                    userMenu();
                    return; // you need to return each time you call a new menu or it will loop twice.
                case 4:
                    out.println("Have a good day!");
                    return;
                default:
                    out.println("Not a valid menu choice");
                    break;
            }
        } while(next);
    }

    /**
     * Has the user pick the proper menu operation menu.
     * @return the menu choice of the user.
     */
    private int getMenuChoice()
    {

        int choice = 0;
        out.println("1. User Menu");
        out.println("2. Company Menu");
        out.println("3. Inventory Menu");
        out.println("4. Exit");

        out.println("Enter choice (e.x. 1, 2, 3, 4): ");
        choice = scanner.nextInt();
        return (InputValidator.isValidMainMenuChoice(choice) ? choice
                : getMenuChoice());
    }


    // company menu operations.
    private void companyMenu() throws SQLException
    {
        boolean next = true;

    }



    // User Menu Operations
    private void userMenu() throws SQLException
    {
        boolean next = true;

        do {
            switch(getUserMenuChoice())
            {
                case 1:
                    out.println("Hire User");
                    USER_MANAGER.addNewUser(true);
                    break;
                case 2:
                    out.println("Terminate User");
                    USER_MANAGER.terminateUser(USER_MANAGER.getUsers().get(USER_MANAGER.searchUserByID()));
                    break;
                case 3:
                    out.println("Change Employee Company");
                    menu();
                    return;
                case 4:
                    out.println("Display Admins");
                    USER_MANAGER.displayAdminUsers();
                    break;
                case 5:
                    out.println("Display All Employees");
                    USER_MANAGER.displayAllUsers();
                    break;
                case 6:
                    out.println("Display All Employees from Company");
                    break;
                case 7:
                    out.println("Go back");
                    next = false; // we out.
                    break;


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
