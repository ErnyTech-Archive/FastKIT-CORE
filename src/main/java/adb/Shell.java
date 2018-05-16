package fastkit.core.adb;

import fastkit.Utils;
import fastkit.util.ExecCmd;
import fastkit.util.exception.CommandErrorException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static fastkit.util.Utils.adb_bin;

public class Shell extends Utils implements GenericAdb {
    private ExecCmd execCmd;

    public Shell(String command) {
        this.execCmd = new ExecCmd(adb_bin() + "shell" + sep  + command);
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
