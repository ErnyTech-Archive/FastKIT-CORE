package fastkit.core;

import fastkit.core.adb.GetDevices;
import fastkit.core.adb.Mode;
import fastkit.core.adb.autoreboot.AutoReboot;
import fastkit.util.exception.CommandErrorException;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public abstract class Executor {
    private Mode mode;
    private String deviceModel;
    private Thread execThread;

    public Executor(Mode mode, String deviceModel) {
        this.mode = mode;
        this.deviceModel = deviceModel;
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

        AutoReboot autoReboot = new AutoReboot(this.mode, getDevices.getMode(), this.deviceModel);
        try {
            autoReboot.exec();
        } catch (InterruptedException | IOException e) {
            onError(e);
            return;
        } catch (CommandErrorException e) {
            onError(e, autoReboot.getOutput());
            return;
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