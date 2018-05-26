package fastkit.core.adb;

import fastkit.core.GenericApi;
import fastkit.core.fastboot.GetVar;
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
                this.logger.add(waitSystem);
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
            } catch (CommandErrorException e) {
                continue;
            } finally {
                this.logger.add(waitRecovery);
            }
            break;
        }
    }

    private void waitFastboot() throws InterruptedException, IOException, CommandErrorException {
        var getVar = new GetVar();
        getVar.exec();
        this.logger.add(getVar);
    }
}
