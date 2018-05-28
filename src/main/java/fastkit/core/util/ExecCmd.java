package fastkit.core.util;

import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ExecCmd {
    private ProcessBuilder processBuilder;
    private String cmd;
    private int returnValue;
    private String stdout;
    private Logger logger;

    public ExecCmd(String cmd) {
        this.cmd = cmd; 
        this.processBuilder = new ProcessBuilder()
                .command(this.cmd.split("\\s+"))
                .redirectErrorStream(true);

    }

    public ExecCmd(String cmd, Logger logger) {
        this.cmd = cmd; 
        this.processBuilder = new ProcessBuilder()
                .command(this.cmd.split("\\s+"))
                .redirectErrorStream(true);
        this.logger = logger;
    }

    public void exec() throws IOException, InterruptedException, CommandErrorException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Thread is Interrupted");
        }

        var process = this.processBuilder.start();
        process.waitFor();

        this.returnValue = process.exitValue();
        var stdout_stream = process.getInputStream();
        var stdout_scanner = new Scanner(stdout_stream).useDelimiter("\\A");
        this.stdout = stdout_scanner.hasNext() ? stdout_scanner.next().trim() : "";

        if (this.logger != null) {
            this.logger.add(this);
        }

        if (this.returnValue != 0) {
            throw new CommandErrorException("Error when try execute command: " + System.lineSeparator() + this.stdout);
        }
    }

    public void exec(List<String> stdout_log) throws IOException, InterruptedException, CommandErrorException {
        exec();
        stdout_log.add(this.stdout);
    }

    public String getCommand() {
        return this.cmd;
    }
    public int getReturnValue() {
        return this.returnValue;
    }

    public String getStdout() {
        return this.stdout;
    }
}
