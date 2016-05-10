package cz.zcu.kiv.oop.uml.factory;

import cz.zcu.kiv.oop.properties.keys.CharacterPropertyKey;
import cz.zcu.kiv.oop.properties.keys.EnumPropertyKey;
import cz.zcu.kiv.oop.properties.keys.StringPropertyKey;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElementType;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;

/**
 * Property keys for default implementation factory of UML elements.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface DefaultUmlElementFactoryPropertyKeys {

  /** Property key for prefix of commented line of panel attributes of UML element. */
  StringPropertyKey ELEMENT_COMMENT_PREFIX = new StringPropertyKey("uxf.diagram.element.comment.prefix", "//");

  /** Property key for prefix of line which defines font size in panel attributes of UML element. */
  StringPropertyKey ELEMENT_FONT_SIZE_PREFIX = new StringPropertyKey("uxf.diagram.element.fontsize.prefix", "fontsize=");
  /** Property key for prefix of line which defines background color in panel attributes of UML element. */
  StringPropertyKey ELEMENT_BACKGROUND_PREFIX = new StringPropertyKey("uxf.diagram.element.background.prefix", "bg=");
  /** Property key for prefix of line which defines foreground color in panel attributes of UML element. */
  StringPropertyKey ELEMENT_FOREGROUND_PREFIX = new StringPropertyKey("uxf.diagram.element.foreground.prefix", "fg=");

  /** Property key for prefix of stereotype. */
  StringPropertyKey STEREOTYPE_PREFIX = new StringPropertyKey("uxf.diagram.element.stereotype.prefix", "<<");
  /** Property key for suffix of stereotype. */
  StringPropertyKey STEREOTYPE_SUFFIX = new StringPropertyKey("uxf.diagram.element.stereotype.suffix", ">>");

  /** Property key for delimiter of italic font for class name. */
  CharacterPropertyKey ITALIC_FONT_DELIMITER = new CharacterPropertyKey("uxf.diagram.element.font.italic.delimiter", '/');
  /** Property key for delimiter of bold font for class name. */
  CharacterPropertyKey BOLD_FONT_DELIMITER = new CharacterPropertyKey("uxf.diagram.element.font.bold.delimiter", '*');
  /** Property key for delimiter of underlined font for class name. */
  CharacterPropertyKey UNDERLINE_FONT_DELIMITER = new CharacterPropertyKey("uxf.diagram.element.font.underline.delimiter", '_');

  /** Prefix of mapping property for UML class type. */
  String CLASS_TYPE_PROPETY_PREFIX = "uxf.diagram.element.class.type.";
  /** Prefix of mapping property for UML relation type. */
  String RELATION_TYPE_PROPERTY_PREFIX = "uxf.diagram.element.relation.";
  /** Prefix of mapping property for UML relation orientation type. */
  String RELATION_ORIENTATION_TYPE_PROPERTY_PREFIX = "uxf.diagram.element.relation.orientation.";
  /** Suffix of mapping property for UML relation types. */
  String RELATION_PROPERTY_SUFFIX = ".type";
  /** Separator of arrows in mapping property for UML relation types. */
  String RELATION_ARROWS_SEPARATOR = "|";

  /** Property key for separator of relation coordinates. */
  StringPropertyKey RELATION_COORDINATES_SEPARATOR = new StringPropertyKey("uxf.diagram.element.relation.coordinates.separator", ";");

  /** Property key for prefix of relation type in panel attributes of UML relation. */
  StringPropertyKey RELATION_TYPE_PREFIX = new StringPropertyKey("uxf.diagram.element.relation.type.prefix", "lt=");
  /** Property key for prefix of relation first qualifier in panel attributes of UML relation. */
  StringPropertyKey RELATION_QUALIFIER_1_PREFIX = new StringPropertyKey("uxf.diagram.element.relation.qualifier.1.prefix", "q1=");
  /** Property key for prefix of relation second qualifier in panel attributes of UML relation. */
  StringPropertyKey RELATION_QUALIFIER_2_PREFIX = new StringPropertyKey("uxf.diagram.element.relation.qualifier.2.prefix", "q2=");
  /** Property key for prefix of relation first multiplicity in panel attributes of UML relation. */
  StringPropertyKey RELATION_MULTIPLICITY_1_PREFIX = new StringPropertyKey("uxf.diagram.element.relation.multiplicity.1.prefix", "m1=");
  /** Property key for prefix of relation second multiplicity in panel attributes of UML relation. */
  StringPropertyKey RELATION_MULTIPLICITY_2_PREFIX = new StringPropertyKey("uxf.diagram.element.relation.multiplicity.2.prefix", "m2=");
  /** Property key for prefix of relation first role in panel attributes of UML relation. */
  StringPropertyKey RELATION_ROLE_1_PREFIX = new StringPropertyKey("uxf.diagram.element.relation.role.1.prefix", "r1=");
  /** Property key for prefix of relation second role in panel attributes of UML relation. */
  StringPropertyKey RELATION_ROLE_2_PREFIX = new StringPropertyKey("uxf.diagram.element.relation.role.2.prefix", "r2=");

  /** Default type of relation. */
  UmlRelationTypePropertyKey DEFAULT_RELATION_TYPE = new UmlRelationTypePropertyKey("uxf.diagram.element.relation.type.default", UmlRelationType.ASSOCIATION);
  /** Default orientation type of relation. */
  UmlRelationOrientationTypePropertyKey DEFAULT_RELATION_ORIENTATION_TYPE = new UmlRelationOrientationTypePropertyKey(
      "uxf.diagram.element.relation.orientation.type.default", UmlRelationOrientationType.UNI);

  /**
   * Enumeration property key for {@link UmlElementType}.
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  class UmlElementTypePropertyKey extends EnumPropertyKey<UmlElementType> {

    /**
     * Constructs enumeration property key.
     *
     * @param key name (key) of property.
     * @param defaultValue default value of property.
     */
    public UmlElementTypePropertyKey(String key, UmlElementType defaultValue) {
      super(key, defaultValue);
    }
  }

  /**
   * Enumeration property key for {@link UmlClassType}.
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  class UmlClassTypePropertyKey extends EnumPropertyKey<UmlClassType> {

    /**
     * Constructs enumeration property key.
     *
     * @param key name (key) of property.
     * @param defaultValue default value of property.
     */
    public UmlClassTypePropertyKey(String key, UmlClassType defaultValue) {
      super(key, defaultValue);
    }
  }

  /**
   * Enumeration property key for {@link UmlRelationType}.
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  class UmlRelationTypePropertyKey extends EnumPropertyKey<UmlRelationType> {

    /**
     * Constructs enumeration property key.
     *
     * @param key name (key) of property.
     * @param defaultValue default value of property.
     */
    public UmlRelationTypePropertyKey(String key, UmlRelationType defaultValue) {
      super(key, defaultValue);
    }
  }

  /**
   * Enumeration property key for {@link UmlRelationOrientationType}.
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  class UmlRelationOrientationTypePropertyKey extends EnumPropertyKey<UmlRelationOrientationType> {

    /**
     * Constructs enumeration property key.
     *
     * @param key name (key) of property.
     * @param defaultValue default value of property.
     */
    public UmlRelationOrientationTypePropertyKey(String key, UmlRelationOrientationType defaultValue) {
      super(key, defaultValue);
    }
  }

}
