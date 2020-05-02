package ro.sdi.lab24.core.service;

@FunctionalInterface
public interface EntityDeletedListener<T>
{
    void onEntityDeleted(T entity);
}
