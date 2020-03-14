package ro.sdi.lab24.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.sdi.lab24.model.Movie;

import static ro.sdi.lab24.model.serialization.xml.XMLUtils.appendChildWithTextNode;
import static ro.sdi.lab24.model.serialization.xml.XMLUtils.getTextFromTagName;

public class MovieXMLSerializer implements XMLSerializer<Movie>
{

    @Override
    public Element serialize(Document document, Movie entity)
    {
        Element element = document.createElement("movie");
        appendChildWithTextNode(document, element, "id", String.valueOf(entity.getId()));
        appendChildWithTextNode(document, element, "name", entity.getName());
        appendChildWithTextNode(document, element, "genre", entity.getGenre());
        appendChildWithTextNode(document, element, "rating", String.valueOf(entity.getId()));
        return element;
    }

    @Override
    public Movie deserialize(Element element)
    {
        int id = Integer.parseInt(getTextFromTagName(element, "id"));
        String name = getTextFromTagName(element, "name");
        String genre = getTextFromTagName(element, "genre");
        int rating = Integer.parseInt(getTextFromTagName(element, "rating"));
        return new Movie(id, name, genre, rating);
    }
}
