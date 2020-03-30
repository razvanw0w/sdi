package ro.sdi.lab24.view;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseBuffer
{
    private List<FutureResponse<?>> responseList = new LinkedList<>();

    public List<String> getResponses()
    {
        return responseList.stream()
                           .filter(FutureResponse::available)
                           .peek(responseList::remove)
                           .map(FutureResponse::get)
                           .collect(Collectors.toList());
    }

    public void add(FutureResponse<?> futureResponse)
    {
        responseList.add(futureResponse);
    }

}
