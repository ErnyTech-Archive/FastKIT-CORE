package fastkit.core.fastboot;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

import static fastkit.core.executor.Executor.fastboot;

public class SetLock implements GenericApi {
    private ExecCmd setLock;
    Logger logger = new Logger();

    public SetLock(LockState lockState) {
        this.setLock = new ExecCmd(fastboot +  "oem" + sep + lockState, this.logger);
    }

    public SetLock(LockState lockState, String lockcode) {
        this.setLock = new ExecCmd(fastboot +  "oem" + sep + lockState + sep + lockcode, this.logger);
    }


    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.setLock.exec();
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
