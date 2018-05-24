package fastkit.core.adb;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

import static fastkit.core.executor.Executor.adb;

public class Push implements GenericApi {
    private ExecCmd push;
    private Logger logger = new Logger();

    public Push(String serial, File localFile, String remoteFile) {
        this.push = new ExecCmd(adb + "push" + sep + localFile + sep + remoteFile, this.logger);
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.push.exec();
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
