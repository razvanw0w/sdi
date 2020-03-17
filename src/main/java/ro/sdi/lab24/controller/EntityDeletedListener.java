package ro.sdi.lab24.controller;

@FunctionalInterface
public interface EntityDeletedListener<T>
{
    void onEntityDeleted(T entity);
}
