package ro.sdi.lab24.core.model.copyadapters;

import ro.sdi.lab24.core.model.Rental;

public class RentalCopyAdapter implements CopyAdapter<Rental> {
    @Override
    public void copy(Rental source, Rental destination) {
        destination.setId(source.getId());
        destination.setTime(source.getTime());
    }
}
