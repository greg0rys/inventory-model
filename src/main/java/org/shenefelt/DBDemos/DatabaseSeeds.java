package org.shenefelt.DBDemos;

import org.shenefelt.Model.Company;
import org.shenefelt.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.System.out;
import static org.shenefelt.Controller.InventoryDatabase.getConnection;

/**
 * Seed class used to seed the database with fictitious values as placeholders.
 */
public class DatabaseSeeds {
    private static final String INSERT_INTO_USERS ="INSERT INTO Users (first_name, last_name, job_role, company_id) VALUES(?,?,?,?)";
    private static final String INSERT_INTO_COMPANIES ="INSERT INTO Company VALUES(default, ?, ?, ?)";
    private static final String INSERT_INTO_COMPUTERS ="";
    private static final String INSERT_INTO_PRINTERS ="";
    private static final String INSERT_INTO_CELL_PHONES ="";
    private static final ArrayList<User> ALL_DB_USERS = new ArrayList<>();
    private static final ArrayList<Company> ALL_COMPANIES = new ArrayList<>();

    public DatabaseSeeds() { }

    public void seedAll() throws SQLException {
        seedUsers();
        seedCompanies();
        seedComputers();
        seedPrinters();
        seedCellPhones();
    }

    public static boolean seedCompanies() throws SQLException {

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

            return true;

        }
        catch(SQLException e)
        {
            out.println("Unexpected... ");
            throw new SQLException(e.getMessage());
        }
    }

    private static void seedUsers() throws SQLException {
        int[] companyID = { 1, 2, 3, 4 };
        String[] firstNames = {"Greg", "Ryan", "Dylan", "Mariposa"};
        String[] lastNames = {"Gregory", "Ryan", "Dylan", "Mariposa"};
        String[] roles = {"owner", "admin", "user", "guest"};
        try(Connection conn = getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_USERS);
            for (int i = 0; i < firstNames.length; i++) {
                ps.setString(1, firstNames[i]);
                ps.setString(2, lastNames[i]);
                ps.setString(3, roles[i]);
                ps.setInt(4, companyID[i]);
                ps.executeUpdate();
            }
        }
        catch(SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
    }

    private static void seedComputers() {}
    private static void seedPrinters() { }
    private static void seedCellPhones() { }

    private static String[] createDummyUserArray()
    {
        return new String[] {"g"};
    }

    public static void createUsers()
    {
        ArrayList<User> users = new ArrayList<>();
        String[] firstNames = {"Greg", "Ryan", "Dylan", "Mariposa"};
        String[] lastNames = {"Gregory", "Ryan", "Dylan", "Mariposa"};
        int[] cID = {1,2,3,4};
        String[] roles = {"owner", "admin", "user", "guest"};

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
    }
}