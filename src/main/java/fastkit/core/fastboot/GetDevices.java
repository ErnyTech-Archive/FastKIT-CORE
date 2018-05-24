package fastkit.core.fastboot;

import fastkit.core.GenericApi;
import fastkit.core.adb.Mode;
import fastkit.core.util.Device;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import static fastkit.core.executor.Executor.fastboot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetDevices implements GenericApi {
    private List<Device> devices = new ArrayList<>();
    private Logger logger = new Logger();

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        var execFastbootDevices = new ExecCmd(fastboot + "devices", this.logger);
        execFastbootDevices.exec();
        var outputs = execFastbootDevices.getStdout().split(System.lineSeparator());

        for (String output : outputs) {
            output = output.trim();

            if (output.isEmpty()) {
                continue;
            }

            if (output.contains("*")) {
                continue;
            }

            var mode = output.split("\\s+")[1].trim();
            var serial = output.split("\\s+")[0].trim();

            if(mode.equals("fastboot")) {
                devices.add(new Device(Mode.fastboot, serial, null));
            }
        }
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }

    public List<Device> get() {
        return this.devices;
    }
}
