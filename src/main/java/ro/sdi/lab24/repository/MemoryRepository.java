package ro.sdi.lab24.repository;

import ro.sdi.lab24.model.Entity;

public class MemoryRepository<ID, T extends Entity<ID>> extends AbstractRepository<ID, T>
{

    @Override
    protected void loadPersistence()
    {

    }

    @Override
    protected void updatePersistence()
    {

    }
}
