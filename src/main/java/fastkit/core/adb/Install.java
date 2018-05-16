package fastkit.core.adb;

import fastkit.core.util.ExecCmd;
import fastkit.core.util.GenericBinary;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

public class Install implements GenericAdb {
    private ExecCmd execCmd;

    public Install(File localFile) {
        execCmd = new ExecCmd(GenericBinary.getAdb() + "install" + sep + localFile);
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
