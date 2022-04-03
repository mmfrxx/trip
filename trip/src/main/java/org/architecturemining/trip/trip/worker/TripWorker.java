package org.architecturemining.trip.trip.worker;

import org.architecturemining.trip.trip.TripApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripWorker extends AbstractWorker{
    private Logger logger = LoggerFactory.getLogger(TripWorker.class);

    public TripWorker(String client, TripApplication.Communicator communicator) {
        super(client, communicator);

    }
    @Override
    public void run() {

    }
}
