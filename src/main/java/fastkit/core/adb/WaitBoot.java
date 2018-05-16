package fastkit.core.adb;

import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class WaitBoot implements GenericAdb {
    private Shell shell;
    private Mode waitmode;
    private GetDevices getDevices = new GetDevices();

    public WaitBoot(Mode reboot) {
        this.waitmode = reboot;
        switch (reboot) {
            case device: {
                this.shell = new Shell("getprop | grep sys.boot_completed");
                break;
            }
            case recovery: {
                this.shell = new Shell("mount | grep /data");
                break;
            }
        }
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        switch (this.waitmode) {
            case device: {
                waitSystem();
                break;
            }
            case recovery: {
                waitRecovery();
                break;
            }
            case fastboot: {
                waitFastboot();
                break;
            }
        }
    }

    private void waitSystem() throws InterruptedException, IOException {
        int sys_boot_completed;

        while (true) {
            try {
                this.shell.exec();
            } catch (CommandErrorException e) {
                continue;
            }

            try {
                sys_boot_completed = Integer.parseInt(
                        shell.getOutput()
                                .split(":")[1]
                                .replace("[", "")
                                .replace("]", "")
                                .trim()
                );
            } catch (NumberFormatException e) {
                continue;
            }


            if (sys_boot_completed == 1) {
                break;
            }
        }
    }

    private void waitRecovery() throws IOException, InterruptedException {
        while (true) {
            try {
                this.shell.exec();
            } catch (CommandErrorException e) {
                continue;
            }
            break;
        }
    }

    private void waitFastboot() throws InterruptedException, IOException {
        while (true) {
            try {
                this.getDevices.exec();
            } catch (CommandErrorException e) {
                continue;
            }
            if (this.getDevices.getMode().toString().equals("fastboot")) {
                break;
            }
        }
    }

    @Override
    public String getOutput() {
        switch (waitmode) {
            case device: {
                return this.shell.getOutput();
            }
            case recovery: {
                return this.shell.getOutput();
            }
            case fastboot: {
                return  this.getDevices.getOutput();
            }
        }
        return null;
    }

    @Override
    public int getReturnValue() {
        switch (waitmode) {
            case device: {
                return this.shell.getReturnValue();
            }
            case recovery: {
                return this.shell.getReturnValue();
            }
            case fastboot: {
                return this.getDevices.getReturnValue();
            }
        }
        return -1;
    }
}
