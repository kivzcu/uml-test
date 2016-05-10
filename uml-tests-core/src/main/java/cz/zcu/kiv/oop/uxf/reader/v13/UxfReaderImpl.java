package cz.zcu.kiv.oop.uxf.reader.v13;

import cz.zcu.kiv.oop.uxf.reader.UxfReader;
import cz.zcu.kiv.oop.uxf.reader.sax.AbstractUxfSaxReader;
import cz.zcu.kiv.oop.uxf.reader.sax.UxfSaxHandler;

/**
 * Implementation of reader of UXF files version 13. This reader uses SAX for reading of UXF. Main difference between version 11 and
 * 13 is tag "id" which contains type of UML element.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfReaderImpl extends AbstractUxfSaxReader implements UxfReader {

  /**
   * Returns SAX handler for reading UXF files version 13. For each reading of UXF is returned new handler.
   *
   * @return SAX handler for reading UXF files version 13.
   */
  @Override
  protected UxfSaxHandler createHandler() {
    return new Uxf13SaxHandler();
  }

}
