package ro.sdi.lab.server.repository;

import ro.sdi.lab.common.model.Entity;

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
