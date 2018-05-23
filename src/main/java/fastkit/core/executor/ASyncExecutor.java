package fastkit.core.executor;

import fastkit.core.GenericApi;
import fastkit.core.adb.GetDevices;
import fastkit.core.adb.Mode;
import fastkit.core.util.exception.CommandErrorException;
import fastkit.core.util.exception.ExecutorErrorException;

import java.io.IOException;
import java.util.List;

public abstract class ASyncExecutor extends Executor {
    private Callback callback;

    public ASyncExecutor(Mode mode) {
        super(mode);
    }

    @Override
    public void start(Callback callback) {
        this.callback = callback;
        this.execThread = new Thread(() -> {
            try {
                executor();
            } catch (ExecutorErrorException e) {
                kill();
            }
        });
        this.execThread.start();
    }


    @Override
    public void executor() throws ExecutorErrorException {
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
            this.callback.onError(new ExecutorErrorException("Device is not in required mode"), this.adbApis);
            return;
        }

        command(getDevices);

        this.callback.onSuccess(this.outputs, this.returnValues, this.adbApis);
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
    public List<String> getOutputs() {
        return this.outputs;
    }

    @Override
    public List<Integer> getReturnValues() {
        return returnValues;
    }

    @Override
    public void call(GenericApi api) throws ExecutorErrorException {
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
        this.adbApis.add(api);
        this.outputs.add(api.getOutput());
        this.returnValues.add(api.getReturnValue());
    }

    @Override
    public void error(Exception e) throws ExecutorErrorException {
        this.callback.onError(e, this.adbApis);
        throw new ExecutorErrorException("Executor has killed because failled to do an operation");
    }

    @Override
    public void error(Exception e, String output) throws ExecutorErrorException {
        this.callback.onError(e, output, this.adbApis);
        throw new ExecutorErrorException("Executor has killed because failled to do an operation");
    }
}
