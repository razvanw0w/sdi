package ro.sdi.lab24.model.serialization.xml;

import org.w3c.dom.Element;

public interface XMLSerializer<T>
{
    Element serialize(T entity);

    T deserialize(Element element);
}
