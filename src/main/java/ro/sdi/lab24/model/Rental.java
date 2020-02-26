package ro.sdi.lab24.model;

import java.time.LocalDateTime;

public class Rental extends Entity<Rental.RentalID>
{
    private LocalDateTime time;

    public Rental(int movieId, int clientId, LocalDateTime time)
    {
        super(new RentalID(movieId, clientId));
        this.time = time;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    public static class RentalID
    {
        private int movieId;
        private int clientId;

        public RentalID(int movieId, int clientId)
        {
            this.movieId = movieId;
            this.clientId = clientId;
        }

        public int getMovieId()
        {
            return movieId;
        }

        public int getClientId()
        {
            return clientId;
        }
    }

    @Override
    public String toString()
    {
        return String.format("Rental[%d, %d, %s]", id.movieId, id.clientId, time);
    }
}
