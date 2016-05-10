package cz.zcu.kiv.oop.uxf.reader.v11;

import cz.zcu.kiv.oop.uxf.reader.UxfReader;
import cz.zcu.kiv.oop.uxf.reader.sax.AbstractUxfSaxReader;
import cz.zcu.kiv.oop.uxf.reader.sax.UxfSaxHandler;

/**
 * Implementation of reader of UXF files version 11. This reader uses SAX for reading of UXF.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfReaderImpl extends AbstractUxfSaxReader implements UxfReader {

  /**
   * Returns SAX handler for reading UXF files version 11. For each reading of UXF is returned new handler.
   *
   * @return SAX handler for reading UXF files version 11.
   */
  @Override
  protected UxfSaxHandler createHandler() {
    return new Uxf11SaxHandler();
  }

}
