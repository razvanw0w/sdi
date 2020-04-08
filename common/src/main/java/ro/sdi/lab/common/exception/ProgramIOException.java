package ro.sdi.lab.common.exception;

public class ProgramIOException extends ProgramException {
    public ProgramIOException(String message) {
        super(message);
    }

    public ProgramIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProgramIOException(Throwable cause) {
        super(cause);
    }
}