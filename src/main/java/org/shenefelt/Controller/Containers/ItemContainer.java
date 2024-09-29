package org.shenefelt.Controller.Containers;

import org.shenefelt.Model.*;
import org.shenefelt.Model.InventoryItems.Computer;
import org.shenefelt.Model.InventoryItems.Printer;
import org.shenefelt.Model.InventoryItems.Tablet;

// spell associated

/**
 * Used to hold items from the database, they may have a user they may have all they may have none.
 */
public class ItemContainer
{
    private CellPhone phone;
    private Computer computer;
    private Printer printer;
    private Tablet tablet;
    private User associatedUser;


    public ItemContainer() { }

    public ItemContainer(CellPhone phone, Computer computer,
                         Printer printer, Tablet tablet, User associatedUser)
    {
        this.phone = phone;
        this.computer = computer;
        this.printer = printer;
        this.tablet = tablet;
        this.associatedUser = associatedUser;
    }


    public ItemContainer(CellPhone phone)
    {
        this.phone = phone;
    }

    public ItemContainer(Computer computer)
    {
        this.computer = computer;
    }

    public ItemContainer(Printer printer)
    {
        this.printer = printer;
    }

    public ItemContainer(Tablet tablet)
    {
        this.tablet = tablet;
    }

    public ItemContainer(User associatedUser)
    {
        this.associatedUser = associatedUser;
    }

    public void setPhone(CellPhone phone)
    {
        this.phone = phone;
    }

    public void setComputer(Computer computer)
    {
        this.computer = computer;
    }

    public void setPrinter(Printer printer)
    {
        this.printer = printer;
    }

    public void setTablet(Tablet tablet)
    {
        this.tablet = tablet;
    }

    public void setAssociatedUser(User associatedUser)
    {
        this.associatedUser = associatedUser;
    }

    public CellPhone getPhone()
    {
        return phone;
    }

    public Computer getComputer()
    {
        return computer;
    }

    public Printer getPrinter()
    {
        return printer;
    }

    public Tablet getTablet()
    {
        return tablet;
    }

    public User getAssociatedUser()
    {
        return associatedUser;
    }


    @Override
    public String toString()
    {
        return "hi";
    }

}
