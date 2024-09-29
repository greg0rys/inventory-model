package org.shenefelt.Model.TimeKeeping;

import org.shenefelt.Model.User;

public class EmployeeTimePunch
{
    private User employee;
    private int numHours;
    private double payRate;
    private double totalPay;
    private final double taxRate = 0.2;


    public EmployeeTimePunch() {}

    public EmployeeTimePunch(User u, int timeWorked, double rateOfPay)
    {
        if(u == null)
            employee = new User();
        else
            employee = u;

        numHours = timeWorked;
        payRate = rateOfPay;

        totalPay = (numHours * payRate) * (1 - taxRate); // full pay (-) taxes
    }

    public String getEmployeeName()
    {
        if(employee == null)
            return "No employee assigned..";

        return "This is the timesheet for: " + employee.getFullName();
    }

    public double getTotalPay()
    {
        // total pay (-) taxes
        totalPay = (numHours * payRate) * (1 - taxRate);

        return totalPay;
    }

    public boolean addWorkedHours(int hours)
    {
        // make sure we don't go negative, make sure we don't work with negative.
        if(hours > numHours || hours < 0)
            return false;

        numHours = hours;
        return true;
    }

    public boolean removeWorkedHours(int hours)
    {
        if(hours > numHours || hours < 0)
            return false;

        numHours = (numHours - hours);
        return true;

    }


}
