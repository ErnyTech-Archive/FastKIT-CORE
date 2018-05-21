package fastkit.core;

import fastkit.core.adb.GetDevices;
import fastkit.core.adb.Mode;
import fastkit.core.util.exception.CommandErrorException;
import fastkit.core.util.exception.ExecutorErrorException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Executor {
    private Mode mode;
    private Thread execThread;
    private List<String> outputs = new ArrayList<>();
    private List<Integer> returnValues = new ArrayList<>();
    public abstract void command(GetDevices device) throws ExecutorErrorException;
    public abstract void onSuccess(List<String> outputs, List<Integer> returnValues);
    public abstract void onError(Exception exception);
    public abstract void onError(Exception exception, String output);
    public static String adb;
    public static String fastboot;

    public Executor(Mode mode) {
        this.mode = mode;
    }

    public void start() {
        this.execThread = new Thread(() -> {
            try {
                executor();
            } catch (ExecutorErrorException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                onError(e);
            }
        });
        this.execThread.start();
    }

    private void executor() throws ExecutorErrorException {
        if (Executor.adb == null) {
            error(new ExecutorErrorException("Adb is not setted"));
        }

        if (Executor.fastboot == null) {
            error(new ExecutorErrorException("Fastboot is not setted"));
        }

        var getDevices = new GetDevices();
        try {
            getDevices.exec();
        } catch (InterruptedException | IOException e) {
            error(e);
            return;
        } catch (CommandErrorException e) {
            error(e, getDevices.getOutput());
            return;
        }

        if (this.mode != getDevices.getMode()) {
                onError(new Exception("Device is not in required mode"));
                return;
        }

        command(getDevices);

        onSuccess(this.outputs, this.returnValues);
    }

    public void kill() {
        this.execThread.interrupt();
    }

    public List<String> getOutputs() {
        if (this.execThread.isAlive()) {
            return this.outputs;
        }
        return null;
    }

    public List<Integer> getReturnValues() {
        if (this.execThread.isAlive()) {
            return this.returnValues;
        }
        return null;
    }

    public void setAdb(String adbpath) {
        Executor.adb = adbpath + " ";
    }

    public void setFastboot(String fastbootpath) {
        Executor.fastboot = fastbootpath + " ";
    }

    void call(GenericApi api) throws ExecutorErrorException {
        try {
            api.exec();
        } catch (InterruptedException | IOException e) {
            this.outputs.add(exceptionToString(e));
            this.returnValues.add(-1);
            error(e);
        } catch (CommandErrorException e) {
            this.outputs.add(api.getOutput());
            this.returnValues.add(api.getReturnValue());
            error(e, api.getOutput());
        }
        this.outputs.add(api.getOutput());
        this.returnValues.add(api.getReturnValue());
    }

    private void error(Exception e) throws ExecutorErrorException {
        onError(e);
        throw new ExecutorErrorException("Executor has killed because failled to do an operation");
    }

    private void error(Exception e, String output) throws ExecutorErrorException {
        onError(e, output);
        throw new ExecutorErrorException("Executor has killed because failled to do an operation");
    }

    private String exceptionToString(Exception e) {
        var exceptionStringWriter = new StringWriter();
        var excpetionPrinter = new PrintWriter(exceptionStringWriter);
        e.printStackTrace(excpetionPrinter);
        return exceptionStringWriter.toString();
    }
}
