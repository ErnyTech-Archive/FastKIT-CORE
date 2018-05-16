package fastkit.core;

import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;

public interface GenericApi {
    String sep = " ";
    public void exec() throws InterruptedException, IOException, CommandErrorException;
    public String getOutput();
    public int getReturnValue();
}
