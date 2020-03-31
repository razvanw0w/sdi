package ro.sdi.lab24;

import java.util.List;
import java.util.TimerTask;

import ro.sdi.lab24.view.ResponseBuffer;

public class ResponseDaemon extends TimerTask
{
    private ResponseBuffer responseBuffer;

    public ResponseDaemon(ResponseBuffer responseBuffer)
    {
        this.responseBuffer = responseBuffer;
    }

    @Override
    public void run()
    {
        List<String> responses = responseBuffer.getResponses();
        if (!responses.isEmpty())
        {
            System.out.println(String.join("\n", responses));
        }
    }
}
