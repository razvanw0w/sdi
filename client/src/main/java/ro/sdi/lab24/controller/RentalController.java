package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Rental;

import java.util.concurrent.Future;

public interface RentalController {
    Future<Void> addRental(int movieId, int clientId, String time);

    Future<Void> deleteRental(int movieId, int clientId);

    Future<Iterable<Rental>> getRentals();

    Future<Void> updateRental(int movieId, int clientId, String time);

    Future<Iterable<Rental>> filterRentalsByMovieName(String name);
}
