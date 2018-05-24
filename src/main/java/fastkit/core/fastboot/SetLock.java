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

    public SetLock(LockState lockState, String device) {
        this.setLock = new ExecCmd(fastboot + getFastbootCmd(lockState, device), this.logger);
    }


    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.setLock.exec();
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }

    private String getFastbootCmd(LockState lockState, String device) {
        switch (lockState) {
            case lock: {
                return "oem lock";
            }
            case unlock: {
                return "oem unlock";
            }
        }
        return null;
    }
}
