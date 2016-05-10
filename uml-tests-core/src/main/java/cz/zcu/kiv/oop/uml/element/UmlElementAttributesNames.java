package cz.zcu.kiv.oop.uml.element;

/**
 * Names of additional attributes of UML element.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UmlElementAttributesNames {

  /** Name of additional attribute for panel attributes of UML element. */
  String PANEL_ATTRIBUTES = "PANEL_ATTRIBUTES";

  /** Name of additional attribute for additional attributes of UML element. */
  String ADDITIONAL_ATTRIBUTES = "ADDITIONAL_ATTRIBUTES";

  /** Name of additional attribute for element stereotype. */
  String STEREOTYPE = "STEREOTYPE";

  /** Name of additional attribute for foreground of UML element. */
  String FOREGROUND = "FOREGROUND";

  /** Name of additional attribute for background of UML element. */
  String BACKGROUND = "BACKGROUND";

  /** Name of additional attribute for font size of UML element. */
  String FONT_SIZE = "FONT_SIZE";

  /** Name of additional attribute for font which is used for class name. */
  String CLASS_NAME_FONT = "CLASS_NAME_FONT";

  /** Name of additional attribute for list of commented lines. */
  String COMMENTED_LINES = "COMMENTED_LINES";

  /** Name of additional attribute for list of additional text lines of UML element. */
  String ADDITIONAL_LINES = "ADDITIONAL_LINES";

  /**
   * Name of additional attribute for list of extra lines of UML element which are similar to additional lines. This list also
   * contains lines for formatting of UML element.
   */
  String EXTRA_LINES = "EXTRA_LINES";

  /** Name of additional attribute for relation qualifier by class from which leads the relation. */
  String QUALIFIER_FROM = "QUALIFIER_FROM";

  /** Name of additional attribute for relation qualifier by class from which leads the relation. */
  String QUALIFIER_TO = "QUALIFIER_TO";

  /** Name of additional attribute for relation multiplicity by class from which leads the relation. */
  String MULTIPLICITY_FROM = "MULTIPLICITY_FROM";

  /** Name of additional attribute for relation multiplicity by class from which leads the relation. */
  String MULTIPLICITY_TO = "MULTIPLICITY_TO";

  /** Name of additional attribute for relation role by class from which leads the relation. */
  String ROLE_FROM = "ROLE_FROM";

  /** Name of additional attribute for relation role by class into which leads the relation. */
  String ROLE_TO = "ROLE_TO";

}
