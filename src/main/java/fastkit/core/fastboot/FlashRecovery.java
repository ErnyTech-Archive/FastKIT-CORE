package fastkit.core.fastboot;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fastkit.core.executor.Executor.fastboot;

public class FlashRecovery implements GenericApi {
    private List<ExecCmd> execCmds = new ArrayList<>();
    private Logger logger = new Logger();

    public FlashRecovery(File recovery) {
        this.execCmds.add(new ExecCmd(fastboot + "erase recovery", this.logger));
        this.execCmds.add(new ExecCmd(fastboot + "flash recovery" + sep + recovery, this.logger));
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        for(ExecCmd execCmd : this.execCmds) {
            execCmd.exec();
        }
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
