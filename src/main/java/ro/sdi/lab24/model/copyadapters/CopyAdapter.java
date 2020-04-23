package ro.sdi.lab24.model.copyadapters;

public interface CopyAdapter<T> {
    void copy(T source, T destination);
}