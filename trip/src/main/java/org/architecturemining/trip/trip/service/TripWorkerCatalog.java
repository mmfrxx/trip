package org.architecturemining.trip.trip.service;

import org.architecturemining.trip.trip.worker.AbstractWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TripWorkerCatalog {
    private Logger logger = LoggerFactory.getLogger(TripWorkerCatalog.class);


    private Map<String, AbstractWorker> clients = new HashMap<>();

    public boolean hasWorkerFor(String client) {
        return clients.containsKey(client);
    }

    public AbstractWorker get(String client) {
        if (hasWorkerFor(client)) {
            return clients.get(client);
        }
        return null;
    }

    public void add(AbstractWorker worker) {
        clients.put(worker.getClient(), worker);
        logger.info("Add worker for: " + worker.getClient() + ". Total workers in catalog: " + clients.size());
    }

    public void remove(AbstractWorker worker) {
        clients.remove(worker.getClient());
        logger.info("Remove worker for: " + worker.getClient() + ". Total workers in catalog: " + clients.size());
    }

    private static TripWorkerCatalog instance = null;

    /**
     * @return the singleton catalog
     */
    public static TripWorkerCatalog getInstance() {
        if (instance == null) {
            instance = new TripWorkerCatalog();
        }
        return instance;
    }
}
