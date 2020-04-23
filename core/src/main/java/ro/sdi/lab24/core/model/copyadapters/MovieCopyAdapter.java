package ro.sdi.lab24.core.model.copyadapters;

import ro.sdi.lab24.core.model.Movie;

public class MovieCopyAdapter implements CopyAdapter<Movie> {
    @Override
    public void copy(Movie source, Movie destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        destination.setGenre(source.getGenre());
        destination.setRating(source.getRating());
    }
}
