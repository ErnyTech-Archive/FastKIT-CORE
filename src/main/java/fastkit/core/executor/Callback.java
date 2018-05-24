package fastkit.core.executor;

import fastkit.core.GenericApi;
import fastkit.core.util.Logger;

import java.util.List;

public interface Callback {
    void onSuccess(Logger logger, List<GenericApi> adbApis);
    void onError(Exception exception, Logger logger, List<GenericApi> adbApis);
}
