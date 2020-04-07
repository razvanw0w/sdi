package ro.sdi.lab.common.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDateTime;

import ro.sdi.lab.common.serialization.XMLSerializer;
import ro.sdi.lab.common.model.Rental;

public class RentalXMLSerializer implements XMLSerializer<Rental>
{

    @Override
    public Element serialize(Document document, Rental entity)
    {
        Element element = document.createElement("rental");
        XMLUtils.appendChildWithTextNode(document, element, "movieid", String.valueOf(entity.getId().getMovieId()));
        XMLUtils.appendChildWithTextNode(document, element, "clientid", String.valueOf(entity.getId().getClientId()));
        XMLUtils.appendChildWithTextNode(document, element, "date", entity.getTime().toString());
        return element;
    }

    @Override
    public Rental deserialize(Element element)
    {
        int movieID = Integer.parseInt(XMLUtils.getTextFromTagName(element, "movieid"));
        int clientID = Integer.parseInt(XMLUtils.getTextFromTagName(element, "clientid"));
        LocalDateTime date = LocalDateTime.parse(XMLUtils.getTextFromTagName(element, "date"));
        return new Rental(movieID, clientID, date);
    }
}
