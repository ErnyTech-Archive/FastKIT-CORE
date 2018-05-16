package fastkit.core.fastboot;

import fastkit.core.adb.GenericAdb;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.GenericBinary;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

public class BootRecovery implements GenericAdb {
    private ExecCmd execCmd;

    public BootRecovery(File recovery) {
        this.execCmd = new ExecCmd(GenericBinary.getFastboot() + "boot" + sep + recovery);
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.execCmd.exec();
    }

    @Override
    public String getOutput() {
        return this.execCmd.getStdout();
    }

    @Override
    public int getReturnValue() {
        return this.execCmd.getReturnValue();
    }
}
