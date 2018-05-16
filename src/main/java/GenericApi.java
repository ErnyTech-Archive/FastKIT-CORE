package fastkit.core;

import fastkit.util.exception.CommandErrorException;

import java.io.IOException;

public interface GenericApi {
    public void exec() throws InterruptedException, IOException, CommandErrorException;
    public String getOutput();
    public int getReturnValue();
}
