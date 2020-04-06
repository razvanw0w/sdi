package ro.sdi.lab24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ro.sdi.lab24.model.Rental;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class RentalControllerImpl implements FutureRentalController {
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
