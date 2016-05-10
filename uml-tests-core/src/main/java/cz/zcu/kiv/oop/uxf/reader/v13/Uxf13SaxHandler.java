package cz.zcu.kiv.oop.uxf.reader.v13;

import org.xml.sax.SAXException;

import cz.zcu.kiv.oop.uml.factory.Uxf13UmlElementFactory;
import cz.zcu.kiv.oop.uxf.reader.sax.AbstractUxfSaxHandler;
import cz.zcu.kiv.oop.uxf.reader.sax.UxfSaxHandler;

/**
 * SAX Handler for reading UXF files version 13. Main difference between version 11 and 13 is tag "id" which contains type of UML
 * element. Also the mapping of relation is different.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class Uxf13SaxHandler extends AbstractUxfSaxHandler implements UxfSaxHandler {

  /**
   * Initializes properties provider for UXF reader version 13.
   */
  @Override
  protected void initPropertiesProvider() {
    propertiesProvider = Uxf13PropertiesProvider.getInstance();
  }

  /**
   * Initializes element provider for UXF reader version 13.
   */
  @Override
  protected void initElementFactory() {
    elementFactory = new Uxf13UmlElementFactory(propertiesProvider);
  }

  /**
   * Handling of end of XML element. Primary stores read characters into correct structure. This is only extension of handler
   * version 11 where is tag "id" read into type of UML element.
   *
   * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being
   *          performed.
   * @param localName The local name (without prefix), or the empty string if Namespace processing is not being performed.
   * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
   * @throws SAXException If some error occurs during reading UML elements from UXF file.
   */
  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    super.endElement(uri, localName, qName);

    if (qName.equalsIgnoreCase(propertiesProvider.getValue(Uxf13SaxReaderPropertyKeys.TAG_ELEMENT_ID))) {
      actualUmlElement.setElementType(buffer.toString());
    }
  }

}
