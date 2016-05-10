package cz.zcu.kiv.oop.uxf.reader;

import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uxf.exception.UxfReaderException;

/**
 * Reader of UXF files which can read UML diagram from UXF files. UXF files could be read by difference methods and also each
 * version of UXF can be different to other versions. From this reasons serves this interface which should be implemented by each
 * UXF reader.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UxfReader {

  /**
   * Reads UML diagram from specified UXF file.
   *
   * @param uxfFile path to UXF file which contains UML diagram.
   * @return Read UML diagram from specified UXF file.
   * @throws UxfReaderException If some error occurs during reading of UXF file.
   */
  public UmlDiagram readDiagram(String uxfFile) throws UxfReaderException;

}
