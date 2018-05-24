package fastkit.core.adb.autoreboot;

import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;
import fastkit.core.adb.Reboot;
import fastkit.core.adb.WaitBoot;
import fastkit.core.fastboot.BootRecovery;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

public class AutoRecovery implements GenericApi {
    private Mode fromMode;
    private Logger logger = new Logger();
    private File deviceRecovery;

    public AutoRecovery(Mode fromMode, File deviceRecovery) {
        this.fromMode = fromMode;
        this.deviceRecovery = deviceRecovery;
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        switch (this.fromMode) {
            case device: {
                var rebootToFastboot = new Reboot(Mode.fastboot);
                rebootToFastboot.exec();
                logger.add(rebootToFastboot);
                bootRecovery();
                break;
            }
            case fastboot: {
                bootRecovery();
            }
        }
        var waitBoot = new WaitBoot(Mode.recovery);
        waitBoot.exec();
        logger.add(waitBoot);
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }

    private void bootRecovery() throws InterruptedException, IOException, CommandErrorException {
        var bootRecovery = new BootRecovery(this.deviceRecovery);
        bootRecovery.exec();
        logger.add(bootRecovery);
    }
}
