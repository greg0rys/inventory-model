package org.shenefelt.DBDemos;

public class DatabaseSeeds {
    private static final String INSERT_INTO_USERS ="INSERT INTO Users VALUES(default,?,?,?,?)";
    private static final String INSERT_INTO_COMPANIES ="";
    private static final String INSERT_INTO_COMPUTERS ="";
    private static final String INSERT_INTO_PRINTERS ="";
    private static final String INSERT_INTO_CELL_PHONES ="";


    public DatabaseSeeds() { }

    public boolean seedAll()
    {
        return true;
    }

    private static void seedUsers() {}
    private static void seedCompanies() {}
    private static void seedComputers() {}
    private static void seedPrinters() { }
    private static void seedCellPhones() { }

    private static String[] createDummyUserArray()
    {
        return new String[] {"g"};
    }

}
