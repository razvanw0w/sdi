package ro.sdi.lab24.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.NetworkingUtils;

public class ControllerAdapter
{
    private ControllerAdapter()
    {
    }

    public static Message handleMessage(
            Message message, Controller controller,
            ClientController clientController,
            MovieController movieController,
            RentalController rentalController
    )
    {
        Map<String, Object> controllers = new HashMap<>();
        controllers.put("Controller", controller);
        controllers.put("ClientController", clientController);
        controllers.put("MovieController", movieController);
        controllers.put("RentalController", rentalController);

        String controllerName = message.getHeader().split(":")[0];
        Object controllerObject = controllers.get(controllerName);

        if (controllerObject == null)
        {
            return NetworkingUtils.exception("Invalid controller name");
        }
        return handle(message, controllerObject);
    }

    static Message handle(Message message, Object controller)
    {
        String[] splitHeader = message.getHeader().split(":");
        String methodName = splitHeader[1];
        Class<?> controllerClass = controller.getClass();
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
            if (parameterTypes.length != message.getBody().size())
            {
                throw new IndexOutOfBoundsException();
            }
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
        catch (IndexOutOfBoundsException e)
        {
            return NetworkingUtils.exception("Invalid parameters for method " + splitHeader[1]);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            return NetworkingUtils.exception(e.getCause().getMessage());
        }
    }
}
