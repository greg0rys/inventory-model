package org.shenefelt;

import org.shenefelt.Controller.TableMangers.InventoryItemsManager;
import org.shenefelt.Controller.TableMangers.UserTableManager;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.shenefelt.Model.User;

import static java.lang.System.out;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/Inventory";
    private static final Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws SQLException
    {
        Driver d = new Driver();
        d.start();
//        DatabaseSeeds.seedCompanies();
//        DatabaseSeeds.seedUsers();
//        DatabaseSeeds.seedComputers();
//            update();
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
        out.println("Thanks for stopping by");

//        InventoryItemsManager.insertItem(3,2,1);

    }


    public static void update() throws SQLException {
        User u = UserTableManager.getUser();

        if(u == null)
        {
            out.println("User doesn't exist");
            System.exit(0);
        }


    }

}