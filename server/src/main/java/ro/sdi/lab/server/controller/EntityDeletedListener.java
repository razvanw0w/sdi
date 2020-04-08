package ro.sdi.lab.server.controller;

@FunctionalInterface
public interface EntityDeletedListener<T>
{
    void onEntityDeleted(T entity);
}
