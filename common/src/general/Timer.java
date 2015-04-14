package general;

import java.util.concurrent.TimeUnit;

public class Timer {

    public static final TimeUnit TIME_UNIT = TimeUnit.NANOSECONDS;
    private long start = 0;
    private long end = 0;
    private long startWallTime = 0;
    private long endWallTime = 0;

    public static Timer start() {
        return new Timer();
    }

    public void reset() {
        _start();
    }

    public long startTime(TimeUnit timeUnit) {
        return TimeUnit.MILLISECONDS.convert(startWallTime, timeUnit);
    }

    public long endTime(TimeUnit timeUnit) {
        return TimeUnit.MILLISECONDS.convert(endWallTime, timeUnit);
    }

    public long elapsedTime(TimeUnit timeUnit) {
        end = System.nanoTime();
        endWallTime = System.currentTimeMillis();
        return timeUnit.convert(end - start, TIME_UNIT);
    }

    public long elapsedTime() {
        return elapsedTime(TIME_UNIT);
    }

    private Timer() {
        _start();
    }

    private void _start() {
        start = System.nanoTime();
        startWallTime = System.currentTimeMillis();
    }

}
