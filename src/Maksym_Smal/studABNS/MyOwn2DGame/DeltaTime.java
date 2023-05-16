package Maksym_Smal.studABNS.MyOwn2DGame;

public class DeltaTime {

    double interval;

    double delta = 0;
    long lastTime;
    long currentTime;
    int targetInterval;
    long timer = 0;
    int iterationCount = 0;
    int currentInterval = 0;

    DeltaTime(int targetInterval) {
        this.targetInterval = targetInterval;
        interval = 1000000000/ targetInterval;
        currentInterval = targetInterval;
    }

    public void startDeltaTime() {
        lastTime = System.nanoTime();
    }

    public double getIntervalProgress() {
        if (delta > 1) {
            delta--;
            iterationCount++;
        }
        if (timer >= 1000000000) {
            currentInterval = iterationCount;
            iterationCount = 0;
            timer = 0;
        }
        long currentTime = System.nanoTime();
        delta += (currentTime - lastTime)/interval;
        timer += currentTime - lastTime;
        lastTime = currentTime;
        return delta;
    }

    public int getCurrentInterval() {
        return currentInterval;
    }

    public double getDeltaSuccess() {
        return targetInterval / currentInterval;
    }
}
