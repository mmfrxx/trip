package org.architecturemining.trip.trip.worker;


import org.architecturemining.trip.trip.TripApplication;

public abstract class AbstractWorker implements Runnable {
    private TripApplication.Communicator communicator;
    private static int MAXSERVICETIME = 50;
    private static int MINSERVICETIME = 10;
    private String client;
    private String type;
    private Boolean enoughCredit;

    public AbstractWorker(String client, String type, Boolean enoughCredit, TripApplication.Communicator communicator) {
        this.client = client;
        this.type = type;
        this.enoughCredit = enoughCredit;
        this.communicator = communicator;
    }

    public String getClient() {
        return client;
    }

    public String getType() {
        return type;
    }

    public Boolean getEnoughCredit() {
        return enoughCredit;
    }

    public TripApplication.Communicator getCommunicator() {
        return communicator;
    }

    public int work() {
        int waitingTime = (int) (Math.random() * (MAXSERVICETIME - MINSERVICETIME) + MINSERVICETIME);
        try {
            // waitingTime is in seconds, hence multiply with 1000 to get milliseconds
            Thread.sleep(waitingTime * 1000);
        } catch (InterruptedException e) {
            return 0;
        }
        return waitingTime;
    }

    @Override
    public abstract void run();

}
