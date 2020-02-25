package ro.sdi.lab24.validation;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
