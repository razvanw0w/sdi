package ro.sdi.lab24.repository;

import ro.sdi.lab24.model.Entity;

import java.io.Serializable;

public class MemoryRepository<ID extends Serializable, T extends Entity<ID>> extends AbstractRepository<ID, T> {

    @Override
    protected void loadPersistence() {

    }

    @Override
    protected void updatePersistence()
    {

    }
}
