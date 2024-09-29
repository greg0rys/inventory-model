package org.shenefelt.Model.InventoryItems;

// super class for other phone types
public class Phone
{
    protected String type, make, model, serial;
    protected long phoneNumber; // the program will need to parse these into strings.
    protected int extensionCost;
    protected double cost;

    public Phone() { } // no args.

    public Phone(String T, String M, String MOD, String SN)
    {
       type = T;
       make = M;
       model = MOD;
       serial = SN;
    }

    public void setType(String T) { type = T; }
    public void setMake(String M) { make = M; }
    public void setModel(String m) { model = m; }
    public void setSerial(String s) { serial = s; }
    public void setPhoneNumber(long n) { phoneNumber = n; }
    public void setExtensionCost(int c) { extensionCost = c; }
    public void setCost(double c) { cost = c; }

    public String getType() { return type; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getSerial() { return serial; }
    public long getPhoneNumber() { return phoneNumber; }
    public int getExtensionCost() { return extensionCost; }
    public double getCost() { return cost; }

    public String parsePhoneNumber() {
        String output = "";
        int counter = 1;

        long tempPhoneNumber = phoneNumber; // Work with a temporary copy of phoneNumber

        while(counter <= 10) {
            output = (tempPhoneNumber % 10) + output; // Extract last digit and prepend to output
            tempPhoneNumber /= 10; // Remove last digit from tempPhoneNumber
            counter++; // Increment counter
        }

        return output;
    }

}
