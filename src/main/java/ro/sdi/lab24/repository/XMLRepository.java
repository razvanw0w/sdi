package ro.sdi.lab24.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.sdi.lab24.exception.ProgramIOException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.serialization.xml.XMLSerializer;
import ro.sdi.lab24.validation.Validator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XMLRepository<ID, T extends Entity<ID>> extends AbstractRepository<ID, T>
{
    String fileName;
    XMLSerializer<T> serializer;
    private Validator<T> validator;
    private Path path;
    private final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    private final TransformerFactory transformerFactory = TransformerFactory.newInstance();

    public XMLRepository(String fileName, XMLSerializer<T> serializer, Validator<T> validator) throws ParserConfigurationException
    {
        this.fileName = fileName;
        this.serializer = serializer;
        this.validator = validator;
        this.path = Paths.get(fileName);
    }

    private void checkFileExistence()
    {
        if (!Files.exists(path))
        {
            try
            {
                Files.createFile(path);
                Document document = documentBuilder.newDocument();
                Element documentElement = document.createElement("entities");
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(new DOMSource(documentElement), new StreamResult(new FileOutputStream(fileName)));
            }
            catch (IOException | TransformerException e)
            {
                throw new ProgramIOException("Couldn't create file " + fileName);
            }
        }
    }

    @Override
    protected void loadPersistence()
    {
        try
        {
            checkFileExistence();
            Document document = documentBuilder.parse(fileName);
            Element documentElement = document.getDocumentElement();
            NodeList childNodes = documentElement.getChildNodes();

            entities = IntStream.range(0, childNodes.getLength())
                    .mapToObj(childNodes::item)
                    .filter(Element.class::isInstance)
                    .map(Element.class::cast)
                    .map(serializer::deserialize)
                    .peek(validator::validate)
                    .collect(Collectors.toMap(T::getId, t -> t));
        }
        catch (SAXException | IOException e)
        {
            throw new ProgramIOException("XML document cannot be loaded!");
        }
    }

    @Override
    protected void updatePersistence()
    {
        try
        {
            checkFileExistence();
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element documentElement = document.createElement("entities");

            entities.values()
                    .stream()
                    .map(entity -> serializer.serialize(document, entity))
                    .forEach(documentElement::appendChild);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(documentElement), new StreamResult(new FileOutputStream(fileName)));
        }
        catch (TransformerException | IOException | ParserConfigurationException e)
        {
            throw new ProgramIOException("XML document cannot be written!");
        }
    }
}
