package fastkit.core.adb;

import fastkit.core.util.ExecCmd;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

import static fastkit.core.Executor.adb;

public class Pull implements GenericAdb {
    private ExecCmd execCmd;

    public Pull(String remoteFile, File localFile) {
        execCmd = new ExecCmd(adb + "pull" + sep + remoteFile + sep + localFile);
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
