package fastkit.core;

import fastkit.core.adb.GetDevices;
import fastkit.core.adb.Mode;
import fastkit.core.adb.autoreboot.AutoReboot;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;

public abstract class Executor {
    private Mode mode;
    private Thread execThread;
    private File deviceRecovery;

    public Executor(Mode mode) {
        this.mode = mode;
    }

    public Executor(Mode mode, File deviceRecovery) {
        this.mode = mode;
        this.deviceRecovery = deviceRecovery;
    }

    public void exec() {
        this.execThread = new Thread(() -> {
            try {
                executor();
            } catch (Exception e) {
                onError(e);
            }
        });
        this.execThread.start();
    }

    private void executor() {
        GetDevices getDevices = new GetDevices();
        try {
            getDevices.exec();
        } catch (InterruptedException | IOException e) {
            onError(e);
            return;
        } catch (CommandErrorException e) {
            onError(e, getDevices.getOutput());
            return;
        }

        if (this.deviceRecovery != null) {
            AutoReboot autoReboot = new AutoReboot(this.mode, getDevices.getMode(), this.deviceRecovery);
            try {
                autoReboot.exec();
            } catch (InterruptedException | IOException e) {
                onError(e);
                return;
            } catch (CommandErrorException e) {
                onError(e, autoReboot.getOutput());
                return;
            }
        } else {
            if (this.mode != getDevices.getMode()) {
                onError(new Exception("Device is not in required mode"));
                return;
            }
        }

        command();
    }

    public void kill() {
        this.execThread.interrupt();
    }

    public abstract void command();
    public abstract void onSuccess(String output, int returnValue);
    public abstract void onError(Exception exception);
    public abstract void onError(Exception exception, String output);
}
