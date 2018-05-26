package fastkit.core.adb.autoreboot;

import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;
import fastkit.core.adb.Reboot;
import fastkit.core.adb.WaitBoot;
import fastkit.core.fastboot.Continue;
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
        if (this.fromMode == Mode.recovery) {
            var reboot = new Reboot(Mode.device);
            reboot.exec();
            this.logger.add(reboot);
            var waitBoot = new WaitBoot(Mode.device);
            waitBoot.exec();
            this.logger.add(waitBoot);
        }

        if (this.fromMode == Mode.fastboot) {
            var bootDevice = new Continue();
            bootDevice.exec();
            this.logger.add(bootDevice);
            var waitBoot = new WaitBoot(Mode.device);
            waitBoot.exec();
            this.logger.add(waitBoot);
        }
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }
}
