package fastkit.core.adb;

import fastkit.Utils;
import fastkit.util.ExecCmd;
import fastkit.util.exception.CommandErrorException;

import java.io.IOException;

import static fastkit.util.Utils.adb_bin;

public class Reboot extends Utils implements GenericAdb {
    private ExecCmd execCmd;

    public Reboot(Mode reboot) {
        switch (reboot) {
            case device: {
                execCmd = new ExecCmd(adb_bin() + "reboot");
                break;
            }
            case recovery: {
                execCmd = new ExecCmd(adb_bin() + "reboot recovery");
                break;
            }
            case fastboot: {
                execCmd = new ExecCmd(adb_bin() + "reboot bootloader");
                break;
            }
        }
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        execCmd.exec();
    }

    @Override
    public String getOutput() {
        return execCmd.getStdout();
    }

    @Override
    public int getReturnValue() {
        return execCmd.getReturnValue();
    }
}
