package fastkit.core.adb.autoreboot;

import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;
import fastkit.core.adb.Reboot;
import fastkit.core.adb.WaitBoot;
import fastkit.core.fastboot.FastbootContinue;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class AutoDevice implements GenericApi {
    private Mode fromMode;
    private Logger logger = new Logger();

    public AutoDevice(Mode fromMode) {
        this.fromMode = fromMode;
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        switch (this.fromMode) {
            case recovery: {
                var rebootFromRecovery = new Reboot(Mode.device);
                rebootFromRecovery.exec();
                logger.add(rebootFromRecovery);
                break;
            }
            case fastboot: {
                var rebootFromFastboot = new FastbootContinue();
                rebootFromFastboot.exec();
                logger.add(rebootFromFastboot);
                break;
            }
        }

        var waitBoot = new WaitBoot(Mode.device);
        waitBoot.exec();
        logger.add(waitBoot);
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
