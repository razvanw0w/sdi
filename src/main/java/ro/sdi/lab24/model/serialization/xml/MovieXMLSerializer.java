package ro.sdi.lab24.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.sdi.lab24.model.Movie;

public class MovieXMLSerializer implements XMLSerializer<Movie> {
    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }

    private void appendChildWithTextNode(Document document, Node parent, String tagName, String content) {
        Element element = document.createElement(tagName);
        element.setTextContent(content);
        parent.appendChild(element);
    }

    @Override
    public Element serialize(Document document, Movie entity) {
        Element element = document.createElement("movie");
        appendChildWithTextNode(document, element, "id", String.valueOf(entity.getId()));
        appendChildWithTextNode(document, element, "name", entity.getName());
        appendChildWithTextNode(document, element, "genre", entity.getGenre());
        appendChildWithTextNode(document, element, "rating", String.valueOf(entity.getId()));
        return element;
    }

    @Override
    public Movie deserialize(Element element) {
        int id = Integer.parseInt(getTextFromTagName(element, "id"));
        String name = getTextFromTagName(element, "name");
        String genre = getTextFromTagName(element, "genre");
        int rating = Integer.parseInt(getTextFromTagName(element, "rating"));
        return new Movie(id, name, genre, rating);
    }
}
