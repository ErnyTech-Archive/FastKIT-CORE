package fastkit.core;

import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;
import java.util.List;

public interface GenericApi {
    String sep = " ";
    public void exec() throws InterruptedException, IOException, CommandErrorException;
    public Logger getLog();
}
