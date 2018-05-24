package fastkit.core.adb.autoreboot;

import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;
import fastkit.core.adb.Reboot;
import fastkit.core.adb.WaitBoot;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class AutoFastboot implements GenericApi {
    private Mode fromMode;
    private Logger logger = new Logger();

    public AutoFastboot(Mode fromMode) {
        this.fromMode = fromMode;
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        switch (this.fromMode) {
            case device: {
                var rebootFromDevice = new Reboot(Mode.fastboot);
                rebootFromDevice.exec();
                logger.add(rebootFromDevice);
                break;
            }
            case recovery: {
                var rebootFromRecovery = new Reboot(Mode.fastboot);
                rebootFromRecovery.exec();
                logger.add(rebootFromRecovery);
                break;
            }
        }
        var waitBoot = new WaitBoot(Mode.fastboot);
        waitBoot.exec();
        logger.add(waitBoot);
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
