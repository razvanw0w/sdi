package ro.sdi.lab24.core.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.sdi.lab24.core.model.Client;

import static ro.sdi.lab24.core.model.serialization.xml.XMLUtils.appendChildWithTextNode;
import static ro.sdi.lab24.core.model.serialization.xml.XMLUtils.getTextFromTagName;

public class ClientXMLSerializer implements XMLSerializer<Client>
{

    @Override
    public Element serialize(Document document, Client entity)
    {
        Element element = document.createElement("client");
        appendChildWithTextNode(document, element, "id", String.valueOf(entity.getId()));
        appendChildWithTextNode(document, element, "name", entity.getName());
        return element;
    }

    @Override
    public Client deserialize(Element element)
    {
        int id = Integer.parseInt(getTextFromTagName(element, "id"));
        String name = getTextFromTagName(element, "name");
        return new Client(id, name);
    }
}
