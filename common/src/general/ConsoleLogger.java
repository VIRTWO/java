package general;

public class ConsoleLogger implements Logger {

    private static boolean debug = false;

    public static void enableDebug() {
        debug = true;
    }

    public static void disableDebug() {
        debug = false;
    }

    private static StringBuffer logCash = new StringBuffer(10000);

    private void write(String message) {
        logCash.append(message + "\n");
        writeCount++;
        if (writeCount == flushInterval) {
            System.out.println(logCash.toString());
            logCash.delete(0, logCash.length());
            writeCount = 0;
        }
    }

    @SuppressWarnings("rawtypes")
    private Class c = null;
    private int flushInterval = 0;
    private int writeCount = 0;

    @SuppressWarnings("rawtypes")
    public ConsoleLogger(Class c, int flushInterval) {
        this.c = c;
        this.flushInterval = flushInterval;
    }

    public void info(String message) {
        this.write("[INFO][" + this.c.getName() + "]: " + message);
    }

    public void warn(String message) {
        this.write("[WARN][" + this.c.getName() + "]: " + message);
    }

    public void error(String message) {
        this.write("[ERROR][" + this.c.getName() + "]: " + message);
    }

    public void fatal(String message) {
        this.write("[FATAL][" + this.c.getName() + "]: " + message);
    }

    public void debug(String message) {
        if (debug == true) {
            this.write("[DEBUG][" + this.c.getName() + "]: " + message);
        }
    }
}
