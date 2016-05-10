package cz.zcu.kiv.oop.uxf.reader.v13;

import cz.zcu.kiv.oop.properties.keys.StringPropertyKey;
import cz.zcu.kiv.oop.uxf.reader.sax.UxfSaxReaderPropertyKeys;

/**
 * Property keys for reader of UXF files version 13.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface Uxf13SaxReaderPropertyKeys extends UxfSaxReaderPropertyKeys {

  /** Property key for name of tag for id of UML element (id of element is same as type of element). */
  StringPropertyKey TAG_ELEMENT_ID = new StringPropertyKey("uxf.diagram.element.id", "id");

}
