package cz.zcu.kiv.oop.old.xml;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Ridi parsovani XML a vraci seznam elementu
 *
 * @author Duong Manh Hung, A09B0205P
 */
public class UMLReaderSax implements IUMLReader {

  private final Handler handler;

  public UMLReaderSax(String filename) throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setValidating(false);

    SAXParser sp = spf.newSAXParser();
    XMLReader parser = sp.getXMLReader();

    handler = new Handler();
    parser.setContentHandler(handler);
    parser.parse(filename);
  }

  @Override
  public ArrayList<UMLElement> getAllElements() {
    return handler.getAllElements();
  }

  @Override
  public int getZoomLvl() {
    return handler.getZoomLvl();
  }

}
