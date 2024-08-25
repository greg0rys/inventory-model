package org.shenefelt;

import org.shenefelt.Constants.AvailStatus;
import org.shenefelt.Controller.ComputerTableManager;
import org.shenefelt.Controller.InventoryDatabase;
import org.shenefelt.Controller.TabletTableManager;
import org.shenefelt.DBDemos.DatabaseSeeds;
import org.shenefelt.Model.Computer;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;
import static org.shenefelt.Controller.InventoryDatabase.getConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/Inventory";


    public static void main(String[] args) throws SQLException
    {
//        DatabaseSeeds.seedCompanies();
//        DatabaseSeeds.seedUsers();
//        DatabaseSeeds.seedAll();
        DatabaseSeeds.seedComputers();
        ArrayList<Computer> computers = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader("src/main/java/org/shenefelt/DBDemos/demo_computers.csv")))
        {
            // because these come back as arrays we can loop and create new computer objects
            // by accessing the current records array[]
            List<String[]> records = reader.readAll();

            int i = 1;
            int y= 1;
            for(String[] record : records)
               computers.add(new Computer(record[0], record[1], record[2], record[3], i++ ,y++ ));

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }

        for(Computer computer : computers)
            computer.display();
    }








}