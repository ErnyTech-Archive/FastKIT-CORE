package fastkit.core.fastboot;

import fastkit.Utils;
import fastkit.core.adb.GenericAdb;
import fastkit.util.ExecCmd;
import fastkit.util.exception.CommandErrorException;

import java.io.IOException;

import static fastkit.util.Utils.fastboot_bin;

public class SetLock extends Utils implements GenericAdb {
    private ExecCmd execCmd;

    public SetLock(LockState lockState, String device) {
        this.execCmd = new ExecCmd(fastboot_bin() + getFastbootCmd(lockState, device));
    }


    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        execCmd.exec();
    }

    @Override
    public String getOutput() {
        return execCmd.getStdout();
    }

    @Override
    public int getReturnValue() {
        return execCmd.getReturnValue();
    }

    private String getFastbootCmd(LockState lockState, String device) {
        switch (lockState) {
            case lock: {
                return "oem lock";
            }
            case unlock: {
                return "oem unlock";
            }
        }
        return null;
    }
}
