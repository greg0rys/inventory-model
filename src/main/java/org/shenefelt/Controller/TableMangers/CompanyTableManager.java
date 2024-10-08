package org.shenefelt.Controller.TableMangers;

import org.shenefelt.Controller.InventoryDatabase;
import org.shenefelt.Model.Company;
import org.shenefelt.Model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

public class CompanyTableManager
{
    private static final String GET_EMPLOYEE_COUNTS_FOR_ALL = "SELECT Company.ID, Company.company_name, Company.company_location, " +
            "Company.location_alias, COUNT(Users.id) AS user_count\n" +
            "FROM Company\n" +
            "LEFT JOIN Users ON Users.company_id = Company.ID\n" +
            "GROUP BY Company.ID, Company.company_name, Company.company_location, Company.location_alias;";

    private static final String GET_ALL_EMPLOYEES_FOR_COMPANY = "SELECT u.full_name, u.email, u.job_role\n" +
            "FROM Users u\n" +
            "WHERE company_id = ?";

    private static final String GET_FK_USER = "SELECT * FROM Users WHERE company_id = ?";
    private final static String UPDATE_USER_COMPANY_ID_TO_UNASSIGNED = "UPDATE Users set company_id = 6 WHERE " +
            "company_id = (SELECT ID from Company WHERE company_name = ?) AND company_id != 6 AND company_id != 0 AND company_id IS NOT NULL";
    private final static String DELETE_COMPANY = "DELETE FROM Company WHERE company_name = ?";
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final ArrayList<Company> ALL_COMPANIES = new ArrayList<>();


    // default no args constructor.
    public CompanyTableManager () throws SQLException
    {
        ALL_COMPANIES.addAll(getAllCompanies());
    }

    /**
     * Get all companies from the database using a subquery to get a count of their
     * employees as well.
     * @return an ArrayList of all companies
     * @throws SQLException DB Connection ERR
     */
    public static ArrayList<Company> getAllCompanies() throws SQLException
    {

        try(Connection conn = InventoryDatabase.getConnection())
        {
            ResultSet rs = conn.createStatement().executeQuery(GET_EMPLOYEE_COUNTS_FOR_ALL);

            while(rs.next())
            {

                ALL_COMPANIES.add(new Company(
                        rs.getInt("ID"),
                        rs.getString("company_name"),
                        rs.getString("company_location"),
                        rs.getString("location_alias"),
                        rs.getInt("user_count")
                ));



            }

            // load my companies with staff.
            for(Company c : ALL_COMPANIES)
                getStaff(c);

            return ALL_COMPANIES;
        }

    }


    /**
     * Get all staff associated with this company in the database.
     * @param C the company we want staff for.
     */
    private static void getStaff(Company C)
    {
        ArrayList<Employee> employees = new ArrayList<>();
        try(Connection conn = InventoryDatabase.getConnection())
        {
                PreparedStatement ps = conn.prepareStatement(GET_ALL_EMPLOYEES_FOR_COMPANY);
                ps.setInt(1, C.getDbID());
                ResultSet rs = ps.executeQuery();

                while(rs.next())
                    employees.add(new Employee(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("full_name"),
                            rs.getString("job_role"),
                            rs.getString("email")

                    ));

                C.setStaff(employees);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Delete a company from the database.
     * Users have a foreign key in the database for the company's ID. This deletes
     * first finds all users associated with that company and moves them to company 6 (unassigned)
     * <br /><b>Performs user check to ensure they want to delete this cannot be undone.</b>
     * @param companyName the name of the company we wish to delete
     * @return true if a company was removed false if the company removal failed.
     * @throws SQLException DB connection error / deletion error.
     */
    public static boolean deleteCompany(String companyName) throws SQLException
    {
        // ensure we have been provided with a company name.
        if(companyName.isEmpty())
            return false;

        boolean flag = false;

        out.println("This operation cannot be undone, are you sure? (Y/N): ");
        if(new Scanner(System.in).nextLine().equalsIgnoreCase("N"))
        {
            out.println("Operation cancelled..");
            return false;
        }
        else
            out.println("Company removal starting.. Connecting to database");

        try(Connection conn = InventoryDatabase.getConnection())
        {

            if(getCompanyEmployeeCount(companyName) > 0 && removeUsersFromCompany(companyName))
                out.println("All employees moved to unassigned company..");
            else
                out.println("No employees are associated with " + companyName);

            // users are now cleared from fk's delete company
            PreparedStatement ps = conn.prepareStatement(DELETE_COMPANY);
            ps.setString(1, companyName);

            flag = ps.executeUpdate() > 0;


            return flag;
        }
    }


    /**
     * Search for a company by name
     * @param CNAME the name of the company
     * @return true if the company is found false if it is not.
     */
    private static boolean searchForCompany(String CNAME)
    {
        boolean flag = false;

        for(Company c : ALL_COMPANIES)
            flag = c.getCompanyName().equalsIgnoreCase(CNAME);

        if(!flag)
            out.println("This company was not found in the database");

        return flag;
    }

    /**
     * Get the total number of employees a company has
     * @param companyName the name of the company we want the count for
     * @return the number of employees the given company has.
     */
    private static int getCompanyEmployeeCount(String companyName)
    {
        for(Company c : ALL_COMPANIES)
            if(c.getCompanyName().equalsIgnoreCase(companyName))
                return c.getNumberOfEmployees();

        return 0;
    }

    /**
     * Unassign all users employed by a company - this is used when a busines is
     * shut down, or restructured.
     * @param companyName the company we wish to clear staff from
     * @return true if all the employees were unassigned false if they weren't.
     */
    private static boolean removeUsersFromCompany(String companyName)
    {
        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(UPDATE_USER_COMPANY_ID_TO_UNASSIGNED);
            ps.setString(1, companyName);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a list of all users at a company
     * @param companyName the name of the company we want employees from
     * @return an ArrayList containing all known company users.
     */
    public static ArrayList<Employee> getCompanyUsers(String companyName)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        int companyID = getCompanyIDFromName(companyName);
        out.println("CID: " + companyID);
        if(companyID == -1)
            return employees;

        try(Connection conn = InventoryDatabase.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(GET_FK_USER);
            ps.setInt(1, 6);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                out.println(rs.getString("first_name"));
            else
                out.println("Nothing found..");
            while(rs.next())
                employees.add(new Employee(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("job_role"),
                        rs.getInt("company_id")
                ));
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

    /**
     * Get the assigned database ID from a given company name
     * @param companyName the name of the company we need the ID for
     * @return the ID of the company.
     */
    private static int getCompanyIDFromName(String companyName)
    {
        if(companyName.isEmpty())
            return -1;
        // this is always loaded at object init.
        for(Company c : ALL_COMPANIES)
            if(c.getCompanyName().equalsIgnoreCase(companyName))
                return c.getDbID();

        return -1; // no company matches the search.
    }

}
