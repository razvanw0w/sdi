package ro.sdi.lab24.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.NetworkingUtils;

public class ControllerAdapter
{
    public static Message handle(Message message, Controller controller)
    {
        String[] splitHeader = message.getHeader().split(":");
        String methodName = splitHeader[1];
        Class<Controller> controllerClass = Controller.class;
        try
        {
            Method method = Arrays
                    .stream(controllerClass.getDeclaredMethods())
                    .filter(m -> m
                            .getName()
                            .equals(methodName))
                    .findAny()
                    .orElseThrow(NoSuchMethodException::new);

            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            IntStream.range(0, parameterTypes.length).forEach(
                    i -> parameters[i] = NetworkingUtils.deserializeByType(
                            message.getBody().get(i),
                            parameterTypes[i]
                    )
            );
            Object returnedValue = method.invoke(controller, parameters);
            if (method.getReturnType().equals(Iterable.class))
            {
                List<String> values = StreamSupport
                        .stream(((Iterable<?>) returnedValue).spliterator(), false)
                        .map(NetworkingUtils::serialize)
                        .collect(Collectors.toList());
                return NetworkingUtils.success(values);
            }
            return NetworkingUtils.success(null);
        }
        catch (NoSuchMethodException e)
        {
            return NetworkingUtils.exception("No such method in class " + splitHeader[0]);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            return NetworkingUtils.exception("Error computing the result of " + splitHeader[1]);
        }
    }
}
