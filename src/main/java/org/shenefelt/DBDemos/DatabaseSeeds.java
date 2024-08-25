package org.shenefelt.DBDemos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.shenefelt.Model.Company;
import org.shenefelt.Model.Computer;
import org.shenefelt.Model.User;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static org.shenefelt.Controller.InventoryDatabase.getConnection;

/**
 * Seed class used to seed the database with fictitious values as placeholders.
 */
public class DatabaseSeeds {
    private static final String INSERT_INTO_USERS ="INSERT INTO Users (first_name, last_name, job_role, company_id) VALUES(?,?,?,?)";
    private static final String INSERT_INTO_COMPANIES ="INSERT INTO Company VALUES(default, ?, ?, ?)";
    private static final String INSERT_INTO_COMPUTERS ="INSERT INTO Computers VALUES(default,?,?,?,?,?,?)";
    private static final String INSERT_INTO_PRINTERS ="";
    private static final String INSERT_INTO_CELL_PHONES ="";
    private static final ArrayList<User> ALL_DB_USERS = new ArrayList<>();
    private static final ArrayList<Company> ALL_COMPANIES = new ArrayList<>();
    private static boolean seededUsers,seededCompanies,seededComputers,seededPrinters,seededCellPhones;

    public DatabaseSeeds() { }

    public static void seedAll() throws SQLException {
        seedUsers();
        seedCompanies();
        seedComputers();
        seedPrinters();
        seedCellPhones();
    }

    public static void seedCompanies() throws SQLException
    {

        if(seededCompanies)
        {
            out.println("Companies already seeded");
            return;
        }

        String[] companies = { "Medicalodges", "Pace", "Fred Meyer", "Walmart" };
        String[] companyLocations = { "Portland, Oregon", "San Antonio, Texas", "Hillsboro, Oregon", "Beaverton, Oregon" };
        String[] locationAlias = { "97239", "78240", "97123", "97005" };
        try(Connection conn = getConnection())
        {
            int idx = 0; // outside tracker to contain -- I want one loop
            for(String s : companies)
            {
                PreparedStatement ps = conn.prepareStatement(INSERT_INTO_COMPANIES);
                ps.setString(1, s);
                ps.setString(2, companyLocations[idx]);
                ps.setString(3, locationAlias[idx]);
                ps.executeUpdate();
                idx++;
            }

        }

        seededCompanies = true;
    }
    public static void seedUsers()
    {
        if(seededUsers)
        {
            out.println("Users already seeded");
            return;
        }

        String[] firstNames = {"Greg", "Ryan", "Dylan", "Mariposa"};
        String[] lastNames = {"Gregory", "Ryan", "Dylan", "Mariposa"};
        String[] roles = {"owner", "admin", "user", "guest"};

        int[] cID = {1,2,3,4};
        int idx = 0;
        for(String f : firstNames)
        {
            try(Connection conn = getConnection()) {
                PreparedStatement ps = conn.prepareStatement(INSERT_INTO_USERS);
                ps.setString(1, f);
                ps.setString(2, lastNames[idx]);
                ps.setString(3, roles[idx]);
                ps.setInt(4, cID[idx]);
                ps.executeUpdate();
                idx++;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        seededUsers = true;
    }

    public static void seedComputers()
    {
        ArrayList<Computer> computers = new ArrayList<>();


        try(Connection conn = getConnection();CSVReader reader = new CSVReader(new FileReader("src/main/java/org/shenefelt/DBDemos/demo_computers.csv"))) {
            List<String[]> records = reader.readAll();
            int i = 1;
            int y = 1;

            for(String[] record : records)
            {
                PreparedStatement ps = conn.prepareStatement(INSERT_INTO_COMPUTERS);
                ps.setString(1, record[0]);
                ps.setString(2, record[1]);
                ps.setString(3, record[2]);
                ps.setString(4, record[3]);
                ps.setInt(5, i);
                ps.setInt(6, y);
                ps.executeUpdate();
                i++;
                y++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }

    }
    private static void seedPrinters() { }
    private static void seedCellPhones() { }



}