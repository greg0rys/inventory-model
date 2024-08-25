package org.shenefelt.Controller;

import org.shenefelt.Model.Company;
import org.shenefelt.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.System.out;

public class UserTableManager
{
    private static ArrayList<Integer> USER_IDS = new ArrayList<>();
    private static final ArrayList<User> USERS = new ArrayList<>();
    private static final ArrayList<Company> COMPANIES = new ArrayList<>(); // used to get company data.
    private static final String ADD_USER = "INSERT INTO Users VALUES(default,?,?,?,?,?)";
    private static final String ADD_USER_FULL_NAME = "UPDATE Users SET full_name = ? WHERE ID = ?";

    public UserTableManager()
    {

    }

    public static void start() throws SQLException {
        getAllUsers();
        getDatabaseIDS();

        for(User U : USERS)
            out.println(U.display());
    }


    public static void getAllUsers() throws SQLException {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Users");

            while(rs.next())
            {
                int ID = rs.getInt("ID");
                USER_IDS.add(ID);
                USERS.add(new User(
                        ID,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("full_name"),
                        rs.getInt("company_id"),
                        rs.getString("job_role")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void addUser(User U) throws SQLException {
        if(U == null)
            return;

        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(ADD_USER);
            ps.setString(1, U.getFirstName());
            ps.setString(2, U.getLastName());
            ps.setString(3, U.getFullName());
            ps.setInt(4, U.getCompanyID());
            ps.setString(5, U.getJobRole());

            if(ps.executeUpdate() > 0)
            {
                out.println(U.getFullName() + " has been added");
                USERS.add(U);
                updateUserIDS();
            }

        }
    }

    private static void updateUserIDS() throws SQLException {

        if(USER_IDS.isEmpty())
            USER_IDS.add(1);
        else
            USER_IDS.add(USER_IDS.size() - 1 + 1);


    }

    public static void getDatabaseIDS() throws SQLException {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement("SELECT ID FROM Users");
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                USER_IDS.add(rs.getInt("ID"));
            }
        }
    }

    public static ArrayList<Integer> getUserIDs()
    {
        return USER_IDS;
    }

    public static ArrayList<User> getUsers()
    {
        return USERS;
    }
}
