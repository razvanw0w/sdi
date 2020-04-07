package ro.sdi.lab.server.validation;

import ro.sdi.lab.common.exception.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
