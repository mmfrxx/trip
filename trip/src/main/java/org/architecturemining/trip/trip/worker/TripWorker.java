package org.architecturemining.trip.trip.worker;

import org.architecturemining.trip.trip.TripApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripWorker extends AbstractWorker{
    private Logger logger = LoggerFactory.getLogger(TripWorker.class);

    public TripWorker(String client, String type, Boolean enoughCredit, TripApplication.Communicator communicator) {
        super(client, type, enoughCredit, communicator);

    }
    @Override
    public void run() {
        if (getEnoughCredit() == null) {
            handleTicketPurchase();
        }
    }

    private void handleTicketPurchase(){
        logger.info("Client " + getClient() + " is trying to check-in with anonymous ticket.");

    }
}
