package fastkit.core.adb;

import fastkit.Utils;
import fastkit.util.ExecCmd;
import fastkit.util.exception.CommandErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fastkit.util.Utils.adb_bin;
import static fastkit.util.Utils.fastboot_bin;

public class GetDevices extends Utils implements GenericAdb {
    private String device_mode;
    private String device_serial;
    private String device_model;
    private StringBuilder output = new StringBuilder();
    private List<Integer> returnValutes = new ArrayList<>();


    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        if(!isFastbootDevice()) {
            this.device_mode = foundDeviceMode();
            this.device_serial = foundDeviceSerial();
            this.device_model = foundDeviceModel();
        } else {
            this.device_mode = "fastboot";
            this.device_serial = null;
            this.device_model = null;
        }
    }

    @Override
    public String getOutput() {
        return this.output.toString();
    }

    @Override
    public int getReturnValue() {
       for(Integer value: this.returnValutes) {
           if (value != 0) {
               return -1;
           }
       }
       return 0;
    }

    private boolean isFastbootDevice() throws IOException, InterruptedException {
        ExecCmd execCmd = new ExecCmd(fastboot_bin() + "devices");
        try {
            execCmd.exec();
        } catch (CommandErrorException e) {
            return false;
        }
        String fastboot_devices = execCmd.getStdout().trim();
        this.output.append(execCmd.getStdout()).append(System.lineSeparator());
        this.returnValutes.add(execCmd.getReturnValue());
        return fastboot_devices.contains("fastboot");
    }

    public Mode getMode() {
        switch (this.device_mode) {
            case "device" : {
                return Mode.device;
            }
            case "recovery" : {
                return Mode.recovery;
            }
            case "fastboot" : {
                return Mode.fastboot;
            }
            default: {
                return null;
            }
        }
    }

    public String getSerial() {
        return this.device_serial;
    }

    public String getModel() {
        return this.device_model;
    }

    private String foundDeviceMode() throws InterruptedException, IOException, CommandErrorException {
        ExecCmd execCmd = new ExecCmd(adb_bin() + "get-state");
        execCmd.exec();
        this.output.append(execCmd.getStdout()).append(System.lineSeparator());
        return execCmd.getStdout().trim();
    }

    private String foundDeviceSerial() throws InterruptedException, IOException, CommandErrorException {
        ExecCmd execCmd = new ExecCmd(adb_bin() + "get-serialno");
        execCmd.exec();
        this.output.append(execCmd.getStdout()).append(System.lineSeparator());
        return execCmd.getStdout().trim();
    }

    private String foundDeviceModel() throws InterruptedException, IOException, CommandErrorException {
        Shell shell = new Shell("getprop | grep ro.product.model");
        shell.exec();
        this.output.append(shell.getOutput()).append(System.lineSeparator());
        return shell.getOutput()
                .split(":")[1]
                .replace("[", "")
                .replace("]", "")
                .trim();
    }
}
