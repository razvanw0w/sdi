package ro.sdi.lab24.exception;

public class DateTimeInvalid extends ProgramException
{
    public DateTimeInvalid(String message)
    {
        super(message);
    }

    public DateTimeInvalid(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DateTimeInvalid(Throwable cause)
    {
        super(cause);
    }
}
