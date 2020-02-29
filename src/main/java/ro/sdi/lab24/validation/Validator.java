package ro.sdi.lab24.validation;

import ro.sdi.lab24.exception.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
