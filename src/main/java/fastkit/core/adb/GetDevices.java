package fastkit.core.adb;

import fastkit.core.GenericApi;
import fastkit.core.util.Device;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fastkit.core.executor.Executor.adb;

public class GetDevices implements GenericApi {
    private List<Device> devices = new ArrayList<>();
    private Logger logger = new Logger();

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        var execAdbDevices = new ExecCmd(adb + "devices", this.logger);
        execAdbDevices.exec();
        var outputs = execAdbDevices.getStdout().split(System.lineSeparator());

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
            var model = foundDeviceModel();
            var product = foundDeviceProduct();

            switch (mode) {
                case "device" : {
                    this.devices.add(new Device(Mode.device, serial, model, product));
                    break;
                }
                case "recovery" : {
                    this.devices.add(new Device(Mode.recovery, serial, model, product));
                    break;
                }
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

    private String foundDeviceModel() throws InterruptedException, IOException, CommandErrorException {
        var shell = new Shell("getprop | grep ro.product.model");
        shell.exec();
        this.logger.add(shell);
        return shell.getOutput()
                .split(":")[1]
                .replace("[", "")
                .replace("]", "")
                .trim();
    }
    
    private String foundDeviceProduct() throws InterruptedException, IOException, CommandErrorException {
        var shell = new Shell("getprop | grep ro.product.device");
        shell.exec();
        this.logger.add(shell);
        return shell.getOutput()
                .split(":")[1]
                .replace("[", "")
                .replace("]", "")
                .trim();
    }
}
