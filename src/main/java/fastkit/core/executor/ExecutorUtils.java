package fastkit.core.executor;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExecutorUtils {
    protected static String exceptionToString(Exception e) {
        var exceptionStringWriter = new StringWriter();
        var excpetionPrinter = new PrintWriter(exceptionStringWriter);
        e.printStackTrace(excpetionPrinter);
        return exceptionStringWriter.toString();
    }
}
