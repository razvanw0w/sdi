package ro.sdi.lab.common.exception;

public class SortingException extends ProgramException
{
    public SortingException(String message)
    {
        super(message);
    }

    public SortingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SortingException(Throwable cause)
    {
        super(cause);
    }
}
