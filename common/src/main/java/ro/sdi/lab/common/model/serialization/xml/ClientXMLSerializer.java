package ro.sdi.lab.common.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ro.sdi.lab.common.serialization.XMLSerializer;
import ro.sdi.lab.common.model.Client;

public class ClientXMLSerializer implements XMLSerializer<Client>
{

    @Override
    public Element serialize(Document document, Client entity)
    {
        Element element = document.createElement("client");
        XMLUtils.appendChildWithTextNode(document, element, "id", String.valueOf(entity.getId()));
        XMLUtils.appendChildWithTextNode(document, element, "name", entity.getName());
        return element;
    }

    @Override
    public Client deserialize(Element element)
    {
        int id = Integer.parseInt(XMLUtils.getTextFromTagName(element, "id"));
        String name = XMLUtils.getTextFromTagName(element, "name");
        return new Client(id, name);
    }
}
