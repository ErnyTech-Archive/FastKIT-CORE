package fastkit.core.executor;

import fastkit.core.GenericApi;

import java.util.List;

public interface Callback {
    void onSuccess(List<String> outputs, List<Integer> returnValues, List<GenericApi> adbApis);
    void onError(Exception exception, List<GenericApi> adbApis);
    void onError(Exception exception, String output, List<GenericApi> adbApis);
}
