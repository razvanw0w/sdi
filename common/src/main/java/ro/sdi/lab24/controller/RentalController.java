package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Rental;

public interface RentalController
{
    void addRental(int movieId, int clientId, String time);

    void deleteRental(int movieId, int clientId);

    Iterable<Rental> getRentals();

    void updateRental(int movieId, int clientId, String time);

    Iterable<Rental> filterRentalsByMovieName(String name);
}
