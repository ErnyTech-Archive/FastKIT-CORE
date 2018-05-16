package fastkit.core.fastboot;

import fastkit.Utils;
import fastkit.core.adb.GenericAdb;
import fastkit.util.ExecCmd;
import fastkit.util.exception.CommandErrorException;

import java.io.IOException;

import static fastkit.util.Utils.fastboot_bin;

public class FastbootContinue extends Utils implements GenericAdb {
    private ExecCmd execCmd;

    public FastbootContinue() {
        this.execCmd = new ExecCmd(fastboot_bin() + "continue");
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.execCmd.exec();
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
