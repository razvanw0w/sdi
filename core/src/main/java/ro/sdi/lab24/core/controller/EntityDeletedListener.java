package ro.sdi.lab24.core.controller;

@FunctionalInterface
public interface EntityDeletedListener<T>
{
    void onEntityDeleted(T entity);
}
