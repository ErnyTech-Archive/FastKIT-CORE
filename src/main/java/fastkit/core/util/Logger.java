package fastkit.core.util;

import fastkit.core.GenericApi;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<String> outputs = new ArrayList<>();
    private List<Integer> returnValues = new ArrayList<>();

    public void add(String output) {
        this.outputs.add(output);
    }

    public void add(int returnValue) {
        this.returnValues.add(returnValue);
    }

    public void add(ExecCmd execCmd) {
        this.outputs.add("Command : " + execCmd.getCommand() + System.lineSeparator() + execCmd.getStdout());
        this.returnValues.add(execCmd.getReturnValue());
    }

    public void add(GenericApi genericApi) {
        merge(genericApi.getLog());
    }

    public void merge(Logger... loggers) {
        for (var logger : loggers) {
            this.outputs.addAll(logger.getOutputs());
            this.returnValues.addAll(logger.getReturnValues());
        }
    }

    public List<String> getOutputs() {
        return this.outputs;
    }

    public List<Integer> getReturnValues() {
        return this.returnValues;
    }
}
