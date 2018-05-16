package fastkit.core.adb.autoreboot;

import fastkit.core.adb.GenericAdb;
import fastkit.core.adb.Mode;
import fastkit.core.adb.Reboot;
import fastkit.core.adb.WaitBoot;
import fastkit.core.fastboot.BootRecovery;
import fastkit.core.fastboot.DeviceRecovery;
import fastkit.core.fastboot.FastbootContinue;
import fastkit.util.exception.CommandErrorException;

import java.io.IOException;

public class AutoRecovery implements GenericAdb {
    private Mode fromMode;
    private String deviceModel;
    private StringBuilder outputs = new StringBuilder();
    private int returnValue = 0;

    public AutoRecovery(Mode fromMode, String deviceModel) {
        this.fromMode = fromMode;
        this.deviceModel = deviceModel;
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
        BootRecovery bootRecovery = new BootRecovery(new DeviceRecovery(this.deviceModel).get());
        bootRecovery.exec();
        outputs.append(bootRecovery.getOutput()).append(System.lineSeparator());
        if (bootRecovery.getReturnValue() != 0) {
            this.returnValue = 1;
        }
    }
}
