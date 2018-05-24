package fastkit.core.executor;

import fastkit.core.GenericApi;
import fastkit.core.adb.GetDevices;
import fastkit.core.adb.Mode;
import fastkit.core.adb.Shell;
import fastkit.core.util.Device;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;
import fastkit.core.util.exception.ExecutorErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Executor extends ExecutorUtils implements GenericExecutor {
    private Mode mode;
    private Thread execThread;
    private Device device;
    private List<GenericApi> adbApis = new ArrayList<>();
    private Logger logger = new Logger();
    //Generic global tools
    public static String adb = "adb" + " ";
    public static String fastboot = "fastboot" + " ";
    private Callback callback;

    public Executor(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void start(Callback callback) {
        this.callback = callback;
        this.execThread = new Thread(() -> {
            try {
                executor(this.device);
            } catch (ExecutorErrorException e) {
                kill();
            }
        });
        this.execThread.start();
    }

    @Override
    public void start(Device device, Callback callback) {
        this.device = device;
        start(callback);
    }


    @Override
    public void executor(Device device) throws ExecutorErrorException {
        if (this.device != null && this.mode != device.getDeviceMode()) {
            this.callback.onError(new ExecutorErrorException("Device is not in required mode"), this.logger, this.adbApis);
            return;
        }

        if (this.device != null) {
            Executor.adb = Executor.adb + "-s " + device.getDeviceSerial() + " ";
            Executor.fastboot = Executor.fastboot + "-s " + device.getDeviceSerial() + " ";
        }

        command(device);

        this.callback.onSuccess(this.logger, this.adbApis);
    }

    @Override
    public void kill() {
        this.execThread.interrupt();
    }

    @Override
    public List<GenericApi> getAdbApis() {
        return this.adbApis;
    }

    @Override
    public void call(GenericApi api) throws ExecutorErrorException {
        try {
            api.exec();
        } catch (InterruptedException | IOException e) {
            this.logger.add(exceptionToString(e));
            this.logger.add(-1);
            error(e, this.logger, adbApis);
        } catch (CommandErrorException e) {
            this.logger.add(api);
            error(e, this.logger, adbApis);
        }
        this.adbApis.add(api);
        this.logger.add(api);
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }

    @Override
    public void error(Exception e, Logger logger, List<GenericApi> adbApis) throws ExecutorErrorException {
        this.callback.onError(e, logger, adbApis);
        throw new ExecutorErrorException("Executor has killed because failled to do an operation");

    }

    public static void setAdb(String adbpath) {
        Executor.adb = adbpath;
    }

    public static void setFastboot(String fastbootpath) {
        Executor.fastboot = fastbootpath;
    }
}
