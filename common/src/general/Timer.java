package general;

import java.util.Date;

public class Timer {

    private long startEpoch = 0, endEpoch = 0, runTime = 0;
    boolean running = false;

    public void start() {
        startEpoch = new Date().getTime();
        this.running = true;
    }

    public long timeElapsed() {
        if (this.running == false) return runTime;
        return ((new Date().getTime()) - this.startEpoch);
    }

    public void printTimeElapsed() {
        System.out.println("Time Elapsed (Milliseconds): " + timeElapsed());
    }

    public void stop() {
        if (this.running == true) {
            endEpoch = new Date().getTime();
            this.runTime = endEpoch - this.startEpoch;
            this.running = false;
        }
    }

}
