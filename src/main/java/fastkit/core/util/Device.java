package fastkit.core.util;

import fastkit.core.adb.Mode;

public class Device {
    private Mode deviceMode;
    private String deviceSerial;
    private String deviceModel;
    private String deviceProduct;

    public Device(Mode deviceMode, String deviceSerial, String deviceModel, String deviceProduct) {
        this.deviceMode = deviceMode;
        this.deviceSerial = deviceSerial;
        this.deviceModel = deviceModel;
        this.deviceProduct = deviceProduct;
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
    
    public String getDeviceProduct() {
        return this.deviceProduct;
    }
}
