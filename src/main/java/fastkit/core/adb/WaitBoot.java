package fastkit.core.adb;

import fastkit.core.GenericApi;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public class WaitBoot implements GenericApi {
    private Mode waitmode;
    private Logger logger = new Logger();

    public WaitBoot(Mode reboot) {
        this.waitmode = reboot;
    }

    @Override
    public void exec() throws InterruptedException, IOException {
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

    @Override
    public Logger getLog() {
        return this.logger;
    }

    private void waitSystem() throws InterruptedException, IOException {
        int sys_boot_completed;
        var waitSystem = new Shell("getprop | grep sys.boot_completed");

        while (true) {
            try {
                waitSystem.exec();
            } catch (CommandErrorException e) {
                continue;
            } finally {
                this.logger.add(waitSystem.getOutput());
                this.logger.add(waitSystem.getReturnValue());
            }

            try {
                sys_boot_completed = Integer.parseInt(
                        waitSystem.getOutput()
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
        var waitRecovery = new Shell("mount | grep /data");
        while (true) {
            try {
                waitRecovery.exec();
                this.logger.add(waitRecovery);
            } catch (CommandErrorException e) {
                continue;
            } finally {
                this.logger.add(waitRecovery.getOutput());
                this.logger.add(waitRecovery.getReturnValue());
            }
            break;
        }
    }

    private void waitFastboot() throws InterruptedException, IOException {
        /*
        var getDevices = new GetDevices();
        while (true) {
            try {
                getDevices.exec();
                this.logger.add(getDevices);
            } catch (CommandErrorException e) {
                continue;
            }
            if (getDevices.getMode().toString().equals("fastboot")) {
                break;
            }
        }
        */
    }
}
