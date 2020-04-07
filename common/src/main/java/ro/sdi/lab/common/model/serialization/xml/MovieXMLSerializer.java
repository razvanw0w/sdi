package ro.sdi.lab.common.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ro.sdi.lab.common.serialization.XMLSerializer;
import ro.sdi.lab.common.model.Movie;

public class MovieXMLSerializer implements XMLSerializer<Movie>
{

    @Override
    public Element serialize(Document document, Movie entity)
    {
        Element element = document.createElement("movie");
        XMLUtils.appendChildWithTextNode(document, element, "id", String.valueOf(entity.getId()));
        XMLUtils.appendChildWithTextNode(document, element, "name", entity.getName());
        XMLUtils.appendChildWithTextNode(document, element, "genre", entity.getGenre());
        XMLUtils.appendChildWithTextNode(document, element, "rating", String.valueOf(entity.getId()));
        return element;
    }

    @Override
    public Movie deserialize(Element element)
    {
        int id = Integer.parseInt(XMLUtils.getTextFromTagName(element, "id"));
        String name = XMLUtils.getTextFromTagName(element, "name");
        String genre = XMLUtils.getTextFromTagName(element, "genre");
        int rating = Integer.parseInt(XMLUtils.getTextFromTagName(element, "rating"));
        return new Movie(id, name, genre, rating);
    }
}
