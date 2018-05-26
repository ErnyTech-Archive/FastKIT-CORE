package fastkit.core.fastboot;

import fastkit.core.GenericApi;
import fastkit.core.util.BootloaderVar;
import fastkit.core.util.BootloaderVars;
import fastkit.core.util.ExecCmd;
import fastkit.core.util.Logger;
import fastkit.core.util.exception.CommandErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fastkit.core.executor.Executor.fastboot;

public class GetVar implements GenericApi {
    private Logger logger = new Logger();
    private BootloaderVars bootloaderVars;

    @Override
    public void exec() throws InterruptedException, IOException, CommandErrorException {
       var getVar = new ExecCmd(fastboot + "getvar" + sep + "all", this.logger);
       getVar.exec();
       parser(getVar.getStdout().split(System.lineSeparator()));
       var getOemVar = new ExecCmd(fastboot + "oem device-info", this.logger);
       getOemVar.exec();
       parser(getOemVar.getStdout().split(System.lineSeparator()));
    }

    @Override
    public Logger getLog() {
        return this.logger;
    }

    public BootloaderVars getVars() {
        return this.bootloaderVars;
    }

    private void parser(String[] outputs) {
        List<BootloaderVar> bootloaderVarsList = new ArrayList<>();

        for(String output : outputs) {
            output = output.trim();

            if (output.equals("...")) {
                continue;
            }

            if (output.equals("all:")) {
                break;
            }

            if (output.contains("OKAY")) {
                break;
            }

            var bootloadervars = output.substring(output.indexOf("(bootloader)") + "(bootloader)".length())
                    .trim()
                    .split(":");

            String value = null;

            String name = mergeName(bootloadervars);

            if (bootloadervars.length >= 2) {
                value = bootloadervars[bootloadervars.length - 1].trim();
            }

            var bootloaderVar = new BootloaderVar(name, value);
            bootloaderVarsList.add(bootloaderVar);
        }

        this.bootloaderVars = new BootloaderVars(bootloaderVarsList);
    }

    private String mergeName(String[] bootloadervars) {
        StringBuilder nameBuilder = new StringBuilder();

        if (bootloadervars.length <= 2) {
            return bootloadervars[0].trim();
        }

        for (int i = 0; i < bootloadervars.length-1; i++) {
            nameBuilder.append(bootloadervars[i].trim());

            if (i != bootloadervars.length-2) {
                nameBuilder.append(":");
            }
        }
        return nameBuilder.toString();
    }
}
