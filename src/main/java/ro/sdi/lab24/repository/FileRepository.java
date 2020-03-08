package ro.sdi.lab24.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import ro.sdi.lab24.exception.ProgramIOException;
import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.serialization.CSVSerializer;
import ro.sdi.lab24.validation.Validator;

public class FileRepository<ID, T extends Entity<ID>> implements Repository<ID, T>
{
    private CSVSerializer<T> serializer;
    private String fileName;
    private Map<ID, T> entities;
    private Validator<T> validator;
    public Path path;

    public FileRepository(String fileName, CSVSerializer<T> serializer, Validator<T> validator)
    {
        this.fileName = fileName;
        this.path = Paths.get(fileName);
        this.serializer = serializer;
        this.validator = validator;
        this.entities = new HashMap<>();
        loadFile();
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

    private void loadFile()
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

    private void updateFile()
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

    @Override
    public Optional<T> findOne(ID id)
    {
        Objects.requireNonNull(id, "id must not be null");
        loadFile();
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll()
    {
        loadFile();
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "entity must not be null");
        loadFile();
        Optional<T> returnedEntity = Optional.ofNullable(entities.putIfAbsent(
                entity.getId(),
                entity
        ));
        returnedEntity.ifPresentOrElse(t ->
                                       {
                                       }, this::updateFile);
        return returnedEntity;
    }

    @Override
    public Optional<T> delete(ID id)
    {
        Objects.requireNonNull(id, "id must not be null");
        loadFile();
        Optional<T> returnedEntity = Optional.ofNullable(entities.remove(id));
        returnedEntity.ifPresent(t -> updateFile());
        return returnedEntity;
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "entity must not be null");
        loadFile();
        Optional<T> returnedEntity = Optional.ofNullable(entities.computeIfPresent(
                entity.getId(),
                (k, v) -> entity
        ));
        returnedEntity.ifPresent(t -> updateFile());
        return returnedEntity;
    }
}
