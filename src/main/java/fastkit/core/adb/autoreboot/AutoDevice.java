package fastkit.core.adb.autoreboot;

import fastkit.core.adb.GenericAdb;
import fastkit.core.adb.Mode;
import fastkit.core.adb.Reboot;
import fastkit.core.adb.WaitBoot;
import fastkit.core.fastboot.FastbootContinue;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class AutoDevice implements GenericAdb {
    private Mode fromMode;
    private GenericAdb reboot;
    private StringBuilder outputs = new StringBuilder();
    private int returnValue = 0;

    public AutoDevice(Mode fromMode) {
        this.fromMode = fromMode;
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        switch (this.fromMode) {
            case recovery: {
                reboot = new Reboot(Mode.device);
                reboot.exec();
                outputs.append(reboot.getOutput()).append(System.lineSeparator());
                if (reboot.getReturnValue() != 0) {
                    this.returnValue = 1;
                }
                break;
            }
            case fastboot: {
                reboot = new FastbootContinue();
                reboot.exec();
                outputs.append(reboot.getOutput()).append(System.lineSeparator());
                if (reboot.getReturnValue() != 0) {
                    this.returnValue = 1;
                }
                break;
            }
        }
        WaitBoot waitBoot = new WaitBoot(Mode.device);
        waitBoot.exec();
        outputs.append(waitBoot.getOutput()).append(System.lineSeparator());
        if (waitBoot.getReturnValue() != 0) {
            this.returnValue = 1;
        }
    }

    @Override
    public String getOutput() {
        return this.outputs.toString();
    }

    @Override
    public int getReturnValue() {
        return this.returnValue;
    }
}
