package org.shenefelt.Controller.Managers;

import org.shenefelt.Controller.TableMangers.CompanyTableManager;
import org.shenefelt.Model.Company;
import org.shenefelt.Model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.System.out;

public class CompanyManager
{
    private final ArrayList<Company> ALL_COMPANIES = new ArrayList<>();

    public CompanyManager() throws SQLException
    {
        ALL_COMPANIES.addAll(CompanyTableManager.getAllCompanies());
    }

    public Company getCompany(int idx)
    {
        if(idx < 0 || idx >= ALL_COMPANIES.size())
            return null;

        return ALL_COMPANIES.get(idx);
    }

    public int getTotalNumberOfCompanies()
    {
        return ALL_COMPANIES.size();
    }

    /**
     * Display all companies from the database.
     */
    public void displayAllCompanies()
    {
        // make sure ALL_Companies isn't empty
        if(ALL_COMPANIES.isEmpty())
            return;

        for(Company c : ALL_COMPANIES)
            out.println(c);
    }


    public void displayAllStaff()
    {
        for(Company c : ALL_COMPANIES)
            for(Employee u : c.getStaff())
            {
                out.println("Displaying employees for " + c.getCompanyName() + "\n");
                out.println(u.getFullName());

            }
    }








    
}
