package ro.sdi.lab24.core.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLUtils
{
    private XMLUtils()
    {
    }

    static String getTextFromTagName(Element parentElement, String tagName)
    {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }

    static void appendChildWithTextNode(Document document, Node parent, String tagName, String content)
    {
        Element element = document.createElement(tagName);
        element.setTextContent(content);
        parent.appendChild(element);
    }
}
