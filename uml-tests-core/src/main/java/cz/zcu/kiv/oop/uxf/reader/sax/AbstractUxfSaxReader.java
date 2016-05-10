package cz.zcu.kiv.oop.uxf.reader.sax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uxf.exception.UxfReaderException;
import cz.zcu.kiv.oop.uxf.reader.UxfReader;

/**
 * Abstract implementation of reader of UXF files version using SAX for reading of UXF. This reader could be ancestor of all UXF
 * readers (for any version). Main logic of reader is implemented but there is important to extends method {@link #createHandler()}
 * which provides handler for handling reading of UXF.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public abstract class AbstractUxfSaxReader implements UxfReader {

  /** Factory which provides SAX parsers which are used for parsing UXF files. */
  protected static final SAXParserFactory spf = SAXParserFactory.newInstance();

  /**
   * Static constructor which initializes validating of UXF file.
   */
  {
    spf.setValidating(false);
  }

  /**
   * Reads UML diagram from specified UXF file.
   *
   * @param uxfFile path to UXF file which contains UML diagram.
   * @return Read UML diagram from specified UXF file.
   * @throws UxfReaderException If some error occurs during reading of UXF file.
   */
  @Override
  public UmlDiagram readDiagram(String uxfFile) throws UxfReaderException {
    if (!StringUtils.hasText(uxfFile)) {
      throw new UxfReaderException(Strings.get("exc.uxf.empty-filename"));
    }

    XMLReader xmlReader = null;
    try {
      SAXParser saxParser = spf.newSAXParser();
      xmlReader = saxParser.getXMLReader();
    }
    catch (ParserConfigurationException exc) {
      throw new UxfReaderException(Strings.get("exc.uxf.cannot-create-SAX-parser"), exc);
    }
    catch (SAXException exc) {
      throw new UxfReaderException(Strings.get("exc.uxf.cannot-create-XML-reader"), exc);
    }

    UxfSaxHandler uxfSaxHandler = createHandler();
    xmlReader.setContentHandler(uxfSaxHandler);

    InputStream is = null;
    try {
      is = new FileInputStream(new File(uxfFile));
      xmlReader.parse(new InputSource(is));
    }
    catch (FileNotFoundException exc) {
      throw new UxfReaderException(Strings.getFormatted("exc.uxf.uxf-file-not-found", uxfFile), exc);
    }
    catch (IOException exc) {
      throw new UxfReaderException(Strings.get("exc.uxf.io-exception"), exc);
    }
    catch (SAXException exc) {
      throw new UxfReaderException(Strings.get("exc.uxf.sax-exception"), exc);
    }
    finally {
      if (is != null) {
        try {
          is.close();
        }
        catch (IOException exc) {
          // this is not important...
        }
      }
    }

    return uxfSaxHandler.getUmlDiagram();
  }

  /**
   * Returns SAX handler for reading UXF files. For each reading of UXF is returned new handler.
   *
   * @return SAX handler for reading UXF files.
   */
  protected abstract UxfSaxHandler createHandler();

}
