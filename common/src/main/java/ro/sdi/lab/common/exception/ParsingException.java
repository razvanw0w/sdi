package ro.sdi.lab.common.exception;

public class ParsingException extends ProgramException
{
    public ParsingException(String message)
    {
        super(message);
    }

    public ParsingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ParsingException(Throwable cause)
    {
        super(cause);
    }
}
