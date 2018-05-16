package fastkit.core.fastboot;

import fastkit.core.adb.GenericAdb;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.GenericBinary;
import fastkit.core.util.exception.CommandErrorException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlashRecovery implements GenericAdb {
    private List<ExecCmd> execCmds = new ArrayList<>();
    private StringBuilder outputs = new StringBuilder();
    private List<Integer> returnValues = new ArrayList<>();

    public FlashRecovery(File recovery) {
        execCmds.add(new ExecCmd(GenericBinary.getFastboot() + "erase recovery"));
        execCmds.add(new ExecCmd(GenericBinary.getFastboot() + "flash recovery" + sep + recovery));
    }

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
        for(ExecCmd execCmd : execCmds) {
            execCmd.exec();
            outputs.append(execCmd.getStdout()).append(System.lineSeparator());
            returnValues.add(execCmd.getReturnValue());
        }
    }

    @Override
    public String getOutput() {
        return outputs.toString();
    }

    @Override
    public int getReturnValue() {
        for(Integer value: this.returnValues) {
            if (value != 0) {
                return -1;
            }
        }
        return 0;
    }
}
