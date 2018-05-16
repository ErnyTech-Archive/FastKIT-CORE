package fastkit.core.adb;

import fastkit.Utils;
import fastkit.util.ExecCmd;
import fastkit.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static fastkit.util.Utils.adb_bin;

public class Install extends Utils implements GenericAdb {
    private ExecCmd execCmd;

    public Install(File localFile) {
        execCmd = new ExecCmd(adb_bin() + "install" + sep + localFile);
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
