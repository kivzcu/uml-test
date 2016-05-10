package cz.zcu.kiv.oop.uxf.reader.v11;

import cz.zcu.kiv.oop.uxf.reader.sax.AbstractUxfSaxHandler;
import cz.zcu.kiv.oop.uxf.reader.sax.UxfSaxHandler;

/**
 * SAX Handler for reading UXF files version 11. This handler is same as default SAX handler for reading UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class Uxf11SaxHandler extends AbstractUxfSaxHandler implements UxfSaxHandler {

  /**
   * Initializes properties provider for UXF reader version 11.
   */
  @Override
  protected void initPropertiesProvider() {
    propertiesProvider = Uxf11PropertiesProvider.getInstance();
  }

}
