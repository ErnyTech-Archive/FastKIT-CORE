package fastkit.core.adb;

import fastkit.core.util.ExecCmd;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

import static fastkit.core.Executor.adb;

public class Push implements GenericAdb {
    private ExecCmd execCmd;

    public Push(File localFile, String remoteFile) {
        execCmd = new ExecCmd(adb + "push" + sep + localFile + sep + remoteFile);
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
