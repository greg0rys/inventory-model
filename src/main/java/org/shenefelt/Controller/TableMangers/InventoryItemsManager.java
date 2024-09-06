package org.shenefelt.Controller.TableMangers;

import org.shenefelt.Controller.InventoryDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryItemsManager
{
    private static final String INSERT_NEW_ITEM_INSERT_NEW_ITEM_WITH_USER = "INSERT INTO Items VALUES(default, ?, ?, ?)";
    private static final int DEFAULT_USER_ID = 1;
    private static final int DEFAULT_ITEM_TYPE_ID = 1;
    private static final int DEFAULT_COMPANY_ID = 1;

    public InventoryItemsManager() { }

    public static boolean insertItem(int userID, int itemT, int cID) throws SQLException {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(INSERT_NEW_ITEM_INSERT_NEW_ITEM_WITH_USER);
            ps.setInt(1, itemT);
            ps.setInt(2,userID);
            ps.setInt(3, cID);
            return ps.executeUpdate() > 0;
        }


    }




}
