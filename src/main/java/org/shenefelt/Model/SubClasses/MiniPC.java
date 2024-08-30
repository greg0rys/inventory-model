package org.shenefelt.Model.SubClasses;

import org.shenefelt.Helpers.AvailStatus;
import org.shenefelt.Model.Computer;

/**
 * Creating child class to describe different types of computers.
 */
public class MiniPC extends Computer
{
    public MiniPC()
    {
        super();
    }

    public MiniPC(AvailStatus status,
                  String pcModel,
                  String pcMake,
                  String pcOwner,
                  int pcSerialNum) {
    }

    @Override
    public String getDeviceType() {
        return "Mini PC";
    }

}
