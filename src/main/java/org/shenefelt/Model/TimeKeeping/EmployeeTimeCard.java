package org.shenefelt.Model.TimeKeeping;

import org.shenefelt.Model.Employee;

import java.util.Objects;

public class EmployeeTimeCard
{
    private int timesheetID;
    private Employee employee;
    private int numHours;
    private double payRate;
    private double totalPay;
    private double overTimePay;
    private int hoursOverForty;
    private final double taxRate = 0.2;
    private double totalVacationPaid;
    private double totalSickTimePaid;


    public EmployeeTimeCard() {}

    public EmployeeTimeCard(Employee u)
    {
        if(u == null)
        {
            employee = new Employee();
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

    /**
     * Employees over time hours
     * @return The remainder of total hours worked - 40
     */
    public int getOverForty()
    {
        if(employee == null || employee.getWorkedHours() < 0)
            return 0;

        return (employee.getWorkedHours() - 40);
    }

    public double getOverTimePay()
    {
        if(numHours < 40)
            return 0.00;

        return (numHours - 40) * employee.getOverTimeRate();
    }




    public double getTotalPay()
    {
        // total pay (-) taxes
        totalPay = (numHours * payRate) * (1 - taxRate);

        return totalPay;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public int getHoursWorked()
    {
        return numHours;
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

        numHours -= hours;
        overTimePay = numHours < 40 ? 0 : (numHours - 40) * employee.getOverTimeRate();
        return true;

    }

    /**
     * Calculate employee pay including any overtime with tax deducted
     * @return the total amount the employee will be paid.
     */
    public double calculateTotalPay()
    {
        if(numHours < 0)
            return 0.00;
        if(payRate < 0)
            return 0.00;
        if(numHours == 0)
            return 0.00;

        totalPay = (numHours * payRate) + calculateOvertime() * (1 - taxRate);
        return totalPay;
    }

    /**
     * Calculate this employee total amount of compensation for overtime.
     * @return the total amount of overtime payment.
     */
    private double calculateOvertime()
    {
        if(numHours < 41)
            return 0.00;

        return (numHours - 40) * employee.getOverTimeRate();
    }

    /**
     * Override the toString method to produce a table for the time card output.
     * @return a String represented by a table.
     */
    @Override
    public String toString()
    {
        String header = employee.getFullName();

        String tableHeader = String.format("%-15s %-10s %-15s %-15s", "Hours Worked", "Pay Rate", "Total Pay", "Overtime Pay");
        String tableContent = String.format("%-15d %-10.2f %-15.2f %-15.2f", numHours, payRate, calculateTotalPay(), getOverTimePay());

        return header + "\n" + tableHeader + "\n" + tableContent;
    }


}
