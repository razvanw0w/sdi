package ro.sdi.lab24.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@javax.persistence.Entity
public class Rental extends Entity<Rental.RentalID> implements Serializable {
    private LocalDateTime time;

    public Rental() {
        super(new RentalID(0, 0));
    }

    public Rental(int movieId, int clientId, LocalDateTime time) {
        super(new RentalID(movieId, clientId));
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public static class RentalID implements Serializable {
        private int movieId;
        private int clientId;

        public RentalID(int movieId, int clientId) {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RentalID rentalID = (RentalID) o;
            return movieId == rentalID.movieId &&
                    clientId == rentalID.clientId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(movieId, clientId);
        }
    }

    @Override
    public String toString()
    {
        return String.format("Rental[%d, %d, %s]", id.movieId, id.clientId, time);
    }
}
