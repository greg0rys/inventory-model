package org.shenefelt.DBDemos;

public class SeedResult
{
    // init first to set value regardless of initial,
    private boolean success = false;
    private String message = " ";

    public SeedResult() { }

    public SeedResult(boolean S, String M)
    {
        success = S;
        message = M;
    }

    public boolean hasResults()
    {
        return !success || message.equals(" ");
    }

    public boolean getSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }
}
