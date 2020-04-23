package ro.sdi.lab24.core.model.serialization.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XMLSerializer<T>
{
    Element serialize(Document document, T entity);

    T deserialize(Element element);
}
