package fastkit.core.util;

public class BootloaderVar {
    private String name;
    private String value;

    public BootloaderVar(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
