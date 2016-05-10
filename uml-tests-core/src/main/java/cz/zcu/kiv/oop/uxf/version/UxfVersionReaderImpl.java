package cz.zcu.kiv.oop.uxf.version;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.properties.PropertiesProvider;
import cz.zcu.kiv.oop.uxf.exception.UxfVersionReaderException;

/**
 * Singleton implementation of reader which serves for reading version from UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfVersionReaderImpl implements UxfVersionReader {

  /** Singleton instance of this version reader. */
  protected static UxfVersionReader instance;

  /** Properties provider for this version reader. */
  protected final PropertiesProvider propertiesProvider = UxfVersionPropertiesProvider.getInstance();

  /**
   * Private constructor which makes from this class the singleton.
   */
  private UxfVersionReaderImpl() {}

  /**
   * Returns read version of UXF file.
   *
   * @param uxfFile path to uxf file from which will be read the version.
   * @return Read version of UXF file.
   * @throws UxfVersionReaderException If some error occurs during reading of UXF version.
   */
  @Override
  public UxfVersion readVersion(String uxfFile) throws UxfVersionReaderException {
    if (!StringUtils.hasText(uxfFile)) {
      throw new UxfVersionReaderException(Strings.get("exc.uxf.empty-filename"));
    }

    XMLInputFactory factory = XMLInputFactory.newInstance();

    InputStream is = null;
    BufferedInputStream bis = null;
    InputStreamReader isr = null;
    XMLStreamReader xmlStreamReader = null;
    // If will be used java version 1.7, there could be used try-catch with handled resources
    try {
      is = new FileInputStream(uxfFile);
      bis = new BufferedInputStream(is);
      isr = new InputStreamReader(bis, Charset.forName("UTF-8"));
      xmlStreamReader = factory.createXMLStreamReader(isr);

      return readUxfVersion(xmlStreamReader);
    }
    catch (FileNotFoundException exc) {
      throw new UxfVersionReaderException(Strings.getFormatted("exc.uxf.uxf-file-not-found", uxfFile), exc);
    }
    catch (XMLStreamException exc) {
      throw new UxfVersionReaderException(Strings.get("exc.uxf.io-exception"), exc);
    }
    finally {
      if (xmlStreamReader != null) {
        try {
          xmlStreamReader.close();
        }
        catch (XMLStreamException exc) {
          // closing reader is not important for re-throwing of exception.
        }
      }

      closeResource(isr);
      closeResource(bis);
      closeResource(is);
    }
  }

  /**
   * Helper method for closing close-able resources.
   *
   * @param closeable close-able resource which will be closed.
   */
  protected void closeResource(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      }
      catch (IOException exc) {
        // closing source is not important for re-throwing of exception.
      }
    }
  }

  /**
   * Reads version from specified XML reader of UXF file. The version is stored in UXF file in this tag:
   *
   * <pre>
   * &lt;diagram program="umlet" version="major.minor.patch">
   * </pre>
   *
   * @param xmlStreamReader reader of XML files which reads UXF file.
   * @return Read version of UXF file.
   * @throws XMLStreamException If some error occurs during reading of UXF file.
   */
  protected UxfVersion readUxfVersion(XMLStreamReader xmlStreamReader) throws XMLStreamException {
    boolean breakReading = false;
    String diagramTagName = propertiesProvider.getValue(UxfVersionReaderPropertyKeys.TAG_DIAGRAM);
    String versionParameterName = propertiesProvider.getValue(UxfVersionReaderPropertyKeys.PARAMETER_VERSION);
    while (xmlStreamReader.hasNext() && !breakReading) {
      int event = xmlStreamReader.next();
      if (event == XMLStreamConstants.START_ELEMENT) {
        if (diagramTagName.equals(xmlStreamReader.getLocalName())) {
          for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
            String attributeName = xmlStreamReader.getAttributeLocalName(i);
            if (versionParameterName.equals(attributeName)) {
              return UxfVersion.parseVersion(xmlStreamReader.getAttributeValue(i));
            }
          }
        }
      }
    }

    return null;
  }

  /**
   * Returns singleton instance of this version reader.
   *
   * @return Singleton instance of this version reader.
   */
  public static UxfVersionReader getInstance() {
    if (instance == null) {
      instance = new UxfVersionReaderImpl();
    }

    return instance;
  }

}
