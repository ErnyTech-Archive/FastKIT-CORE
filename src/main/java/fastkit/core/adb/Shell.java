package fastkit.core.adb;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

import static fastkit.core.executor.Executor.adb;

public class Shell implements GenericApi {
    private ExecCmd shell;
    private Logger logger = new Logger();

    public Shell(String command) {
        this.shell = new ExecCmd(adb + "shell" + sep  + command, this.logger);
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.shell.exec();
    }

    public String getOutput() {
        return this.shell.getStdout();
    }

    public int getReturnValue() {
        return this.shell.getReturnValue();
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
