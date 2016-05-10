package cz.zcu.kiv.oop.uxf.reader.sax;

import org.xml.sax.ContentHandler;

import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uxf.version.UxfVersion;

/**
 * Handler of SAX reader which reads UML diagram from UXF file. Handler serves for reading all tags and parameters from UXF file (in
 * XML) and reading data of UML diagram.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UxfSaxHandler extends ContentHandler {

  /**
   * Returns read UML diagram from UXF file.
   *
   * @return Read UML diagram.
   */
  public UmlDiagram getUmlDiagram();

  /**
   * Returns read zoom level of UML diagram.
   *
   * @return Read zoom level.
   */
  public int getZoomLevel();

  /**
   * Returns read version of UXF file.
   *
   * @return Read UXF version.
   */
  public UxfVersion getUxfVersion();

  /**
   * Returns read help text from UXF file.
   *
   * @return Read help text.
   */
  public String getHelpText();

}
