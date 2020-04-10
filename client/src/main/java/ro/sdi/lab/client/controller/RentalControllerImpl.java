package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sdi.lab.common.controller.RentalController;
import ro.sdi.lab.common.model.Rental;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class RentalControllerImpl implements FutureRentalController {
    public static final Logger log = LoggerFactory.getLogger(RentalControllerImpl.class);

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RentalController rentalController;


    @Override
    public Future<Void> addRental(int movieId, int clientId, String time) {
        Callable<Void> callable = () -> {
            rentalController.addRental(movieId, clientId, time);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> deleteRental(int movieId, int clientId) {
        Callable<Void> callable = () -> {
            rentalController.deleteRental(movieId, clientId);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Rental>> getRentals() {
        Callable<Iterable<Rental>> callable = () -> {
            return rentalController.getRentals();
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateRental(int movieId, int clientId, String time) {
        Callable<Void> callable = () -> {
            rentalController.updateRental(movieId, clientId, time);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Rental>> filterRentalsByMovieName(String name) {
        Callable<Iterable<Rental>> callable = () -> {
            return rentalController.filterRentalsByMovieName(name);
        };
        return executorService.submit(callable);
    }
}
