package org.shenefelt;

import org.shenefelt.Controller.Managers.CompanyManager;

import java.sql.SQLException;
import java.util.logging.Logger;

import static java.lang.System.out;


public class Main
{
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/Inventory";
    private static final Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws SQLException
    {
        // new Driver().start(); // why create an object and store to only run one method???

        new CompanyManager().displayAllCompanies();

        out.println();

        new CompanyManager().displayAllCompanyEmployeeCounts();

    }

}