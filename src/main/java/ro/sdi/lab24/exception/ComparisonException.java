package ro.sdi.lab24.exception;

public class ComparisonException extends ProgramException {
    public ComparisonException(String message) {
        super(message);
    }

    public ComparisonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComparisonException(Throwable cause) {
        super(cause);
    }
}
