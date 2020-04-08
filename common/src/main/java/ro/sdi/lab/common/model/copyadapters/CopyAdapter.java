package ro.sdi.lab.common.model.copyadapters;

public interface CopyAdapter<T>
{
    void copy(T source, T destination);
}
