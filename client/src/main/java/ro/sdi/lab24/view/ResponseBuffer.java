package ro.sdi.lab24.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ResponseBuffer
{
    private List<FutureResponse<?>> responseList = new LinkedList<>();

    protected static final int DELAY_SECONDS = 10;
    private Timer timer;

    public synchronized List<String> getResponses()
    {
        if (timer != null)
            timer.cancel();

        List<FutureResponse<?>> completedFutures =
                responseList.stream()
                            .filter(FutureResponse::available)
                            .collect(Collectors.toList());
        List<String> result =
                completedFutures.stream()
                                .map(FutureResponse::get)
                                .collect(Collectors.toUnmodifiableList());
        responseList.removeAll(completedFutures);
        return result;
    }

    public void add(FutureResponse<?> futureResponse)
    {
        responseList.add(futureResponse);
        if (timer != null)
            timer.cancel();
        timer = new Timer();
        timer.schedule(new ShowResponsesTask(this), DELAY_SECONDS * 1000);
    }

    static class ShowResponsesTask extends TimerTask
    {
        private ResponseBuffer responseBuffer;

        public ShowResponsesTask(ResponseBuffer responseBuffer)
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
}
