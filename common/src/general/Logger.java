package general;

public interface Logger {

    public abstract void info(String message);

    public abstract void warn(String message);

    public abstract void error(String message);

    public abstract void fatal(String message);

    public abstract void debug(String message);

    public abstract void flush();
    
}