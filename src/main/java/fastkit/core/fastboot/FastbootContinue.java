package fastkit.core.fastboot;

import fastkit.core.adb.GenericAdb;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.GenericBinary;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class FastbootContinue implements GenericAdb {
    private ExecCmd execCmd;

    public FastbootContinue() {
        this.execCmd = new ExecCmd(GenericBinary.getFastboot() + "continue");
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
