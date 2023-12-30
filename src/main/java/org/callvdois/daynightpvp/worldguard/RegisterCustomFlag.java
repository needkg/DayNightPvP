package org.callvdois.daynightpvp.worldguard;

public class RegisterCustomFlag {

    private final AllowPvpOnDayFlag allowPvpOnDayFlag;

    public RegisterCustomFlag() {
        allowPvpOnDayFlag = new AllowPvpOnDayFlag();
    }

    public void run() {
        allowPvpOnDayFlag.register();
    }

}
