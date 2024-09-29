package org.shenefelt.Model.TimeKeeping;

import org.shenefelt.Model.User;

import java.util.Objects;

public class EmployeeTimeCard
{
    private User employee;
    private int numHours;
    private double payRate;
    private double totalPay;
    private double overTimePay;
    private int hoursOverForty;
    private final double taxRate = 0.2;
    private double totalVacationPaid;
    private double totalSickTimePaid;


    public EmployeeTimeCard() {}

    public EmployeeTimeCard(User u)
    {
        if(u == null)
        {
            employee = new User();
            employee.setHoursWorked(0);
        }
        else {
            employee = u;
        }

        numHours = Objects.requireNonNull(u).getWorkedHours();
        payRate = u.getPayRate();
        hoursOverForty = getOverForty();

        totalPay = (numHours * payRate) * (1 - taxRate); // full pay (-) taxes
    }

    public int getOverForty()
    {
        if(employee == null || employee.getWorkedHours() < 0)
            return 0;

        return (employee.getWorkedHours() - 40);
    }

    public double getOverTimePay()
    {
        if(numHours < 40 || numHours == 0)
            return 0.00;

        return (numHours - 40) * employee.getOverTimeRate();
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

        if(numHours + hours > 40)
            overTimePay = (numHours + hours) * employee.getOverTimeRate();

        numHours = hours;
        employee.setHoursWorked(numHours);
        return true;
    }

    public boolean removeWorkedHours(int hours)

    {

        if(hours > numHours || hours < 0)
            return false;

        numHours = (numHours - hours);
        overTimePay = numHours < 40 ? 0 : (numHours - 40) * employee.getOverTimeRate();
        return true;

    }

    public double calculateTotalPay()
    {
        if(numHours < 0)
            return 0.00;
        if(payRate < 0)
            return 0.00;
        if(numHours == 0)
            return 0.00;
        if(taxRate < 0)
            return 0.00;
        if(numHours > 40)
            overTimePay = (numHours - 40) * employee.getOverTimeRate();

        totalPay = (numHours * payRate) * (1 - taxRate);
        return totalPay;
    }

    @Override
    public String toString()
    {
        return "Employee: " + employee.getFullName() + "\nHours worked: " + numHours + "\nPay Rate: "
                + payRate + "\nTotal Pay: " + calculateTotalPay();
    }


}
