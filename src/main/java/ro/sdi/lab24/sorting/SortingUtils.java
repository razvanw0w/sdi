package ro.sdi.lab24.sorting;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;
import ro.sdi.lab24.exception.SortingException;

public class SortingUtils
{
    private SortingUtils()
    {
    }

    public static <T> List<T> sort(List<T> entities, Sort sort)
    {
        if (entities.isEmpty())
        {
            return Collections.emptyList();
        }
        List<Pair<Sort.Direction, String>> sortingFields = new LinkedList<>(sort.sortingFields);
        Collections.reverse(sortingFields);

        sortingFields.forEach(
                sortingField ->
                {
                    Sort.Direction direction = sortingField.getKey();
                    String name = sortingField.getValue();

                    Class<?> entityClass = entities.get(0).getClass();
                    try
                    {
                        Field field = entityClass.getField(name);
                        field.setAccessible(true);
                        entities.sort(
                                (entity1, entity2) ->
                                {
                                    try
                                    {
                                        Object value1 = field.get(entity1);
                                        Object value2 = field.get(entity2);
                                        if (value1 instanceof Integer)
                                        {
                                            return SortingUtils.<Integer>getComparator(direction)
                                                    .compare((Integer) value1, (Integer) value2);
                                        }
                                        else if (value1 instanceof String)
                                        {
                                            return SortingUtils.<String>getComparator(direction)
                                                    .compare((String) value1, (String) value2);
                                        }
                                        else if (value1 instanceof LocalDateTime)
                                        {
                                            return SortingUtils.<LocalDateTime>getComparator(
                                                    direction)
                                                    .compare(
                                                            (LocalDateTime) value1,
                                                            (LocalDateTime) value2
                                                    );
                                        }
                                        throw new SortingException(
                                                "Sorting not implemented for type "
                                                        + value1.getClass().getSimpleName()
                                        );
                                    }
                                    catch (IllegalAccessException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    return 0;
                                });
                    }
                    catch (NoSuchFieldException e)
                    {
                        throw new SortingException(String.format(
                                "Field %s not present in class %s",
                                name,
                                entityClass.getSimpleName()
                        ));
                    }
                }
        );
        return entities;
    }

    private static <T extends Comparable<? super T>> Comparator<T> getComparator(Sort.Direction direction)
    {
        if (direction == Sort.Direction.ASC)
            return Comparator.naturalOrder();
        return Comparator.reverseOrder();
    }
}
