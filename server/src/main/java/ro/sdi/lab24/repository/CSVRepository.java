package ro.sdi.lab24.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import ro.sdi.lab24.exception.ProgramIOException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.serialization.CSVSerializer;
import ro.sdi.lab24.validation.Validator;

public class CSVRepository<ID, T extends Entity<ID>> extends AbstractRepository<ID, T>
{
    public Path path;
    private CSVSerializer<T> serializer;
    private String fileName;
    private Validator<T> validator;

    public CSVRepository(String fileName, CSVSerializer<T> serializer, Validator<T> validator)
    {
        super();
        this.fileName = fileName;
        this.path = Paths.get(fileName);
        this.serializer = serializer;
        this.validator = validator;
        loadPersistence();
    }

    private void checkFileExistence()
    {
        if (!Files.exists(path))
        {
            try
            {
                Files.createFile(path);
            }
            catch (IOException e)
            {
                throw new ProgramIOException("Couldn't create file " + fileName);
            }
        }
    }

    @Override
    protected void loadPersistence()
    {
        checkFileExistence();
        try
        {
            entities = Files.readAllLines(path).stream().map(line -> serializer.deserialize(line))
                    .collect(Collectors.toMap(Entity<ID>::getId, entity -> entity));
            entities.values().forEach(value -> validator.validate(value));
        }
        catch (IOException exception)
        {
            throw new ProgramIOException("Cannot load file " + fileName);
        }
    }

    @Override
    protected void updatePersistence()
    {
        checkFileExistence();
        try
        {
            Files.write(
                    path,
                    entities.values()
                            .stream()
                            .map(value -> serializer.serialize(value))
                            .collect(Collectors.toList())
            );
        }
        catch (IOException exception)
        {
            throw new ProgramIOException("Cannot update file " + fileName);
        }
    }

}
