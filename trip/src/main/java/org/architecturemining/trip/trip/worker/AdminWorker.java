package org.architecturemining.trip.trip.worker;

import org.architecturemining.trip.trip.TripApplication;
import org.architecturemining.trip.trip.service.TripWorkerCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminWorker extends AbstractWorker{
    private Logger logger = LoggerFactory.getLogger(TripWorker.class);


    public AdminWorker(String client, String tycoonName, Boolean toAdd, TripApplication.Communicator communicator) {
        super(client, tycoonName, toAdd, communicator);
    }

    @Override
    public void run() {
        if (Boolean.TRUE.equals(getEnoughCredit())) {
            logger.info("Client " + getClient() + " wants to add tycoon " + getType() + " to our system.");
            Boolean processed = getCommunicator().getOutputController().addTycoon(getType(), getClient());
        } else {
            logger.info("Client " + getClient() + " wants to remove tycoon " + getType() + " from our system.");
            Boolean processed = getCommunicator().getOutputController().removeTycoon(getType(), getClient());
        }
        TripWorkerCatalog.getInstance().remove(this);
    }
}
