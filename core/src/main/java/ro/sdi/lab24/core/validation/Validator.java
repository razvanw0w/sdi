package ro.sdi.lab24.core.validation;

import ro.sdi.lab24.core.exception.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
