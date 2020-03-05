package ro.sdi.lab24.repository;

import ro.sdi.lab24.exception.ProgramIOException;
import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.serialization.CSVSerializer;
import ro.sdi.lab24.validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileRepository<ID, T extends Entity<ID>> implements Repository<ID, T> {
    private CSVSerializer<T> serializer;
    private String fileName;
    private Map<ID, T> entities;
    private Validator<T> validator;

    public FileRepository(CSVSerializer<T> serializer, String fileName, Validator<T> validator) {
        this.serializer = serializer;
        this.fileName = fileName;
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    private void loadFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = bufferedReader.readLine();
            while (line != null) {
                T entity = serializer.deserialize(line);
                validator.validate(entity);
                entities.put(entity.getId(), entity);
                line = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            throw new ProgramIOException("Cannot load file " + fileName);
        }
    }

    private void updateFile() {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(entities.values().stream().map(t -> serializer.serialize(t)).collect(Collectors.joining("\n")));
        } catch (IOException exception) {
            throw new ProgramIOException("Cannot update file " + fileName);
        }
    }

    @Override
    public Optional<T> findOne(ID id) {
        Objects.requireNonNull(id, "id must not be null");
        loadFile();
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        loadFile();
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Objects.requireNonNull(entity, "entity must not be null");
        loadFile();
        Optional<T> returnedEntity = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        returnedEntity.ifPresent(t -> updateFile());
        return returnedEntity;
    }

    @Override
    public Optional<T> delete(ID id) {
        Objects.requireNonNull(id, "id must not be null");
        loadFile();
        Optional<T> returnedEntity = Optional.ofNullable(entities.remove(id));
        returnedEntity.ifPresent(t -> updateFile());
        return returnedEntity;
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        Objects.requireNonNull(entity, "entity must not be null");
        loadFile();
        Optional<T> returnedEntity = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
        returnedEntity.ifPresent(t -> updateFile());
        return returnedEntity;
    }
}
