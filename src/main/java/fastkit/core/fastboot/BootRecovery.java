package fastkit.core.fastboot;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

import static fastkit.core.executor.Executor.fastboot;

public class BootRecovery implements GenericApi {
    private ExecCmd bootRecovery;
    private Logger logger = new Logger();

    public BootRecovery(File recovery) {
        this.bootRecovery = new ExecCmd(fastboot + "boot" + sep + recovery, this.logger);
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        this.bootRecovery.exec();
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
