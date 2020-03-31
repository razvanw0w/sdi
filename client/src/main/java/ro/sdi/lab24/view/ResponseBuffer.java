package ro.sdi.lab24.view;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseBuffer
{
    private List<FutureResponse<?>> responseList = new LinkedList<>();

    public List<String> getResponses()
    {
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
    }
}
