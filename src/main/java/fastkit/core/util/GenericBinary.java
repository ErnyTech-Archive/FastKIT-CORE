package fastkit.core.util;

public class GenericBinary {
    private static String adb;
    private static String fastboot;

    public static void setAdb(GenericOsString genericOsAdb) {
        GenericBinary.adb = genericOsAdb.get() + " ";
    }

    public static void setFastboot(GenericOsString genericOsFastboot) {
        GenericBinary.fastboot = genericOsFastboot.get() + " ";
    }

    public static String getAdb() {
        return GenericBinary.adb;
    }

    public static String getFastboot() {
        return GenericBinary.fastboot;
    }

}
