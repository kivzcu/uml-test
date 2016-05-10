package cz.zcu.kiv.oop.uxf.version;

import cz.zcu.kiv.oop.properties.keys.StringPropertyKey;

/**
 * Property keys for version reader of UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UxfVersionReaderPropertyKeys {

  /** Property key for name of tag for UXF diagram. */
  StringPropertyKey TAG_DIAGRAM = new StringPropertyKey("uxf.diagram", "diagram");

  /** Property key for parameter which contains version of UXF diagram. */
  StringPropertyKey PARAMETER_VERSION = new StringPropertyKey("uxf.diagram.version", "version");

}
