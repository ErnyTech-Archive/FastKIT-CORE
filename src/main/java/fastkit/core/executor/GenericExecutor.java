package fastkit.core.executor;

import fastkit.core.GenericApi;
import fastkit.core.adb.GetDevices;
import fastkit.core.util.exception.ExecutorErrorException;

import java.util.List;

public interface GenericExecutor {
    public void start(Callback callback);
    void command(GetDevices device) throws ExecutorErrorException;
    void executor() throws ExecutorErrorException;
    public void kill();
    public List<GenericApi> getAdbApis();
    public List<String> getOutputs();
    public List<Integer> getReturnValues();
    void call(GenericApi api) throws ExecutorErrorException;
    void error(Exception e) throws ExecutorErrorException;
    void error(Exception e, String output) throws ExecutorErrorException;
}
