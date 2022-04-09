package org.architecturemining.trip.trip.worker;

import org.architecturemining.trip.trip.TripApplication;
import org.architecturemining.trip.trip.service.TripWorkerCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripWorker extends AbstractWorker{
    private Logger logger = LoggerFactory.getLogger(TripWorker.class);

    public TripWorker(String client, String type, Boolean enoughCredit, TripApplication.Communicator communicator) {
        super(client, type, enoughCredit, communicator);

    }

    @Override
    public void run() {
        if ("anon".equals(getType())) {
            Boolean processed = handleAnonymousCheckIn();
        }
        if("subscription".equals(getType()) || "ticket".equals(getType())){
            handleProductPurchase();
        }
        if("check-in".equals((getType()))) {
            handleCheckIn();
        }
        if("check-out".equals((getType()))) {
            handleCheckOut();
        }
        TripWorkerCatalog.getInstance().remove(this);
    }

    private Boolean handleCheckIn(){
        logger.info("Client " + getClient() + " wants to check in with personal account/card.");
        Boolean processed = getCommunicator().getOutputController().checkIn(getClient(), getEnoughCredit());
        return processed;
    }

    private Boolean handleCheckOut(){
        logger.info("Client " + getClient() + " wants to check out with personal account/card.");
        Boolean processed = getCommunicator().getOutputController().checkOut(getClient());
        return processed;
    }

    private Boolean handleAnonymousCheckIn(){
        logger.info("Client " + getClient() + " is trying to check-in with anonymous ticket.");
        Boolean processed = getCommunicator().getOutputController().checkInWithAnonTicket(getClient());
        return processed;
    }

    private Boolean handleProductPurchase(){
        logger.info("Start processing " + getType() + "purchase request for client: " + getType());
        Boolean processed = getCommunicator().getOutputController().getProductsList(getType(), getClient());

        if (!processed) {
            logger.error("Could not get products list for client " + getClient());
            return false;
        }
        logger.info("Client " + getClient() + " selects product and wants to proceed further to payment.");
        processed = getCommunicator().getOutputController().payForProduct(getType(), getClient());

        return true;
    }
}
