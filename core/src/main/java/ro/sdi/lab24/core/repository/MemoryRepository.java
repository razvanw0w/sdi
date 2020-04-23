package ro.sdi.lab24.core.repository;

import ro.sdi.lab24.core.model.Entity;

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
