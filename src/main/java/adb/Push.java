package fastkit.core.adb;

import fastkit.Utils;
import fastkit.util.ExecCmd;
import fastkit.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

import static fastkit.util.Utils.adb_bin;

public class Push extends Utils implements GenericAdb {
    private ExecCmd execCmd;

    public Push(File localFile, String remoteFile) {
        execCmd = new ExecCmd(adb_bin() + "push" + sep + localFile + sep + remoteFile);
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
