package fastkit.core.adb;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

import static fastkit.core.executor.Executor.adb;

public class Install implements GenericApi {
    private ExecCmd install;
    private Logger logger = new Logger();

    public Install(String serial, File localFile) {
        this.install = new ExecCmd(adb + "install" + sep + localFile, this.logger);
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.install.exec();
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
