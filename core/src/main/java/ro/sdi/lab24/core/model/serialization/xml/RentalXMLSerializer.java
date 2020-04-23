package ro.sdi.lab24.core.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.sdi.lab24.core.model.Rental;

import java.time.LocalDateTime;

import static ro.sdi.lab24.core.model.serialization.xml.XMLUtils.appendChildWithTextNode;
import static ro.sdi.lab24.core.model.serialization.xml.XMLUtils.getTextFromTagName;

public class RentalXMLSerializer implements XMLSerializer<Rental>
{

    @Override
    public Element serialize(Document document, Rental entity)
    {
        Element element = document.createElement("rental");
        appendChildWithTextNode(document, element, "movieid", String.valueOf(entity.getId().getMovieId()));
        appendChildWithTextNode(document, element, "clientid", String.valueOf(entity.getId().getClientId()));
        appendChildWithTextNode(document, element, "date", entity.getTime().toString());
        return element;
    }

    @Override
    public Rental deserialize(Element element)
    {
        int movieID = Integer.parseInt(getTextFromTagName(element, "movieid"));
        int clientID = Integer.parseInt(getTextFromTagName(element, "clientid"));
        LocalDateTime date = LocalDateTime.parse(getTextFromTagName(element, "date"));
        return new Rental(movieID, clientID, date);
    }
}
