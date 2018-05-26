package fastkit.core.util;

import java.util.List;

public class BootloaderVars {
    private List<BootloaderVar> bootloaderVars;

    public BootloaderVars(List<BootloaderVar> bootloaderVars) {
        this.bootloaderVars = bootloaderVars;
    }

    public String get(String name) {
        for(BootloaderVar bootloaderVar : this.bootloaderVars) {
            if (bootloaderVar.getName().equals(name)) {
                return bootloaderVar.getValue();
            }
        }
        return null;
    }

    public BootloaderVar getBootloaderVar(String name) {
        for(BootloaderVar bootloaderVar : this.bootloaderVars) {
            if (bootloaderVar.getName().equals(name)) {
                return bootloaderVar;
            }
        }
        return null;
    }
}
