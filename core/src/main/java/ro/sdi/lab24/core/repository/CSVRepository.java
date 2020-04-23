package ro.sdi.lab24.core.repository;

import ro.sdi.lab24.core.exception.ProgramIOException;
import ro.sdi.lab24.core.model.Entity;
import ro.sdi.lab24.core.model.serialization.csv.CSVSerializer;
import ro.sdi.lab24.core.validation.Validator;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class CSVRepository<ID extends Serializable, T extends Entity<ID>> extends AbstractRepository<ID, T> {
    public Path path;
    private CSVSerializer<T> serializer;
    private String fileName;
    private Validator<T> validator;

    public CSVRepository(String fileName, CSVSerializer<T> serializer, Validator<T> validator) {
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
