package org.callvdois.daynightpvp.worldguard;

public class RegisterCustomFlags {

    private final CustomFlag customFlag;

    public RegisterCustomFlags() {
        customFlag = new CustomFlag();
    }

    public void run() {
        customFlag.register();
    }

}
