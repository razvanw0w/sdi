package ro.sdi.lab24.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.sdi.lab24.model.Rental;

import java.time.LocalDateTime;

public class RentalXMLSerializer implements XMLSerializer<Rental> {
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
    public Element serialize(Document document, Rental entity) {
        Element element = document.createElement("rental");
        appendChildWithTextNode(document, element, "movieid", String.valueOf(entity.getId().getMovieId()));
        appendChildWithTextNode(document, element, "clientid", String.valueOf(entity.getId().getClientId()));
        appendChildWithTextNode(document, element, "date", entity.getTime().toString());
        return element;
    }

    @Override
    public Rental deserialize(Element element) {
        int movieID = Integer.parseInt(getTextFromTagName(element, "movieid"));
        int clientID = Integer.parseInt(getTextFromTagName(element, "clientid"));
        LocalDateTime date = LocalDateTime.parse(getTextFromTagName(element, "date"));
        return new Rental(movieID, clientID, date);
    }
}
