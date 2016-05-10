package cz.zcu.kiv.oop.uxf.reader.sax;

import cz.zcu.kiv.oop.properties.keys.StringPropertyKey;

/**
 * Contains property keys used by UXF reader for parsing. All property keys serves for configurable reading UXF of any versions. All
 * properties can have different values for different versions of UXF file.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UxfSaxReaderPropertyKeys {

  /** Property key for tag with zoom level of UML diagram. */
  StringPropertyKey TAG_ZOOM_LEVEL = new StringPropertyKey("uxf.diagram.zoom_level", "zoom_level");

  /** Property key for tag with help text of UXF. */
  StringPropertyKey TAG_HELP_TEXT = new StringPropertyKey("uxf.diagram.help_text", "help_text");

  /** Property key for tag which contains information about UML element. */
  StringPropertyKey TAG_ELEMENT = new StringPropertyKey("uxf.diagram.element", "element");

  /** Property key for tag which contains type of UML element. */
  StringPropertyKey TAG_ELEMENT_TYPE = new StringPropertyKey("uxf.diagram.element.type", "type");

  /** Property key for tag which contains coordinate X of UML element. */
  StringPropertyKey TAG_COORDINATE_X = new StringPropertyKey("uxf.diagram.element.x", "x");

  /** Property key for tag which contains coordinate Y of UML element. */
  StringPropertyKey TAG_COORDINATE_Y = new StringPropertyKey("uxf.diagram.element.y", "y");

  /** Property key for tag which contains width of UML element. */
  StringPropertyKey TAG_COORDINATE_WIDTH = new StringPropertyKey("uxf.diagram.element.w", "w");

  /** Property key for tag which contains height of UML element. */
  StringPropertyKey TAG_COORDINATE_HEIGHT = new StringPropertyKey("uxf.diagram.element.h", "h");

  /** Property key for tag which contains panel attributes of UML element. */
  StringPropertyKey TAG_PANEL_ATTRIBUTES = new StringPropertyKey("uxf.diagram.element.panel_attributes", "panel_attributes");

  /** Property key for tag which contains additional attributes of UML element. */
  StringPropertyKey TAG_ADDITIONAL_ATTRIBUTES = new StringPropertyKey("uxf.diagram.element.additional_attributes", "additional_attributes");

}
