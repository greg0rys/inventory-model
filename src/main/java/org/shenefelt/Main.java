package org.shenefelt;

import org.shenefelt.Controller.Managers.CompanyManager;
import org.shenefelt.Controller.Managers.UserManager;
import org.shenefelt.Menus.MainMenu;
import org.shenefelt.Model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import static java.lang.System.out;


public class Main
{
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/Inventory";
    private static final Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws SQLException
    {
        // new Driver().start(); // why create an object and store to only run one method???

        CompanyManager CM = new CompanyManager();
        UserManager UM = new UserManager();

        out.println("There are " + CM.getTotalNumberOfCompanies() + " companies in the systems" +
                " database.. Generating list.");
//        CM.displayAllCompanies();

        CM.displayAllStaff();


        ArrayList<Employee> temp = UM.getAllCompanyUsers(6);

        if(temp.isEmpty())
            out.println("No users for company");
        else
            out.println("Users for company: " + temp.size() + "\n***DISPLAYING***");

        temp.forEach(s -> out.println(s.toString() + "\n"));


        MainMenu.menu();


    }

}