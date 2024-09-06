package org.shenefelt.Controller.TableMangers;
import org.shenefelt.Controller.InventoryDatabase;
import org.shenefelt.Model.Tablet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabletTableManager
{
    private static final String GET_ALL_TABLETS = "SELECT * FROM Tablets";
    private static final String ADD_TABLET = "INSERT INTO Tablets VALUES(default,?,?,?,?,?,?)";
    private static final ArrayList<Tablet> tablets = new ArrayList<>();

    public TabletTableManager(boolean seed)
    {
        if(seed)
            seedTablets();
    }

    public static boolean addTablet(Tablet tablet)
    {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(ADD_TABLET);
            ps.setString(1, tablet.getAvailability());
            ps.setString(2, tablet.getMake());
            ps.setString(3, tablet.getModel());
            ps.setString(4, tablet.getOwner());
            ps.setInt(5, tablet.getSerialNum());
            ps.setDouble(6, tablet.getPrice());

            return ps.executeUpdate() > 0 && tablets.add(tablet);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static List<Tablet> getAllTablets()
    {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            Tablet temp = new Tablet();
            PreparedStatement ps = conn.prepareStatement(GET_ALL_TABLETS);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                temp.setDbID(rs.getInt("ID"));
                temp.setAvailability(rs.getString("status"));
                temp.setMake(rs.getString("make"));
                temp.setModel(rs.getString("model"));
                temp.setOwner(rs.getString("owner"));
                temp.setSerialNum(rs.getInt("serial_num"));
                temp.setPrice(rs.getDouble("price"));

                tablets.add(temp);

            }


        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return tablets;
    }

    public static boolean seedTablets()
    {
        ArrayList<Tablet> temp = generateTablets();
        tablets.addAll(temp);

        try(Connection conn = InventoryDatabase.getConnection())
        {
            for(Tablet t : temp)
            {
                PreparedStatement ps = conn.prepareStatement(ADD_TABLET);
                ps.setString(1, t.getAvailability());
                ps.setString(2, t.getMake());
                ps.setString(3, t.getModel());
                ps.setString(4, t.getOwner());
                ps.setInt(5, t.getSerialNum());
                ps.setDouble(6, t.getPrice());
                ps.executeUpdate();
            }

            return true;

        } catch(Exception e)
        {
            return false;
        }
    }

    private static ArrayList<Tablet> generateTablets()
    {
        ArrayList<Tablet> tempTablets = new ArrayList<>();
        String avail[] = {"AVAILABLE", "DEPLOYED", "DEPLOYED_WITHOUT_DATA", "EWASTE"};
        String makes[] = {"Samsung Galaxy Tab 8", "iPad Pro 13.5 Inch", "Lenovo Tablet", "iPad Mini"};
        String models[] = {"Inspiron 14", "GPRO", "Gamer", "Gram"};
        String owners[] = {"Greg", "Mariposa", "Ryan", "Dylan"};
        int serialNo[] = {101251,101351,101451,101551};
        double values[] = {399.99,597.36,800.25,1431.25};

        for(int i = 0; i < 4; i++)
        {
            String status = avail[i];
            String make = makes[i];
            String model = models[i];
            String owner = owners[i];
            int serialNum = serialNo[i];
            double price = values[i];

            tempTablets.add(new Tablet(status, make, model, owner, serialNum, price));
        }

        return tempTablets;
    }

}
