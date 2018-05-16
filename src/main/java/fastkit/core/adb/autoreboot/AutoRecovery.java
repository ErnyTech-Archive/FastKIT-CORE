package fastkit.core.adb.autoreboot;

import fastkit.core.adb.GenericAdb;
import fastkit.core.adb.Mode;
import fastkit.core.adb.Reboot;
import fastkit.core.adb.WaitBoot;
import fastkit.core.fastboot.BootRecovery;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

public class AutoRecovery implements GenericAdb {
    private Mode fromMode;
    private StringBuilder outputs = new StringBuilder();
    private int returnValue = 0;
    private File deviceRecovery;

    public AutoRecovery(Mode fromMode, File deviceRecovery) {
        this.fromMode = fromMode;
        this.deviceRecovery = deviceRecovery;
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        switch (this.fromMode) {
            case device: {
                Reboot rebootToFastboot = new Reboot(Mode.fastboot);
                rebootToFastboot.exec();
                outputs.append(rebootToFastboot.getOutput()).append(System.lineSeparator());
                if (rebootToFastboot.getReturnValue() != 0) {
                    this.returnValue = 1;
                }
                bootRecovery();
                break;
            }
            case fastboot: {
                bootRecovery();
            }
        }
        WaitBoot waitBoot = new WaitBoot(Mode.recovery);
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

    private void bootRecovery() throws InterruptedException, IOException, CommandErrorException {
        BootRecovery bootRecovery = new BootRecovery(this.deviceRecovery);
        bootRecovery.exec();
        outputs.append(bootRecovery.getOutput()).append(System.lineSeparator());
        if (bootRecovery.getReturnValue() != 0) {
            this.returnValue = 1;
        }
    }
}
