package fastkit.core.adb;

import fastkit.core.GenericApi;

public interface GenericAdb extends GenericApi {
    public enum reboot {
        system,
        recovery,
        fastboot;
    }
}
