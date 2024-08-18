package org.shenefelt.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InventoryDatabase
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Inventory";
    private static final String DB_USER = "flarp";
    private static final String DB_PASSWORD = "Radiokid!!0329";

    public InventoryDatabase() { }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


}
