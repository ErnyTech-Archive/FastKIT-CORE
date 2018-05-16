package fastkit.core.fastboot;

import fastkit.Utils;
import fastkit.core.adb.GenericAdb;
import fastkit.util.ExecCmd;
import fastkit.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

import static fastkit.util.Utils.fastboot_bin;

public class BootRecovery extends Utils implements GenericAdb {
    private ExecCmd execCmd;

    public BootRecovery(File recovery) {
        this.execCmd = new ExecCmd(fastboot_bin() + "boot" + sep + recovery);
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
