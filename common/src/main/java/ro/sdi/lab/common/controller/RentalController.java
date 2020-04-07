package ro.sdi.lab.common.controller;

import ro.sdi.lab.common.model.Rental;

public interface RentalController
{
    void addRental(int movieId, int clientId, String time);

    void deleteRental(int movieId, int clientId);

    Iterable<Rental> getRentals();

    void updateRental(int movieId, int clientId, String time);

    Iterable<Rental> filterRentalsByMovieName(String name);
}
