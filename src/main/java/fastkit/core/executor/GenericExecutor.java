package fastkit.core.executor;

import fastkit.core.GenericApi;
import fastkit.core.util.Device;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.ExecutorErrorException;

import java.util.List;

public interface GenericExecutor {
    void start(Callback callback);
    void start(Device device, Callback callback);
    void command(Device device) throws ExecutorErrorException;
    void executor(Device device) throws ExecutorErrorException;
    void kill();
    List<GenericApi> getAdbApis();
    Logger getLog();
    void call(GenericApi api) throws ExecutorErrorException;
    void error(Exception e, Logger logger, List<GenericApi> adbApis) throws ExecutorErrorException;
}
