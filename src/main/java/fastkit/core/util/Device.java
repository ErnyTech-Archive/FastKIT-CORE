package fastkit.core.util;

import fastkit.core.adb.Mode;

public class Device {
    private Mode deviceMode;
    private String deviceSerial;
    private String deviceModel;


    public Device(Mode deviceMode, String deviceSerial, String deviceModel) {
        this.deviceMode = deviceMode;
        this.deviceSerial = deviceSerial;
        this.deviceModel = deviceModel;
    }

    public Mode getDeviceMode() {
        return this.deviceMode;
    }

    public String getDeviceSerial() {
        return this.deviceSerial;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }
}
