package org.shenefelt.Controller.TableMangers;

import org.shenefelt.Controller.InventoryDatabase;
import org.shenefelt.Model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.System.out;

public class CompanyTableManager
{
    private static final String SELECT_ALL_COMPANIES = "SELECT * FROM Company";
    private static final String GET_NUMBER_EMPLOYEES_ALL_COMPANIES = "SELECT Company.company_name, COUNT(Users.id) AS user_count\n" +
            "FROM Company\n" +
            "         LEFT JOIN Users ON Users.company_id = Company.ID\n" +
            "GROUP BY Company.company_name;";
    private static final ArrayList<Company> ALL_COMPANIES = new ArrayList<>();
    public CompanyTableManager () throws SQLException
    {
        ALL_COMPANIES.addAll(getAllCompanies());
    }

    public static ArrayList<Company> getAllCompanies() throws SQLException
    {

        try(Connection conn = InventoryDatabase.getConnection())
        {
            ArrayList<Company> all_companies = new ArrayList<>();
            ResultSet rs = conn.createStatement().executeQuery(SELECT_ALL_COMPANIES);

            while(rs.next())
            {
                all_companies.add(new Company(
                        rs.getInt("ID"),
                        rs.getString("company_name"),
                        rs.getString("company_location"),
                        rs.getString("location_alias")
                ));
            }

            return all_companies;
        }



    }

    public static void displayAllCompanyEmployeeCounts() throws SQLException {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(GET_NUMBER_EMPLOYEES_ALL_COMPANIES);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                out.println(rs.getString("company_name") + " has " + rs.getInt("user_count") + " employees");
            }
        }
    }

}
