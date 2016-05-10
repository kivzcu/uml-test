package cz.zcu.kiv.oop.uxf.reader.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.exception.InitException;
import cz.zcu.kiv.oop.properties.PropertiesProvider;
import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlElement;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.exception.UmlException;
import cz.zcu.kiv.oop.uml.factory.DefaultUmlElementFactory;
import cz.zcu.kiv.oop.uml.factory.UmlElementFactory;
import cz.zcu.kiv.oop.uml.geometry.UmlLineShape;
import cz.zcu.kiv.oop.uml.geometry.UmlRectangle;
import cz.zcu.kiv.oop.uxf.exception.UxfVersionFormatException;
import cz.zcu.kiv.oop.uxf.version.UxfVersion;
import cz.zcu.kiv.oop.uxf.version.UxfVersionReaderPropertyKeys;

/**
 * Abstract SAX Handler for reading UXF files which contains basic and general implementation of reading handling. All readers using
 * SAX should extends this handler.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public abstract class AbstractUxfSaxHandler extends DefaultHandler implements UxfSaxHandler {

  /** Default zoom level of UXF file. */
  public static final int DEFAULT_ZOOM_LEVEL = 10;

  /** Size of buffer. */
  protected static final int BUFFER_SIZE = 256;

  /** Read UML diagram. */
  protected UmlDiagram umlDiagram;
  /** Read room level of UML diagram. */
  protected int zoomLevel;
  /** Read version of UXF file. */
  protected UxfVersion uxfVersion;
  /** Read help text from UXF file. */
  protected String helpText;

  /** Descriptor of actual read UML element. */
  protected UmlElementDescriptor actualUmlElement;
  /** Information whether will be used buffer for reading characters. */
  protected boolean readCharacters;

  /** Buffer which serves for storing text informations of elements. */
  protected final StringBuilder buffer = new StringBuilder(BUFFER_SIZE);
  /** List of read elements. */
  protected final List<UmlElement> elements = new ArrayList<UmlElement>();

  /** Properties provider which provides properties for handling of UXF reading. */
  protected PropertiesProvider propertiesProvider;
  /** Element provider which provides UML elements. */
  protected UmlElementFactory elementFactory;

  /**
   * Constructs and initializes handler.
   */
  public AbstractUxfSaxHandler() {
    initPropertiesProvider();
    initElementFactory();
  }

  /**
   * Initializes properties provider which provides properties for handling of UXF reading.
   *
   * @throws InitException If some error occurs during provider initialization.
   */
  protected abstract void initPropertiesProvider() throws InitException;

  /**
   * Initializes element provider.
   */
  protected void initElementFactory() {
    elementFactory = new DefaultUmlElementFactory(propertiesProvider);
  }

  /**
   * Action before start of XML file which clears read elements.
   */
  @Override
  public void startDocument() {
    elements.clear();
  }

  /**
   * Action after end of XML file which links relations.
   */
  @Override
  public void endDocument() {
    linkRelations();

    umlDiagram = new UmlDiagram(elements);
  }

  /**
   * Links relations - sets from and into which class leads relations.
   */
  protected void linkRelations() {
    List<UmlRelation> relations = new ArrayList<UmlRelation>();
    List<UmlClass> classes = new ArrayList<UmlClass>();

    for (UmlElement element : elements) {
      if (element instanceof UmlRelation) {
        relations.add((UmlRelation)element);
      }
      else if (element instanceof UmlClass) {
        classes.add((UmlClass)element);
      }
    }

    for (UmlRelation relation : relations) {
      UmlLineShape line = relation.getRelationLine();
      UmlClass from = null;
      UmlClass to = null;
      for (UmlClass clazz : classes) {
        UmlRectangle rectangle = clazz.getCoordinates();
        if (rectangle.contains(line.getStartPoint()) && from == null) {
          from = clazz;
        }
        else if (rectangle.contains(line.getEndPoint()) && to == null) {
          to = clazz;
        }

        if (from != null && to != null) {
          break;
        }
      }

      switch (relation.getRelationOrientationType()) {
        case LEFT :
          relation.setFrom(to);
          relation.setTo(from);
          break;

        case RIGHT :
        case UNI :
        case UNDEFINED :
          relation.setFrom(from);
          relation.setTo(to);
          break;
      }
    }
  }

  /**
   * Handling of start of XML element. Primary cleans correct structure before storing read data.
   *
   * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being
   *          performed.
   * @param localName The local name (without prefix), or the empty string if Namespace processing is not being performed.
   * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
   * @param atts The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
   */
  @Override
  public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
    if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_ELEMENT))) {
      actualUmlElement = new UmlElementDescriptor();
      actualUmlElement.setZoomFactor(zoomLevel / (double)DEFAULT_ZOOM_LEVEL);
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfVersionReaderPropertyKeys.TAG_DIAGRAM))) {
      try {
        String version = atts.getValue(propertiesProvider.getValue(UxfVersionReaderPropertyKeys.PARAMETER_VERSION));
        uxfVersion = UxfVersion.parseVersion(version);
      }
      catch (UxfVersionFormatException exc) {
        throw new SAXException(exc);
      }
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_ZOOM_LEVEL))) {
      zoomLevel = DEFAULT_ZOOM_LEVEL;
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_HELP_TEXT))) {
      helpText = null;
    }

    clearBufferBeforeRead();
  }

  /**
   * Clears buffer before reading characters from element.
   */
  protected void clearBufferBeforeRead() {
    readCharacters = true;
    buffer.delete(0, buffer.length());
  }

  /**
   * Reads characters from actual XML element.
   *
   * @param ch The characters.
   * @param start The start position in the character array.
   * @param length The number of characters to use from the character array.
   */
  @Override
  public void characters(char ch[], int start, int length) {
    if (readCharacters) {
      buffer.append(ch, start, length);
    }
  }

  /**
   * Handling of end of XML element. Primary stores read characters into correct structure.
   *
   * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being
   *          performed.
   * @param localName The local name (without prefix), or the empty string if Namespace processing is not being performed.
   * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
   * @throws SAXException If some error occurs during reading UML elements from UXF file.
   */
  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_ELEMENT))) {
      try {
        UmlElement umlElement = elementFactory.getUmlElement(actualUmlElement);
        if (umlElement != null) {
          elements.add(umlElement);
        }
        actualUmlElement = null;
      }
      catch (UmlException exc) {
        throw new SAXException(exc);
      }

      return;
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfVersionReaderPropertyKeys.TAG_DIAGRAM))) {
      return;
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_ZOOM_LEVEL))) {
      zoomLevel = getIntegerValueFromBuffer(qName);
      return;
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_HELP_TEXT))) {
      helpText = buffer.toString();
      return;
    }

    if (actualUmlElement == null) {
      throw new SAXException(Strings.getFormatted("exc.uxf.element-wrong-location", qName));
    }

    // properties of element
    if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_ELEMENT_TYPE))) {
      actualUmlElement.setElementType(buffer.toString());
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_COORDINATE_X))) {
      actualUmlElement.setX(getDoubleValueFromBuffer(qName));
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_COORDINATE_Y))) {
      actualUmlElement.setY(getDoubleValueFromBuffer(qName));
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_COORDINATE_WIDTH))) {
      actualUmlElement.setWidth(getDoubleValueFromBuffer(qName));
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_COORDINATE_HEIGHT))) {
      actualUmlElement.setHeight(getDoubleValueFromBuffer(qName));
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_PANEL_ATTRIBUTES))) {
      actualUmlElement.setPanelAttributes(buffer.toString());
    }
    else if (qName.equalsIgnoreCase(propertiesProvider.getValue(UxfSaxReaderPropertyKeys.TAG_ADDITIONAL_ATTRIBUTES))) {
      actualUmlElement.setAdditionalAttributes(buffer.toString());
    }
  }

  /**
   * Parses integer value which is stored in buffer.
   *
   * @return Parsed integer value of string stored in buffer.
   * @throws SAXException If integer value is in wrong format.
   */
  protected int getIntegerValueFromBuffer() throws SAXException {
    return getIntegerValueFromBuffer(null);
  }

  /**
   * Parses integer value which is stored in buffer.
   *
   * @param elementName name of element which contains integer value.
   * @return Parsed integer value of string stored in buffer.
   * @throws SAXException If integer value is in wrong format.
   */
  protected int getIntegerValueFromBuffer(String elementName) throws SAXException {
    String bufferValue = buffer.toString();
    try {
      return Integer.parseInt(bufferValue);
    }
    catch (NumberFormatException exc) {
      throw new SAXException(Strings.getFormatted("exc.uxf.number-format", bufferValue, elementName), exc);
    }
  }

  /**
   * Parses double value which is stored in buffer.
   *
   * @return Parsed double value of string stored in buffer.
   * @throws SAXException If double value is in wrong format.
   */
  protected double getDoubleValueFromBuffer() throws SAXException {
    return getDoubleValueFromBuffer(null);
  }

  /**
   * Parses double value which is stored in buffer.
   *
   * @param elementName name of element which contains double value.
   * @return Parsed double value of string stored in buffer.
   * @throws SAXException If double value is in wrong format.
   */
  protected double getDoubleValueFromBuffer(String elementName) throws SAXException {
    String bufferValue = buffer.toString();
    try {
      return Double.valueOf(bufferValue);
    }
    catch (NumberFormatException exc) {
      throw new SAXException(Strings.getFormatted("exc.uxf.number-format", bufferValue, elementName), exc);
    }
  }

  /**
   * Returns read UML diagram from UXF file.
   *
   * @return Read UML diagram.
   */
  @Override
  public UmlDiagram getUmlDiagram() {
    return umlDiagram;
  }

  /**
   * Returns read zoom level of UML diagram.
   *
   * @return Read zoom level.
   */
  @Override
  public int getZoomLevel() {
    return zoomLevel;
  }

  /**
   * Returns read version of UXF file.
   *
   * @return Read UXF version.
   */
  @Override
  public UxfVersion getUxfVersion() {
    return uxfVersion;
  }

  /**
   * Returns read help text from UXF file.
   *
   * @return Read help text.
   */
  @Override
  public String getHelpText() {
    return helpText;
  }

}
