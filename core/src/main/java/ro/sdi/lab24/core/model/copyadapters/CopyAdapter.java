package ro.sdi.lab24.core.model.copyadapters;

public interface CopyAdapter<T> {
    void copy(T source, T destination);
}