package fastkit.core.executor;

import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;

import java.util.ArrayList;
import java.util.List;

public abstract class Executor extends ExecutorUtils implements GenericExecutor {
    Mode mode;
    Thread execThread;
    List<GenericApi> adbApis = new ArrayList<>();
    List<String> outputs = new ArrayList<>();
    List<Integer> returnValues = new ArrayList<>();
    //Generic global tools
    public static String adb = "adb" + " ";
    public static String fastboot = "fastboot" + " ";

    public Executor(Mode mode) {
        this.mode = mode;
    }

    public static void setAdb(String adbpath) {
        Executor.adb = adbpath;
    }

    public static void setFastboot(String fastbootpath) {
        Executor.fastboot = fastbootpath;
    }
}
