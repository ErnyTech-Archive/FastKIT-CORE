package fastkit.core.adb;

import fastkit.core.util.ExecCmd;
import fastkit.core.util.GenericBinary;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class Reboot implements GenericAdb {
    private ExecCmd execCmd;

    public Reboot(Mode reboot) {
        switch (reboot) {
            case device: {
                execCmd = new ExecCmd(GenericBinary.getAdb() + "reboot");
                break;
            }
            case recovery: {
                execCmd = new ExecCmd(GenericBinary.getAdb() + "reboot recovery");
                break;
            }
            case fastboot: {
                execCmd = new ExecCmd(GenericBinary.getAdb() + "reboot bootloader");
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
