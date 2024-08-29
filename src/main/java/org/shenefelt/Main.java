package org.shenefelt;

import org.shenefelt.Controller.ComputerTableManager;
import org.shenefelt.Controller.UserTableManager;

import java.sql.SQLException;

import org.shenefelt.Model.User;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/Inventory";


    public static void main(String[] args) throws SQLException
    {
//        DatabaseSeeds.seedCompanies();
//        DatabaseSeeds.seedUsers();
//        DatabaseSeeds.seedComputers();
            update();
//          UserTableManager.addUser(new User("Bad","Gal","owner",1));
//        ArrayList<Computer> computers = new ArrayList<>();
//
//        try(CSVReader reader = new CSVReader(new FileReader("src/main/java/org/shenefelt/DBDemos/demo_computers.csv")))
//        {
//            // because these come back as arrays we can loop and create new computer objects
//            // by accessing the current records array[]
//            List<String[]> records = reader.readAll();
//
//            int i = 1;
//            int y= 1;
//            for(String[] record : records)
//               computers.add(new Computer(record[0], record[1], record[2], record[3], i++ ,y++ ));
//
//        } catch (IOException | CsvException e) {
//            throw new RuntimeException(e);
//        }
//
//        for(Computer computer : computers)
//            computer.display();
    }


    public static void update() throws SQLException {
//        ComputerTableManager.getAllComputers();
//        ComputerTableManager.updateComputerMake(2, "Fag");
        ComputerTableManager.getUserAssignedComputer(1);


    }

}