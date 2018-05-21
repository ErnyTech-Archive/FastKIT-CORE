package fastkit.core.adb;

import fastkit.core.util.ExecCmd;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

import static fastkit.core.Executor.adb;

public class Shell implements GenericAdb {
    private ExecCmd execCmd;

    public Shell(String command) {
        this.execCmd = new ExecCmd(adb + "shell" + sep  + command);
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
