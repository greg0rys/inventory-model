package org.shenefelt.Controller;

import org.shenefelt.Constants.AvailStatus;
import org.shenefelt.Model.Computer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

// designed to manage the DB tables that pertain to the computer objects.
public class ComputerTableManager
{
    private static final String GET_ALL_PCS = "SELECT * FROM Computers";
    private static final String ADD_PC = "INSERT INTO Computers VALUES(default,?,?,?,?,?,?)";
    private static final ArrayList<Computer> COMPUTERS = new ArrayList<>();
    private static Connection connection;

    public ComputerTableManager () throws SQLException
    {
        connection = InventoryDatabase.getConnection();
        checkConnection();
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
     * @return List<Computer> ~ the list of all computers as stateful objects
     * @throws SQLException database connectivity errors.
     */
    public static List<Computer> getAllComputers() throws SQLException
    {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(GET_ALL_PCS);
            ResultSet rs = ps.executeQuery(); // we should end up with a whole result set

            while (rs.next())
            {
                AvailStatus status = AvailStatus.valueOf(rs.getString("status"));
                String pcModel = rs.getString("model");
                String pcMake = rs.getString("make");
                String pcOwner = rs.getString("owner");
                String deviceType = rs.getString("type");
                int pcSerialNum = rs.getInt("serial_num");
                int databaseID = rs.getInt("ID");
                COMPUTERS.add(new Computer(databaseID, deviceType,status, pcModel, pcMake, pcOwner, pcSerialNum));
            }
        }
        catch (SQLException e)
        {
            out.println("There was an error: " + e.getMessage());
            throw new SQLException();
        }

        return COMPUTERS;
    }

    public static  boolean addComputer(Computer computer) throws SQLException
    {
        try (Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(ADD_PC);
            ps.setString(1, computer.getDeviceType());
            ps.setString(2, computer.getStatusString());
            ps.setString(3, computer.getMake());
            ps.setString(4, computer.getModel());
            ps.setString(5, computer.getOwner());
            ps.setInt(6, computer.getSerialNum());

            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            out.println("There was an error: " + e.getMessage());
            throw new SQLException();
        }
    }


}
