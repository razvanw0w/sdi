package ro.sdi.lab.client.view;

import org.springframework.remoting.RemoteConnectFailureException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

import ro.sdi.lab.common.exception.ProgramException;

public class ResponseMapper<T> implements Function<Future<T>, String>
{
    Function<T, String> resultMapper;

    public ResponseMapper(Function<T, String> resultMapper)
    {
        this.resultMapper = resultMapper;
    }

    @Override
    public String apply(Future<T> future)
    {
        try
        {
            T result = future.get();
            return resultMapper.apply(result);
        }
        catch (InterruptedException e)
        {
            return "Operation interrupted";
        }
        catch (ExecutionException e)
        {
            if (e.getCause() instanceof ProgramException)
            {
                return Console.handleException((ProgramException) e.getCause());
            }
            else if (e.getCause() instanceof RemoteConnectFailureException)
            {
                return "Remote connection failed!";
            }
            else
            {
                throw new RuntimeException(e);
            }
        }
    }
}
