package ro.sdi.lab24.validation;

public class ProgramException extends RuntimeException
{
    public ProgramException(String message)
    {
        super(message);
    }

    public ProgramException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ProgramException(Throwable cause)
    {
        super(cause);
    }
}
