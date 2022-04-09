package org.architecturemining.trip.trip.service;

import org.architecturemining.trip.trip.TripApplication;
import org.architecturemining.trip.trip.model.ProductType;
import org.architecturemining.trip.trip.worker.AdminWorker;
import org.architecturemining.trip.trip.worker.TripWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {

    private Logger logger = LoggerFactory.getLogger(InputController.class);

    @Autowired
    private TripApplication.Communicator communicator;

    @GetMapping("/trip/{client}/{product}/{enough-credit}")
    public boolean createTrip(@PathVariable("client") String client,
                              @PathVariable("product")String type,
                              @PathVariable(value = "enough-credit", required = false) Boolean isEnoughCredit) {
        if (TripWorkerCatalog.getInstance().hasWorkerFor(client)) {
            return false;
        } else {
            TripWorker worker = new TripWorker(client,type, isEnoughCredit, communicator);
            TripWorkerCatalog.getInstance().add(worker);

            Thread thread = new Thread(worker);
            thread.start();
            return true;
        }
    }

    @GetMapping("/internal/update-tycoons/{client}/{tycoon}/{toAdd}")
    public boolean updateTycoons(@PathVariable("client") String client,
                                 @PathVariable("tycoon") String tycoon,
                                 @PathVariable("toAdd") Boolean toAdd) {
        if (TripWorkerCatalog.getInstance().hasWorkerFor(client)) {
            return false;
        } else {
            AdminWorker worker = new AdminWorker(client, tycoon, toAdd, communicator);
            TripWorkerCatalog.getInstance().add(worker);

            Thread thread = new Thread(worker);
            thread.start();
            return true;
        }
    }
}
