package fastkit.core.adb.autoreboot;

import fastkit.core.adb.GenericAdb;
import fastkit.core.adb.Mode;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

public class AutoReboot implements GenericAdb {
    private Mode toMode;
    private Mode fromMode;
    private File deviceRecovery;
    private GenericAdb reboot;

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
                this.reboot = new AutoDevice(this.fromMode);
                this.reboot.exec();
                break;
            }
            case recovery: {
                this.reboot = new AutoRecovery(this.fromMode, this.deviceRecovery);
                this.reboot.exec();
                break;
            }
            case fastboot: {
                this.reboot = new AutoFastboot(this.fromMode);
                this.reboot.exec();
                break;
            }
        }
    }

    @Override
    public String getOutput() {
        return this.reboot.getOutput();
    }

    @Override
    public int getReturnValue() {
        return this.reboot.getReturnValue();
    }
}
