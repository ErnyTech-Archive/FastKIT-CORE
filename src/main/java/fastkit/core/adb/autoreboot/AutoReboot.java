package fastkit.core.adb.autoreboot;

import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

public class AutoReboot implements GenericApi {
    private Mode toMode;
    private Mode fromMode;
    private File deviceRecovery;
    private Logger logger = new Logger();

    public AutoReboot(Mode toMode, Mode fromMode, File deviceRecovery) {
        this.toMode = toMode;
        this.fromMode = fromMode;
        this.deviceRecovery = deviceRecovery;
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        if (this.toMode == this.fromMode) {
            return;
        }

        switch (this.toMode) {
            case device: {
                var rebootToDevice = new AutoDevice(this.fromMode);
                rebootToDevice.exec();
                logger.add(rebootToDevice);
                break;
            }
            case recovery: {
                var rebootToRecovery = new AutoRecovery(this.fromMode, this.deviceRecovery);
                rebootToRecovery.exec();
                logger.add(rebootToRecovery);
                break;
            }
            case fastboot: {
                var rebootToFastboot = new AutoFastboot(this.fromMode);
                rebootToFastboot.exec();
                logger.add(rebootToFastboot);
                break;
            }
        }
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }


}
