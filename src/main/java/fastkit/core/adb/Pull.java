package fastkit.core.adb;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

import static fastkit.core.executor.Executor.adb;

public class Pull implements GenericApi {
    private ExecCmd pull;
    private Logger logger = new Logger();

    public Pull(String serial, String remoteFile, File localFile) {
        this.pull = new ExecCmd(adb + "pull" + sep + remoteFile + sep + localFile, this.logger);
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.pull.exec();
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
