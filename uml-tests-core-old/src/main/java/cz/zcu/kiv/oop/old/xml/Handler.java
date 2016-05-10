package cz.zcu.kiv.oop.old.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Zpracovani XML souboru pomoci SAX
 *
 * @author Duong Manh Hung, A09B0205P
 */
public class Handler extends DefaultHandler {

  private final int BUFFER_SIZE = 100;
  private final ArrayList<UMLElement> elements = new ArrayList<UMLElement>();
  private final StringBuffer elementValue = new StringBuffer(BUFFER_SIZE);
  private boolean insideElement;

  private int zoomLvl;

  private String type;
  private int x, y, w, h;
  private String panelAttributes;
  private String adtlAttributes;

  // Pri nacteni XML souboru...
  @Override
  public void startDocument() {
    elements.clear();
  }

  // Pri otviracim elementu...
  @Override
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (qName.equals("zoom_level")) {
      // Vyprazdni StringBuffer a ohlas, ze jsme v elementu
      elementValue.setLength(0);
      insideElement = true;
    }
    else if (qName.equals("type")) {
      elementValue.setLength(0);
      insideElement = true;
    }
    else if (qName.equals("x") || qName.equals("y") || qName.equals("w") || qName.equals("h")) {
      elementValue.setLength(0);
      insideElement = true;
    }
    else if (qName.equals("panel_attributes") || qName.equals("additional_attributes")) {
      elementValue.setLength(0);
      insideElement = true;
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    // Uvnitr otviraciho a zaviraciho elementu
    if (insideElement == true) {
      // Pridej do StringBufferu
      elementValue.append(ch, start, length);
    }
  }

  // Při zavíracím elementu...
  @Override
  public void endElement(String uri, String localName, String qName) {
    if (qName.equals("zoom_level")) {
      // Uloz do promenne a ohlas, ze uz nejsem v elementu
      zoomLvl = Integer.parseInt(elementValue.toString());
      insideElement = false;
    }
    else if (qName.equals("type")) {
      type = elementValue.toString();
      insideElement = false;
    }
    else if (qName.equals("x")) {
      x = Integer.parseInt(elementValue.toString());
      insideElement = false;
    }
    else if (qName.equals("y")) {
      y = Integer.parseInt(elementValue.toString());
      insideElement = false;
    }
    else if (qName.equals("w")) {
      w = Integer.parseInt(elementValue.toString());
      insideElement = false;
    }
    else if (qName.equals("h")) {
      h = Integer.parseInt(elementValue.toString());
      insideElement = false;
    }
    else if (qName.equals("panel_attributes")) {
      panelAttributes = elementValue.toString();
      insideElement = false;
    }
    else if (qName.equals("additional_attributes")) {
      adtlAttributes = elementValue.toString();
      insideElement = false;
    }
    else if (qName.equals("element")) {
      elements.add(new UMLElement(type, x, y, w, h, panelAttributes, adtlAttributes));
    }
  }

  /**
   * Ziskani seznamu s elementy
   *
   * @return Seznam vyparsovanych elementu
   */
  public ArrayList<UMLElement> getAllElements() {
    return elements;
  }

  public int getZoomLvl() {
    return zoomLvl;
  }

}
