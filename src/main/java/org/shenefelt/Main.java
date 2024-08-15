package org.shenefelt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.out;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/Inventory";
    private static final String SEED_STRING = "INSERT INTO Computers(Make,Model,ProductID) VALUES(?,?,?)";
    private static String username = "flarp";
    private static String password = "Radiokid!!0329";
    static String makes[] = {"Dell", "HP", "Acer", "LG"};
    static String models[] = {"Inspiron 14", "GPRO", "Gamer", "Gram"};
    static int productNums[] = {101351,101451,101551,101651};

    public static void main(String[] args) throws SQLException
    {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        out.println("Seeding the DB");

            try(Connection conn = getConnection())
            {
                String make, model;
                int productno;
                for(int i = 0;  i < productNums.length; i++)
                {
                    make = makes[i];
                    model = models[i];
                    productno = productNums[i];
                    PreparedStatement ps = conn.prepareStatement(SEED_STRING);
                    ps.setString(1, make);
                    ps.setString(2, model);
                    ps.setInt(3, productno);

                    out.println("Comitting " + make + " " + model + " " + productno);
                    ps.executeUpdate();
                }

            }
    }

    private static Connection getConnection() throws SQLException
    {


        return DriverManager.getConnection(DB_URL, username, password);


    }

    private class Computer {
        int ID;
        Long ProductNO;
        String make,model,owner;


        public Computer()
        {}

        public Computer(int ID, Long ProductNO, String make, String model, String owner)
        {
            this.ID = ID;
            this.ProductNO = ProductNO;
            this.make = make;
            this.model = model;
            this.owner = owner;
        }

    }

}