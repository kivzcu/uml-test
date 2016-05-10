package cz.zcu.kiv.oop.uml.util;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;

/**
 * This static class serves for translation of types of UML elements (enumerations).
 *
 * @author Mr.FrAnTA (Michal Dékány)
 * @author Zuzana Mikolášová
 * @author Patrik Harag
 */
public class UmlTranslationUtils {

  /**
   * Private constructor which makes this class "static".
   */
  private UmlTranslationUtils() {}

  /**
   * Returns translation of class type.
   *
   * @param classType class type which will be translated.
   * @return Translation of class type.
   */
  public static String translateClassType(UmlClassType classType) {
    return Strings.get("enum.UmlClassType." + classType.name());
  }

  /**
   * Returns translation of relation type.
   *
   * @param relationType relation type which will be translated.
   * @return Translation of relation type.
   */
  public static String translateRelationType(UmlRelationType relationType) {
    return Strings.get("enum.UmlRelationType." + relationType.name());
  }

}