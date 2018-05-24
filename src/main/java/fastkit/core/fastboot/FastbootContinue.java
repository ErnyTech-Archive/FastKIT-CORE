package fastkit.core.fastboot;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

import static fastkit.core.executor.Executor.fastboot;

public class FastbootContinue implements GenericApi {
    private Logger logger = new Logger();

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        var fastbootContinue = new ExecCmd(fastboot + "continue", this.logger);
        fastbootContinue.exec();
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
