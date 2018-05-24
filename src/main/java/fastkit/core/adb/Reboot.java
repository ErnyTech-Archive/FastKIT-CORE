package fastkit.core.adb;

import fastkit.core.GenericApi;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

import static fastkit.core.executor.Executor.adb;

public class Reboot implements GenericApi {
    private Mode reboot;
    private Logger logger = new Logger();

    public Reboot(Mode reboot) {
        this.reboot = reboot;
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        switch (this.reboot) {
            case device: {
                var rebootDevice = new ExecCmd(adb + "reboot", this.logger);
                rebootDevice.exec();
                break;
            }
            case recovery: {
                var rebootRecovery = new ExecCmd(adb + "reboot recovery", this.logger);
                rebootRecovery.exec();
                break;
            }
            case fastboot: {
                var rebootFastboot = new ExecCmd(adb + "reboot bootloader", this.logger);
                rebootFastboot.exec();
                break;
            }
        }
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
