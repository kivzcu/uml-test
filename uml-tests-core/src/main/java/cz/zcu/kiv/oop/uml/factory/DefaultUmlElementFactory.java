package cz.zcu.kiv.oop.uml.factory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.properties.PropertiesProvider;
import cz.zcu.kiv.oop.properties.keys.EnumPropertyKey;
import cz.zcu.kiv.oop.properties.keys.StringPropertyKey;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElement;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributes;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributesNames;
import cz.zcu.kiv.oop.uml.element.UmlElementType;
import cz.zcu.kiv.oop.uml.element.UmlFont;
import cz.zcu.kiv.oop.uml.element.UmlNote;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uml.exception.UmlElementException;
import cz.zcu.kiv.oop.uml.factory.DefaultUmlElementFactoryPropertyKeys.UmlClassTypePropertyKey;
import cz.zcu.kiv.oop.uml.factory.DefaultUmlElementFactoryPropertyKeys.UmlElementTypePropertyKey;
import cz.zcu.kiv.oop.uml.geometry.UmlLineShape;
import cz.zcu.kiv.oop.uml.geometry.UmlPolyline;
import cz.zcu.kiv.oop.uml.geometry.UmlRectangle;
import cz.zcu.kiv.oop.uxf.reader.sax.UmlElementDescriptor;

/**
 * Default implementation of UML elements factory. This implementation of factory could be used by UXF reader for creating new UML
 * elements from read descriptors.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class DefaultUmlElementFactory implements UmlElementFactory {

  /** Provider which provides properties for factoring UML elements and their properties. */
  protected final PropertiesProvider propertiesProvider;

  /** Cache of properties for mapping of relation types. */
  protected Map<String, List<String>> relationTypePropertiesCache;
  /** Cache of properties for mapping of relation orientation types. */
  protected Map<String, List<String>> relationOrientationTypePropertiesCache;

  /**
   * Constructs factory.
   *
   * @param propertiesProvider provider which provides properties for factoring UML elements and their properties.
   */
  public DefaultUmlElementFactory(PropertiesProvider propertiesProvider) {
    this.propertiesProvider = propertiesProvider;
    init();
  }

  /**
   * Initializes factory. This method is called at the end of factory construction.
   */
  protected void init() {
    relationTypePropertiesCache = new HashMap<String, List<String>>();
    relationOrientationTypePropertiesCache = new HashMap<String, List<String>>();

    initCache(DefaultUmlElementFactoryPropertyKeys.RELATION_TYPE_PROPERTY_PREFIX, relationTypePropertiesCache);
    initCache(DefaultUmlElementFactoryPropertyKeys.RELATION_ORIENTATION_TYPE_PROPERTY_PREFIX, relationOrientationTypePropertiesCache);
  }

  /**
   * Initialize cache of properties for mapping of some enumeration type. The mapping uses these rules (example):
   * <ol>
   * <li>create property for arrow symbols as:<br />
   * <tt>uxf.diagram.element.relation.# = list of arrow symbols for relation separated by |</tt></li>
   * <li>create relation type for arrow symbols as:<br />
   * <tt>uxf.diagram.element.relation.#.type = name of UMLRelationType field</tt></li>
   * </ol>
   * <p>
   * In this method is processed first property for mapping properties to values.
   *
   * @param propertyPrefix prefix of mapping properties.
   * @param cache cache which will be initialized.
   */
  protected void initCache(String propertyPrefix, Map<String, List<String>> cache) {
    int i = 1;
    while (propertiesProvider.getProperty(propertyPrefix + i) != null) {
      StringPropertyKey.List propertyKeyList = new StringPropertyKey.List(propertyPrefix + i, DefaultUmlElementFactoryPropertyKeys.RELATION_ARROWS_SEPARATOR);
      List<String> arrows = propertiesProvider.getListValue(propertyKeyList);
      cache.put(propertyPrefix + i, arrows);
      i++;
    }
  }

  /**
   * Returns mapped value of enumeration type for entered value. The mapping uses these rules (example):
   * <ol>
   * <li>create property for arrow symbols as:<br />
   * <tt>uxf.diagram.element.relation.# = list of arrow symbols for relation separated by |</tt></li>
   * <li>create relation type for arrow symbols as:<br />
   * <tt>uxf.diagram.element.relation.#.type = name of UMLRelationType field</tt></li>
   * </ol>
   * <p>
   * In this method is found the correct property for entered value. After that is used second property where is used the suffix
   * which will be connected after found property name and then will be used the value of this property for getting value of
   * enumeration.
   *
   * @param typeClass class type of enumeration type which will be returned.
   * @param value value for which will be returned the correct value of enumeration type.
   * @param propertySuffix suffix of property name which maps value of enumeration for entered value.
   * @param cache cache which contains mapping of types.
   * @return Value of mapped enumeration type; <code>null</code> in case that entered value is not mapped.
   */
  protected <E extends Enum<E>> E getType(Class<E> typeClass, String value, String propertySuffix, Map<String, List<String>> cache) {
    for (Entry<String, List<String>> typeEntry : cache.entrySet()) {
      if (typeEntry.getValue().contains(value)) {
        String property = typeEntry.getKey() + propertySuffix;
        EnumPropertyKey<E> propertyKey = new EnumPropertyKey<E>(property, typeClass, null);

        return propertiesProvider.getValue(propertyKey);
      }
    }

    return null;
  }

  /**
   * Returns factored UML element from entered descriptor.
   *
   * @param umlElementDescriptor descriptor of UML element which contains read data from UXF file.
   * @return Factored UML element.
   * @throws UmlElementException If some error occurs during element factoring.
   */
  @Override
  public UmlElement getUmlElement(UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    if (umlElementDescriptor == null) {
      return null;
    }

    UmlElementType elementType = getUmlElementType(umlElementDescriptor);
    switch (elementType) {
      case CLASS :
        return getUmlClass(umlElementDescriptor);

      case RELATION :
        return getUmlRelation(umlElementDescriptor);

      case NOTE :
        return getUmlNote(umlElementDescriptor);

      default :
        throw new UmlElementException(Strings.getFormatted(
                "exc.uml.unsupported-element-type", umlElementDescriptor.getElementType()));
    }
  }

  /**
   * Returns factored UML element type from entered descriptor.
   *
   * @param umlElementDescriptor descriptor of UML element type which contains read data from UXF file.
   * @return Factored UML element type.
   * @throws UmlElementException If some error occurs during element type factoring.
   */
  public UmlElementType getUmlElementType(UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    String rawElementType = umlElementDescriptor.getElementType();
    if (rawElementType == null) {
      throw new UmlElementException(Strings.get("exc.uml.unknown-element-type"));
    }

    UmlElementTypePropertyKey elementTypePropertyKey = new UmlElementTypePropertyKey(rawElementType, null);
    UmlElementType elementType = propertiesProvider.getValue(elementTypePropertyKey);
    if (elementType == null) {
      throw new UmlElementException(Strings.getFormatted("exc.uml.unsupported-element-type", rawElementType));
    }

    return elementType;
  }

  public UmlRectangle getUmlCoordinates(UmlElementDescriptor umlElementDescriptor) {
    return new UmlRectangle(umlElementDescriptor.getX(), umlElementDescriptor.getY(), umlElementDescriptor.getWidth(), umlElementDescriptor.getHeight());
  }

  /**
   * Returns factored UML class from entered descriptor.
   *
   * @param umlElementDescriptor descriptor of UML element from which will be factored the class.
   * @return Factored UML class.
   * @throws UmlElementException If some error occurs during class factoring.
   */
  protected UmlClass getUmlClass(UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    UmlClass clazz = new UmlClass();
    initUmlClass(clazz, umlElementDescriptor);
    clazz.setCoordinates(getUmlCoordinates(umlElementDescriptor));

    return clazz;
  }

  /**
   * Initializes UML class. This method fills name and type of class and additional attributes from descriptor.
   *
   * @param clazz UML class which will be initialized.
   * @param umlElementDescriptor descriptor of UML element from which will be initialized the class.
   * @throws UmlElementException If some error occurs during class initialization.
   */
  protected void initUmlClass(UmlClass clazz, UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    UmlElementAttributes attributes = clazz.getAttributes();

    String panelAttributes = umlElementDescriptor.getPanelAttributes();
    if (StringUtils.hasText(panelAttributes)) {

      List<String> commentedLines = new ArrayList<String>();
      List<String> additionalLines = new ArrayList<String>();
      List<String> extraLines = new ArrayList<String>();

      String classTypePrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.STEREOTYPE_PREFIX);
      String classTypeSuffix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.STEREOTYPE_SUFFIX);

      String elementLineCommentPrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_COMMENT_PREFIX);
      String elementFontSizePrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_FONT_SIZE_PREFIX);
      String elementBackgroundPrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_BACKGROUND_PREFIX);
      String elementForegroundPrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_FOREGROUND_PREFIX);

      String[] lines = panelAttributes.split("\n");
      for (int i = 0, j = 0; i < lines.length; i++) {
        String line = lines[i];
        if (j == 0 && line.startsWith(classTypePrefix) && line.endsWith(classTypeSuffix)) {
          String classTypeName = line.replaceAll("(" + classTypePrefix + "|" + classTypeSuffix + ")", "");
          clazz.setClassType(getUmlClassType(classTypeName.toLowerCase()));
          attributes.setAttribute(UmlElementAttributesNames.STEREOTYPE, line.toLowerCase());
          j++;
        }
        else if (line.startsWith(elementLineCommentPrefix)) {
          commentedLines.add(line);
        }
        else if (line.startsWith(elementFontSizePrefix)) {
          extraLines.add(line);

          try {
            double fontSize = Double.valueOf(line.replaceFirst(elementFontSizePrefix, ""));
            attributes.setAttribute(UmlElementAttributesNames.FONT_SIZE, fontSize);
          }
          catch (NumberFormatException exc) {
            additionalLines.add(line);
          }
        }
        else if (line.startsWith(elementBackgroundPrefix)) {
          extraLines.add(line);

          try {
            String colorName = line.replaceFirst(elementBackgroundPrefix, "");
            java.lang.reflect.Field colorField = Color.class.getField(colorName.toUpperCase());
            attributes.setAttribute(UmlElementAttributesNames.BACKGROUND, colorField.get(null));
          }
          catch (Exception exc) {
            additionalLines.add(line);
          }
        }
        else if (line.startsWith(elementForegroundPrefix)) {
          extraLines.add(line);

          try {
            String colorName = line.replaceFirst(elementForegroundPrefix, "");
            java.lang.reflect.Field colorField = Color.class.getField(colorName.toUpperCase());
            attributes.setAttribute(UmlElementAttributesNames.FOREGROUND, colorField.get(null));
          }
          catch (Exception exc) {
            additionalLines.add(line);
          }
        }
        else if (j == 0 || j == 1) { // name is on first or second (readable) line
          UmlFont font = new UmlFont();

          char italic = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ITALIC_FONT_DELIMITER);
          char bold = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.BOLD_FONT_DELIMITER);
          char underline = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.UNDERLINE_FONT_DELIMITER);

          String name = line;
          int n = line.length();
          for (int k = 0; k < n; k++) {
            char c = name.charAt(k);
            if (c == name.charAt(n - k - 1)) {
              if (c == italic) {
                if (!font.isItalic()) {
                  font.setStyle(font.getStyle() | UmlFont.ITALIC);
                }
              }
              else if (c == bold) {
                if (!font.isBold()) {
                  font.setStyle(font.getStyle() | UmlFont.BOLD);
                }
              }
              else if (c == underline) {
                if (!font.isUnderlined()) {
                  font.setStyle(font.getStyle() | UmlFont.UNDERLINED);
                }
              }
            }
            else {
              name = line.substring(k, n - k);
              break;
            }
          }

          clazz.setName(name);
          attributes.setAttribute(UmlElementAttributesNames.CLASS_NAME_FONT, font);
          j += 2;
        }
        else {
          additionalLines.add(line);
          extraLines.add(line);
        }
      }

      if (!commentedLines.isEmpty()) {
        attributes.setAttribute(UmlElementAttributesNames.COMMENTED_LINES, commentedLines);
      }

      if (!additionalLines.isEmpty()) {
        attributes.setAttribute(UmlElementAttributesNames.ADDITIONAL_LINES, additionalLines);
      }

      if (!extraLines.isEmpty()) {
        attributes.setAttribute(UmlElementAttributesNames.EXTRA_LINES, extraLines);
      }
    }

    attributes.setAttribute(UmlElementAttributesNames.ADDITIONAL_ATTRIBUTES, umlElementDescriptor.getAdditionalAttributes());
    attributes.setAttribute(UmlElementAttributesNames.PANEL_ATTRIBUTES, panelAttributes);
  }

  /**
   * Returns type of UML class from entered name of class type. This method uses mapping for types of UML classes:<br />
   * <tt>uxf.diagram.element.class.type.CLASS_TYPE = name of UMLClassType field</tt>.
   *
   * @param classTypeName name of class type.
   * @return Type of UML class for entered name of class type.
   */
  protected UmlClassType getUmlClassType(String classTypeName) {
    UmlClassTypePropertyKey propertyKey = new UmlClassTypePropertyKey(DefaultUmlElementFactoryPropertyKeys.CLASS_TYPE_PROPETY_PREFIX + classTypeName, null);
    UmlClassType type = propertiesProvider.getValue(propertyKey);
    if (type == null) {
      type = UmlClassType.CLASS;
    }

    return type;
  }

  /**
   * Returns factored UML relation from entered descriptor.
   *
   * @param umlElementDescriptor descriptor of UML element from which will be factored the relation.
   * @return Factored UML relation.
   * @throws UmlElementException If some error occurs during relation factoring.
   */
  protected UmlRelation getUmlRelation(UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    UmlRelation relation = new UmlRelation();

    // Sets default types of relation
    relation.setRelationType(propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.DEFAULT_RELATION_TYPE));
    relation.setRelationOrientationType(propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.DEFAULT_RELATION_ORIENTATION_TYPE));

    // initializes relation from panel attributes
    initUmlRelation(relation, umlElementDescriptor);
    relation.setCoordinates(getUmlCoordinates(umlElementDescriptor));

    return relation;
  }

  /**
   * Sets attribute into entered attributes if entered value is not <code>null</code>.
   *
   * @param attributes additional attributes of UML element.
   * @param name attribute name.
   * @param value attribute value.
   */
  protected void setAttribute(UmlElementAttributes attributes, String name, Object value) {
    if (value != null) {
      attributes.setAttribute(name, value);
    }
  }

  /**
   * Initializes UML relation. This method sets type of relation and additional attributes from descriptor.
   *
   * @param relation UML relation which will be initialized.
   * @param umlElementDescriptor descriptor of UML element from which will be initialized the relation.
   * @throws UmlElementException If some error occurs during relation initialization.
   */
  protected void initUmlRelation(UmlRelation relation, UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    UmlElementAttributes attributes = relation.getAttributes();

    String[] qualifiers = new String[2];
    String[] multiplicity = new String[2];
    String[] roles = new String[2];

    String panelAttributes = umlElementDescriptor.getPanelAttributes();
    if (StringUtils.hasText(panelAttributes)) {

      List<String> commentedLines = new ArrayList<String>();
      List<String> additionalLines = new ArrayList<String>();

      String relationStereotypePrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.STEREOTYPE_PREFIX);
      String relationStereotypeSuffix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.STEREOTYPE_SUFFIX);

      String relationTypePrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_TYPE_PREFIX);
      String qualifier1Prefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_QUALIFIER_1_PREFIX);
      String qualifier2Prefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_QUALIFIER_2_PREFIX);
      String multiplicity1Prefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_MULTIPLICITY_1_PREFIX);
      String multiplicity2Prefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_MULTIPLICITY_2_PREFIX);
      String role1Prefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_ROLE_1_PREFIX);
      String role2Prefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_ROLE_2_PREFIX);

      String elementLineCommentPrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_COMMENT_PREFIX);

      String[] lines = panelAttributes.split("\n");
      for (int i = 0, j = 0; i < lines.length; i++) {
        String line = lines[i];
        if (j == 0 && line.startsWith(relationStereotypePrefix) && line.endsWith(relationStereotypeSuffix)) {
          attributes.setAttribute(UmlElementAttributesNames.STEREOTYPE, line.toLowerCase());
          j++;
        }
        else if (line.startsWith(relationTypePrefix)) {
          String lineArrow = line.replace(relationTypePrefix, "");
          relation.setRelationType(getUmlRelationType(lineArrow));
          relation.setRelationOrientationType(getUmlRelationOrientationType(lineArrow));
        }
        else if (line.startsWith(qualifier1Prefix)) {
          String mul = line.replace(qualifier1Prefix, "");
          qualifiers[0] = mul;
        }
        else if (line.startsWith(qualifier2Prefix)) {
          String mul = line.replace(qualifier2Prefix, "");
          qualifiers[1] = mul;
        }
        else if (line.startsWith(multiplicity1Prefix)) {
          String mul = line.replace(multiplicity1Prefix, "");
          multiplicity[0] = mul;
        }
        else if (line.startsWith(multiplicity2Prefix)) {
          String mul = line.replace(multiplicity2Prefix, "");
          multiplicity[1] = mul;
        }
        else if (line.startsWith(role1Prefix)) {
          String role = line.replace(role1Prefix, "");
          roles[0] = role;
        }
        else if (line.startsWith(role2Prefix)) {
          String role = line.replace(role2Prefix, "");
          roles[1] = role;
        }
        else if (line.startsWith(elementLineCommentPrefix)) {
          commentedLines.add(line);
        }
        else {
          additionalLines.add(line);
        }
      }

      if (!commentedLines.isEmpty()) {
        attributes.setAttribute(UmlElementAttributesNames.COMMENTED_LINES, commentedLines);
      }

      if (!additionalLines.isEmpty()) {
        attributes.setAttribute(UmlElementAttributesNames.ADDITIONAL_LINES, additionalLines);
      }
    }

    attributes.setAttribute(UmlElementAttributesNames.ADDITIONAL_ATTRIBUTES, umlElementDescriptor.getAdditionalAttributes());
    attributes.setAttribute(UmlElementAttributesNames.PANEL_ATTRIBUTES, panelAttributes);

    // initializes relation line
    relation.setRelationLine(getUmlRelationLine(umlElementDescriptor));

    switch (relation.getRelationOrientationType()) {
      case LEFT :
        setAttribute(attributes, UmlElementAttributesNames.QUALIFIER_FROM, qualifiers[1]);
        setAttribute(attributes, UmlElementAttributesNames.QUALIFIER_TO, qualifiers[0]);
        setAttribute(attributes, UmlElementAttributesNames.MULTIPLICITY_FROM, multiplicity[1]);
        setAttribute(attributes, UmlElementAttributesNames.MULTIPLICITY_TO, multiplicity[0]);
        setAttribute(attributes, UmlElementAttributesNames.ROLE_FROM, roles[1]);
        setAttribute(attributes, UmlElementAttributesNames.ROLE_TO, roles[0]);
        break;

      case UNI :
      case UNDEFINED :
      case RIGHT :
        setAttribute(attributes, UmlElementAttributesNames.QUALIFIER_FROM, qualifiers[0]);
        setAttribute(attributes, UmlElementAttributesNames.QUALIFIER_TO, qualifiers[1]);
        setAttribute(attributes, UmlElementAttributesNames.MULTIPLICITY_FROM, multiplicity[0]);
        setAttribute(attributes, UmlElementAttributesNames.MULTIPLICITY_TO, multiplicity[1]);
        setAttribute(attributes, UmlElementAttributesNames.ROLE_FROM, roles[1]);
        setAttribute(attributes, UmlElementAttributesNames.ROLE_TO, roles[0]);
        break;
    }
  }

  /**
   * Returns relation type from entered arrow.
   *
   * @param relationArrow arrow of relation.
   * @return Type of relation for entered arrow.
   */
  protected UmlRelationType getUmlRelationType(String relationArrow) {
    UmlRelationType type = getType(UmlRelationType.class, relationArrow, DefaultUmlElementFactoryPropertyKeys.RELATION_PROPERTY_SUFFIX,
        relationTypePropertiesCache);

    return (type == null) ? UmlRelationType.UNDEFINED : type;
  }

  /**
   * Returns relation orientation type from entered arrow.
   *
   * @param relationArrow arrow of relation.
   * @return Type of relation orientation for entered arrow.
   */
  protected UmlRelationOrientationType getUmlRelationOrientationType(String relationArrow) {
    UmlRelationOrientationType orientationType = getType(UmlRelationOrientationType.class, relationArrow,
        DefaultUmlElementFactoryPropertyKeys.RELATION_PROPERTY_SUFFIX, relationOrientationTypePropertiesCache);

    return orientationType == null ? UmlRelationOrientationType.UNDEFINED : orientationType;
  }

  /**
   * Returns factored line (shape) of UML relation.
   *
   * @param umlElementDescriptor descriptor of UML element from which will be factored the line (shape) of UML relation.
   * @return Factored line (shape).
   * @throws UmlElementException If some error occurs during line factoring.
   */
  protected UmlLineShape getUmlRelationLine(UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    String panelAttributes = umlElementDescriptor.getAdditionalAttributes();
    if (StringUtils.hasText(panelAttributes)) {
      String separator = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_COORDINATES_SEPARATOR);
      String[] coordinates = panelAttributes.split(separator);

      List<Double> coordinatesValues = new ArrayList<Double>();
      for (int i = 0; i < coordinates.length; i++) {
        double coordinate = Double.valueOf(Double.valueOf(coordinates[i]));
        coordinate += (i % 2 == 0 ? umlElementDescriptor.getX() : umlElementDescriptor.getY());
        coordinatesValues.add(coordinate);
      }

      return new UmlPolyline(coordinatesValues.toArray(new Double[coordinatesValues.size()]));
    }

    return null;
  }

  /**
   * Returns factored UML note from entered descriptor.
   *
   * @param umlElementDescriptor descriptor of UML element from which will be factored the note.
   * @return Factored UML note.
   * @throws UmlElementException If some error occurs during note factoring.
   */
  protected UmlNote getUmlNote(UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    UmlNote note = new UmlNote();
    initUmlNote(note, umlElementDescriptor);
    note.setCoordinates(getUmlCoordinates(umlElementDescriptor));

    return note;
  }

  /**
   * Initializes UML note. This method fills text of note and additional attributes from descriptor.
   *
   * @param note UML note which will be initialized.
   * @param umlElementDescriptor descriptor of UML element from which will be initialized the note.
   * @throws UmlElementException If some error occurs during note initialization.
   */
  protected void initUmlNote(UmlNote note, UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    UmlElementAttributes attributes = note.getAttributes();

    String panelAttributes = umlElementDescriptor.getPanelAttributes();
    if (StringUtils.hasText(panelAttributes)) {
      StringBuilder sb = new StringBuilder();

      List<String> commentedLines = new ArrayList<String>();
      List<String> extraLines = new ArrayList<String>();

      String elementLineCommentPrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_COMMENT_PREFIX);
      String elementFontSizePrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_FONT_SIZE_PREFIX);
      String elementBackgroundPrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_BACKGROUND_PREFIX);
      String elementForegroundPrefix = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.ELEMENT_FOREGROUND_PREFIX);

      String[] lines = panelAttributes.split("\n");
      for (int i = 0; i < lines.length; i++) {
        String line = lines[i];
        if (line.startsWith(elementLineCommentPrefix)) {
          commentedLines.add(line);
        }
        else if (line.startsWith(elementFontSizePrefix)) {
          extraLines.add(line);

          try {
            double fontSize = Double.valueOf(line.replaceFirst(elementFontSizePrefix, ""));
            attributes.setAttribute(UmlElementAttributesNames.FONT_SIZE, fontSize);
          }
          catch (NumberFormatException exc) {
            sb.append(line + "\n");
          }
        }
        else if (line.startsWith(elementBackgroundPrefix)) {
          extraLines.add(line);

          try {
            String colorName = line.replaceFirst(elementBackgroundPrefix, "");
            java.lang.reflect.Field colorField = Color.class.getField(colorName.toUpperCase());
            attributes.setAttribute(UmlElementAttributesNames.BACKGROUND, colorField.get(null));
          }
          catch (Exception exc) {
            sb.append(line + "\n");
          }
        }
        else if (line.startsWith(elementForegroundPrefix)) {
          extraLines.add(line);

          try {
            String colorName = line.replaceFirst(elementForegroundPrefix, "");
            java.lang.reflect.Field colorField = Color.class.getField(colorName.toUpperCase());
            attributes.setAttribute(UmlElementAttributesNames.FOREGROUND, colorField.get(null));
          }
          catch (Exception exc) {
            sb.append(line + "\n");
          }
        }
        else {
          sb.append(line + "\n");
        }
      }

      if (!commentedLines.isEmpty()) {
        attributes.setAttribute(UmlElementAttributesNames.COMMENTED_LINES, commentedLines);
      }

      if (!extraLines.isEmpty()) {
        attributes.setAttribute(UmlElementAttributesNames.EXTRA_LINES, extraLines);
      }

      String text = sb.toString();
      if (text.endsWith("\n")) {
        text = text.substring(0, text.length() - 1);
      }

      note.setText(text);
    }

    attributes.setAttribute(UmlElementAttributesNames.ADDITIONAL_ATTRIBUTES, umlElementDescriptor.getAdditionalAttributes());
    attributes.setAttribute(UmlElementAttributesNames.PANEL_ATTRIBUTES, panelAttributes);
  }

}
