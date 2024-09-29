package org.shenefelt.Controller.TableMangers;

import org.shenefelt.Model.InventoryItems.Computer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.System.out;
import static org.shenefelt.Controller.InventoryDatabase.getConnection;

// designed to manage the DB tables that pertain to the computer objects.
public class ComputerTableManager
{
    private static final String GET_ALL_PCS = "SELECT * FROM Computers";
    private static final String ADD_PC = "INSERT INTO Computers VALUES(default,?,?,?,?,?,?)";
    private static final String UPDATE_PC_MAKE = "UPDATE Computers SET make = ? WHERE ID = ?";
    private static final String GET_USER_ASSIGNED_EQUIPMENT = "SELECT c.make AS pc_make, c.model AS pc_model, u.first_name AS user_first_name " +
            "FROM Computers c " +
            "JOIN Users u ON c.assigned_user = u.id " +
            "WHERE u.id = ?";
    private static final ArrayList<Computer> COMPUTERS = new ArrayList<>();
    private static Connection connection;

    public ComputerTableManager () throws SQLException
    {
        connection = getConnection();
        checkConnection();
        getAllComputers();
    }

    /**
     * Check the initial connection to ensure that we are communicating.
     */
    private void checkConnection()
    {
        if(connection == null)
            out.println("Error connecting to Inventory database");
        else
            out.println("Connected to Inventory database");

    }

    @Override
    public String toString()
    {
        return "I am the ComputerTableManager";
    }

    /**
     * Get all computers from the db server.
     *
     * @throws SQLException database connectivity errors.
     */
    public static void getAllComputers() throws SQLException
    {
        try(Connection conn = getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(GET_ALL_PCS);
            ResultSet rs = ps.executeQuery(); // we should end up with a whole result set

            while (rs.next())
            {
                String status = rs.getString("availability");
                String pcModel = rs.getString("model");
                String pcMake = rs.getString("make");
                String pcOwner = rs.getString("company_id");
                String deviceType = rs.getString("type");
                int pcSerialNum = rs.getInt("serial_number");
                int databaseID = rs.getInt("ID");
                int CID = rs.getInt("company_id");
                int UID = rs.getInt("assigned_user");
               COMPUTERS.add(new Computer(databaseID, deviceType,status,
                       pcModel, pcMake,
                       pcOwner, pcSerialNum,
                       CID, UID));
            }
        }
        catch (SQLException e)
        {
            out.println("There was an error: " + e.getMessage());
            throw new SQLException();
        }

    }

    public static  boolean addComputer(Computer computer) throws SQLException
    {
        try (Connection conn = getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(ADD_PC);
            ps.setString(1, computer.getDeviceType());
            ps.setString(2, computer.getStatusString());
            ps.setString(3, computer.getMake());
            ps.setString(4, computer.getModel());
            ps.setString(5, computer.getOwner());

            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            out.println("There was an error: " + e.getMessage());
            throw new SQLException();
        }
    }

    /**
     * Update the make of a PC in the database
     * @param id the ID of the item we want to update.
     * @return true if updated, false if failed.
     */
    public static boolean updateComputerMake(int id, String newMake) throws SQLException
    {
        int idx = (id - 1); // arraylist starts @ 0.

        // this all works because the arraylist should have the Database ID - 1 for its storage index.
        // static structure needs to be loaded.
        if(COMPUTERS.isEmpty())
            getAllComputers();
        // provided ID cannot be < 0 as 0 = the first item in any array struct
        if(idx < 0)
            return false;
        // if ID is larger than array size return false.
        if(idx >= COMPUTERS.size())
            return false;




        try(Connection conn = getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(UPDATE_PC_MAKE);
            ps.setString(1, newMake);
            ps.setInt(2, id);


            if(ps.executeUpdate() > 0)
            {
                Computer temp = COMPUTERS.get((id - 1));
                out.println("Updated the device: " + temp.getMake() + " " + temp.getDbID() + " new make: " + newMake);
                COMPUTERS.get(idx).setMake(newMake); // update local struct.
                return true;
            }

            return false;
        }

    }

    public static void displayAll() throws SQLException
    {
        if(COMPUTERS.isEmpty())
            getAllComputers();

        for(Computer c : COMPUTERS)
            c.display();

    }


    public static void getUserAssignedComputer(int userID) throws SQLException {
        try(Connection conn = getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(GET_USER_ASSIGNED_EQUIPMENT);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.printf("User: %s\n\tMake: %s\n\tModel: %s", rs.getString("user_first_name"), rs.getString("pc_make"), rs.getString("pc_model"));
            } else {
                out.println("No computer found for the given user.");
            }
        }
    }

}
