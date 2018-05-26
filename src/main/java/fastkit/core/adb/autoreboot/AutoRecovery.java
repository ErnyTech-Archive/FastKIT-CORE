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

    public AutoRecovery(Mode fromMode) {
        this.fromMode = fromMode;
    }

    public AutoRecovery(Mode fromMode, File deviceRecovery) {
        this.fromMode = fromMode;
        this.deviceRecovery = deviceRecovery;
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        if (this.deviceRecovery == null) {
            rebootRecobery();
            return;
        }

        if (this.fromMode == Mode.device) {
            AutoReboot rebootFastboot = new AutoReboot(Mode.fastboot, this.fromMode, this.deviceRecovery);
            rebootFastboot.exec();
            this.logger.add(rebootFastboot);
        }

        if (this.fromMode == Mode.device || this.fromMode == Mode.fastboot) {
            var bootRecovery = new BootRecovery(this.deviceRecovery);
            bootRecovery.exec();
            this.logger.add(bootRecovery);
            var waitBoot = new WaitBoot(Mode.recovery);
            waitBoot.exec();
            this.logger.add(waitBoot);
        }
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }

    private void rebootRecobery() throws InterruptedException, IOException, CommandErrorException {
        if (this.fromMode == Mode.fastboot) {
            var rebootToDevice = new AutoReboot(Mode.device, this.fromMode);
            rebootToDevice.exec();
            this.logger.add(rebootToDevice);
        }

        if (this.fromMode == Mode.device || this.fromMode == Mode.fastboot) {
            var rebootToRecovery = new Reboot(Mode.recovery);
            rebootToRecovery.exec();
            this.logger.add(rebootToRecovery);
        }
    }
}
