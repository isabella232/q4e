package org.devzuz.q.maven.jdt.ui.pomeditor.pomreader;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;



public class MavenNameSpaceContext
    implements NamespaceContext
{

    public String getNamespaceURI(String prefix) {
        if (prefix == null) throw new NullPointerException("Null prefix");
        else if ("pre".equals(prefix)) return "http://maven.apache.org/POM/4.0.0";
        else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
        return XMLConstants.NULL_NS_URI;
    }

    public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }

    // This method isn't necessary for XPath processing.
    public Iterator getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }

}
