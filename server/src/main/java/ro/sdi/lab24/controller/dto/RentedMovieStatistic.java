package ro.sdi.lab24.controller.dto;

public class RentedMovieStatistic implements Comparable<RentedMovieStatistic> {
    private String movieName;
    private Long numberOfRentals;

    public RentedMovieStatistic(String movieName, Long numberOfRentals) {
        this.movieName = movieName;
        this.numberOfRentals = numberOfRentals;
    }

    public RentedMovieStatistic() {
        this.movieName = "";
        this.numberOfRentals = 0L;
    }

    public String getMovieName() {
        return movieName;
    }

    public Long getNumberOfRentals() {
        return numberOfRentals;
    }

    @Override
    public String toString() {
        return "RentedMovieStatistic{" +
                "movieName='" + movieName + '\'' +
                ", numberOfRentals=" + numberOfRentals +
                '}';
    }

    @Override
    public int compareTo(RentedMovieStatistic other) {
        return numberOfRentals.compareTo(this.getNumberOfRentals());
    }
}
