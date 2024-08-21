package org.shenefelt.Controller;

import org.shenefelt.Model.Computer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDatabase {
    // reset port on db to 3306 when on linux machines or whatever default port you are using for mariadb
    private static final String DB_URL = "jdbc:mysql://localhost:3307/Inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Radiokid!!0329";

    public InventoryDatabase() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

//    public static boolean seedAllTables()
//    {
//
//    }

    private static ArrayList<Computer> getComputerValues() {
        String[] status = { "Available", "Deployed", "Need Data", "Ewaste" };
        String[] types = { "Desktop", "Laptop", "Mini-Pc", "Tablet" };
        String[] models = { "Dell", "HP", "Lenovo", "LG" };
        String[] makes = { "XPS 150", "GPRO", "ThinkBook", "Gram" };
        String[] owners = { "Greg", "Ryan", "Dylan", "Jennifer" };
        int[] serialNos = {101251, 101252, 101253, 101254 };

        return new ArrayList<Computer>();

    }

}

