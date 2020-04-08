package ro.sdi.lab.client.controller;

import ro.sdi.lab.common.model.Rental;

import java.util.concurrent.Future;

public interface FutureRentalController {
    Future<Void> addRental(int movieId, int clientId, String time);

    Future<Void> deleteRental(int movieId, int clientId);

    Future<Iterable<Rental>> getRentals();

    Future<Void> updateRental(int movieId, int clientId, String time);

    Future<Iterable<Rental>> filterRentalsByMovieName(String name);
}
