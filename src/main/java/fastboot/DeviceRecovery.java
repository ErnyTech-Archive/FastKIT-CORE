package fastkit.core.fastboot;

import java.io.File;

public class DeviceRecovery {
    private String deviceModel;

    public DeviceRecovery(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public File get() {
        return new File("");
    }
}
