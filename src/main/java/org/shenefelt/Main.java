package org.shenefelt;

import org.shenefelt.Constants.AvailStatus;
import org.shenefelt.Controller.ComputerTableManager;
import org.shenefelt.Controller.InventoryDatabase;
import org.shenefelt.Controller.TabletTableManager;
import org.shenefelt.DBDemos.DatabaseSeeds;
import org.shenefelt.Model.Computer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.out;
import static org.shenefelt.Controller.InventoryDatabase.getConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/Inventory";
    private static final String SEED_STRING = "INSERT INTO Computers(status, type,make, model, owner, serial_num) " +
            "VALUES" +
            "(default,?,?,?,?,?,?)";

    static String[] makes = {"Dell", "HP", "Acer", "LG"};
    static String[] models = {"Inspiron 14", "GPRO", "Gamer", "Gram"};
    static int[] productNums = {101351,101451,101551,101651};
    static String[] status = {"AVAILABLE", "DEPLOYED", "DEPLOYED_WITHOUT_DATA", "EWASTE"};
    static String[] owners = {"Greg", "Mariposa", "Ryan", "Dylan"};
    static String[] types = {"Laptop", "Mini-PC", "Desktop", "Thin Client"};
    private static ArrayList<Computer> computers = new ArrayList<>();
    private static ComputerTableManager computerTableManager;



    public static void main(String[] args) throws SQLException
    {
        DatabaseSeeds.createUsers();
    }








}