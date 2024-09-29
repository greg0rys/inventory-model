/**
 * @publisher Greg Shenefelt - @greg0rys
 */
package org.shenefelt.Controller.TableMangers;

import org.shenefelt.Controller.InventoryDatabase;
import org.shenefelt.Helpers.InputValidator;
import org.shenefelt.Model.Company;
import org.shenefelt.Model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.lang.System.out;

public class UserTableManager
{
    private static ArrayList<Integer> USER_IDS = new ArrayList<>();
    private static final ArrayList<Employee> EMPLOYEES = new ArrayList<>();
    private static final ArrayList<Company> COMPANIES = new ArrayList<>(); // used to get company data.
    private static final String ADD_USER = "INSERT INTO Users VALUES(default,?,?,?,?,?,?,?,?,?)";
    private static final String ADD_USER_FULL_NAME = "UPDATE Users SET full_name = ? WHERE ID = ?";
    private static final String GET_USER_IDS = "SELECT ID FROM Users";
    private static final String UPDATE_USER_FULL_NAME = "UPDATE Users SET full_name = ? WHERE ID = ?";
    private static final String UPDATE_USER_JOB_ROLE = "UPDATE Users SET job_role = ? WHERE ID = ?";
    private static final String UPDATE_USER_COMPANY = "UPDATE Users SET company_id = ? WHERE ID = ?";
    private static final String UPDATE_USER_USERNAME = "UPDATE Users SET username = ? WHERE ID = ?";
    private static final String UPDATE_USER_EMAILS = "UPDATE Users SET email = CONCAT(username, '@gregoryshenefelt.com')";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String UPDATE_HIRE_STATUS = "UPDATE Users SET hire_status = ? WHERE ID = ?";
    private static final Logger LOGGER = Logger.getLogger(UserTableManager.class.getName());

    // default no args due to static class
    public UserTableManager() {    }


    /**
     * Get all users from the database and load the local storage structures.
     * @throws SQLException error with database query or connection.
     */
    public static void getAllUsers() throws SQLException
    {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Users");

            while(rs.next())
            {
                int ID = rs.getInt("ID");
                USER_IDS.add(ID);
                EMPLOYEES.add(new Employee(
                        ID,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("full_name"),
                        rs.getInt("company_id"),
                        rs.getString("job_role"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("hire_status")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Add a new user to the local structure and the database.
     * @param U the user we want to add.
     * @throws SQLException error with database query or connection.
     */
    public static boolean addUser(Employee U) throws SQLException
    {
        if(U == null)
            return false;

        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(ADD_USER);
            ps.setString(1, U.getFirstName());
            ps.setString(2, U.getLastName());
            ps.setString(3, U.getFullName());
            ps.setInt(4, U.getCompanyID());
            ps.setString(5, U.getJobRole());
            ps.setString(6,U.generateUserName());
            ps.setString(7,U.getEmail());
            ps.setInt(8, 1);
            ps.setInt(9, 0);


            if(ps.executeUpdate() > 0)
            {
                out.println(U.getFullName() + " has been added");
                U.setUserID((USER_IDS.size() - 1) + 1);
                if(EMPLOYEES.add(U))
                    LOGGER.info(U.getFullName() + " has been added to the database.");
                return true;
            }

        }

        LOGGER.warning(U.getFullName() + " was not added to the database");
        return false;
    }


    /**
     * Get the local structure that is holding the user id's
     * @return an ArrayList of all user ID's
     */
    public static ArrayList<Integer> getUserIDs()
    {
        return USER_IDS;
    }

    /**
     * Get the local structure that is holding the user objects
     * @return an array list of users.
     */
    public static ArrayList<Employee> getUsers() throws SQLException {
        if(EMPLOYEES.isEmpty())
            getAllUsers();
        return EMPLOYEES;
    }

    /**
     * Select a given user ID to get the matching user object from the structures
     * @return the Employee we are looking for or null
     * @throws SQLException error with database query or connection.
     */
    public static Employee getUser() throws SQLException
    {
        if(EMPLOYEES.isEmpty() || USER_IDS.isEmpty())
            getAllUsers();
        int choice =  selectUserID();
        // make sure the user exists either here in the local array list or the DB. if the ladder add user to list.
        if(!userInList(choice))
            return null;

        return EMPLOYEES.get(choice - 1);
    }

    /**
     * Check for this user in both the static list and the database if needed.
     * @param userID the ID of the user we are looking for.
     * @return true if the user is in the list false if else.
     * @throws SQLException error with database query
     */
    public static boolean userInList(int userID) throws SQLException
    {
        if(EMPLOYEES.isEmpty())
            return false;


        for(Employee U : EMPLOYEES)
        {
            if(U.getUserID() == userID)
                return true;
        }

        return checkDBForUser(userID);
    }

    /**
     * If we do not see the user in the static array list, we will search the database for this user
     * if found we will add it to the static structure
     * @param userID the id of the user.
     * @return true if the user was found and added
     * @throws SQLException error with database query.
     */
    private static boolean checkDBForUser(int userID) throws SQLException
    {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            Employee temp = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE ID = ?");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                EMPLOYEES.add(new Employee(rs.getInt("ID"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("full_name"),
                        rs.getInt("company_id"), rs.getString("job_role")));

                return true;

            }

        }
        return false;
    }

    /**
     * Display all known user id's and the user who is associated with them to allow the user to select
     * the input user.
     * @return the database id of the user we want.
     * @throws SQLException error with database query
     */
    public static int selectUserID() throws SQLException {
        if(EMPLOYEES.isEmpty() || USER_IDS.isEmpty())
            getAllUsers();
        for(Employee U : EMPLOYEES)
            U.displayNameWithID();
        out.println("Please enter a user id from above: ");
        int choice = SCANNER.nextInt();

        return USER_IDS.contains(choice) ? choice : -1;
    }

    /**
     * Update the users full name in the database -- this should also trigger the first and last names to be split
     * @return true if we updated the user.
     * @throws SQLException there was an error with the database.
     */
    public static boolean updateUserFullName(Employee U) throws SQLException {
        String newName;
        int userID;
        Employee temp;

        out.println("Please enter the new full name: ");
        newName = SCANNER.next();
        out.println("Please enter the user ID: ");
        userID = selectUserID();
        temp = EMPLOYEES.get(userID - 1);

        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(UPDATE_USER_FULL_NAME);
            ps.setString(1, newName);
            ps.setInt(2, userID);

            if(ps.executeUpdate() > 0)
            {
                temp.setFullName(newName);

                return true;
            }
        }

        return false;
    }


    /**
     * Manually update a users database username.
     * @param userID
     * @param newName
     * @return
     * @throws SQLException
     */
    public static boolean updateUsernameAndEmail(int userID, String newName)
            throws SQLException
    {

        if(!InputValidator.validateUserSelectionByID(userID))
            return false;


        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(UPDATE_USER_USERNAME);
            PreparedStatement ps2 = conn.prepareStatement(UPDATE_USER_EMAILS);
            ps.setString(1, newName);
            ps.setInt(2, userID);

            return ps.executeUpdate() > 0 && ps2.executeUpdate() > 0;
        }
    }

    /**
     * Update a users hire status in the database.
     * @param dbID
     * @param hireStatus
     * @return
     * @throws SQLException
     */
    public static boolean updateHireStatus(int dbID, int hireStatus) throws SQLException
    {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(UPDATE_HIRE_STATUS);
            ps.setInt(1, hireStatus);
            ps.setInt(2, dbID);

            return ps.executeUpdate() > 0;
        }

    }


}
