package cz.zcu.kiv.oop.uml.junit;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributes;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributesNames;
import cz.zcu.kiv.oop.uml.element.UmlFont;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uml.util.UmlGeometryUtils;
import cz.zcu.kiv.oop.uml.util.UmlTranslationUtils;
import cz.zcu.kiv.oop.uml.util.UmlUtils;

/**
 * Static class which contains asserting methods for UML diagram whose are used in generated tests for validating of diagrams.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlAssert {

  /**
   * Private constructor which makes this class "static".
   */
  private UmlAssert() {}

  /**
   * Checks whatever the diagram contains only one note. If the diagram contains zero or more than one notes, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertContainsOneNote(UmlDiagram diagram) {
    int count = diagram.getNotesCount();
    if (count == 0) {
      throw new UmlAssertionError(Strings.get("assert.no-note"));
    }

    if (count != 1) {
      throw new UmlAssertionError(Strings.getFormatted("assert.wrong-numner-of-notes", count));
    }
  }

  /**
   * Checks whatever the diagram contains note with information in correct format (it has to contains only students number and
   * number of home exercise). If the note contains information in wrong format, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertNoteHasCorrectFormat(UmlDiagram diagram) {
    if (!UmlUtils.hasNoteWithCorrectFormat(diagram)) {
      throw new UmlAssertionError(Strings.get("assert.wrong-note-format"));
    }
  }

  /**
   * Checks whatever the note of diagram contains correct information - specified student's personal number and test (exercise)
   * number. If the note doesn't contains expected information, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param personalNumber expected students's personal number.
   * @param testNumber expected test (exercise) number.
   */
  public static void assertNoteHasCorrectData(UmlDiagram diagram, String personalNumber, String testNumber) {
    if (!UmlUtils.hasNoteWithCorrectData(diagram, personalNumber, testNumber)) {
      throw new UmlAssertionError(Strings.get("assert.wrong-note-content"));
    }
  }

  /**
   * Checks whatever the diagram contains notes whose overlapping other elements. If some note overlaps some other element, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertNotesNotOverlap(UmlDiagram diagram) {
    if (UmlGeometryUtils.containsOverlappingNotes(diagram)) {
      throw new UmlAssertionError(Strings.get("assert.note-overlap"));
    }
  }

  /**
   * Checks whatever the diagram contains classes with unique names. If the diagram contains classes with duplicate names, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertContainsClassesWithUniqueNames(UmlDiagram diagram) {
    if (!UmlUtils.containsClassesWithUniqueNames(diagram)) {
      throw new UmlAssertionError(Strings.get("assert.duplicate-class-names"));
    }
  }

  /**
   * Checks whatever the diagram contains class with specified name. If the diagram doesn't contains the class, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className name of class which will be searched.
   */
  public static void assertClassExists(UmlDiagram diagram, String className) {
    if (!UmlUtils.containsClass(diagram, className)) {
      throw new UmlAssertionError(Strings.getFormatted("assert.not-contains-class", className));
    }
  }

  /**
   * Checks whatever the type of class with specified name is equals to entered one. If the type of class is different than expected
   * one, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className name of class which type will be tested.
   * @param expectedType expected class type.
   */
  public static void assertCorrectClassType(UmlDiagram diagram, String className, UmlClassType expectedType) {
    if (!UmlUtils.containsClass(diagram, expectedType, className)) {
      String classType = UmlTranslationUtils.translateClassType(expectedType);
      throw new UmlAssertionError(Strings.getFormatted("assert.wrong-class-type", className, classType));
    }
  }

  /**
   * Checks whatever the diagram contains expected number of classes. If the number of classes is different, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param expectedCount expected number for classes in diagram.
   */
  public static void assertClassesCount(UmlDiagram diagram, int expectedCount) {
    int classesCount = diagram.getClassesCount();
    if (classesCount != expectedCount) {
      throw new UmlAssertionError(Strings.getFormatted("assert.wrong-number-of-classes", classesCount, expectedCount));
    }
  }

  /**
   * Checks whatever the diagram doesn't contains another classes than the classes with entered names. If the diagram contains
   * different number of classes, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param classesNames array of classes name which should be in diagram.
   */
  public static void assertContainsNoRedundantClasses(UmlDiagram diagram, String[] classesNames) {
    UmlClass[] redundantClasses = UmlUtils.getRedundantClases(diagram, classesNames);
    if (redundantClasses.length > 0) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < redundantClasses.length; i++) {
        UmlClass clazz = redundantClasses[i];
        sb.append(UmlTranslationUtils.translateClassType(clazz.getClassType()));
        sb.append(" ");
        sb.append(clazz.getName());

        if (i != redundantClasses.length - 1) {
          sb.append(", ");
        }
      }

      throw new UmlAssertionError(Strings.getFormatted("assert.redundant-classes", sb));
    }
  }

  /**
   * Checks whatever the class with entered name is colored (has set foreground or background). If the class is colored, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className name of class which will be tested.
   */
  public static void assertClassIsNotColored(UmlDiagram diagram, String className) {
    UmlClass clazz = UmlUtils.getClassByName(diagram, className);
    UmlElementAttributes attributes = clazz.getAttributes();
    Color foreground = attributes.getAttribute(UmlElementAttributesNames.FOREGROUND, Color.class);
    Color background = attributes.getAttribute(UmlElementAttributesNames.BACKGROUND, Color.class);
    if (foreground != null) {
      if (background != null) {
        throw new UmlAssertionError(Strings.getFormatted("assert.has-foreground-and-background-color", className));
      }

      throw new UmlAssertionError(Strings.getFormatted("assert.has-foreground-color", className));
    }
    else if (background != null) {
      throw new UmlAssertionError(Strings.getFormatted("assert.has-background-color", className));
    }
  }

  /**
   * Checks whatever the class name has correct font. If the class has different type of font, the {@link UmlAssertionError} is
   * thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className name of class which will be tested.
   * @param expectedFont expected font for class name.
   */
  public static void assertClassNameHasCorrectFont(UmlDiagram diagram, String className, UmlFont expectedFont) {
    UmlClass clazz = UmlUtils.getClassByName(diagram, className);
    UmlFont font = clazz.getAttributes().getAttribute(UmlElementAttributesNames.CLASS_NAME_FONT, UmlFont.class);
    if (!expectedFont.equals(font)) {
      throw new UmlAssertionError(Strings.getFormatted("assert.class-name-has-wrong-font", className));
    }
  }

  /**
   * Checks whatever the class with specified name has no additional text. If the class contains additional informations, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className name of class which will be tested.
   */
  @SuppressWarnings("unchecked")
  public static void assertClassHasNoAdditionalText(UmlDiagram diagram, String className) {
    UmlClass clazz = UmlUtils.getClassByName(diagram, className);
    List<String> additionalLines = clazz.getAttributes().getAttribute(UmlElementAttributesNames.ADDITIONAL_LINES, List.class);
    if (additionalLines != null && additionalLines.size() > 0) {
      throw new UmlAssertionError(Strings.getFormatted("assert.class-has-additional-text", className));
    }
  }

  /**
   * Checks whatever diagram contains no overlapped classes. If some class overlaps another class, the {@link UmlAssertionError} is
   * thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertClassesNotOverlap(UmlDiagram diagram) {
    if (UmlGeometryUtils.containsOverlappedClasses(diagram)) {
      throw new UmlAssertionError(Strings.get("assert.classes-overlap"));
    }
  }

  /**
   * Checks whatever between classes with specified names are no relations. If diagram contains some relations between classes, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   */
  public static void assertNoRelationBetween(UmlDiagram diagram, String className1, String className2) {
    UmlRelation[] relations = UmlUtils.getRelationsBetween(diagram, className1, className2);
    if (relations.length > 0) {
      throw new UmlAssertionError(Strings.getFormatted("assert.redundant-relation", className1, className2));
    }
  }

  /**
   * Checks whatever the entered number of relations between classes with specified names is correct. If diagram contains more or
   * less relations between classes, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   * @param expectedCount expected count of relations between classes.
   */
  public static void assertRelationsCountBetween(UmlDiagram diagram, String className1, String className2, int expectedCount) {
    UmlRelation[] relations = UmlUtils.getRelationsBetween(diagram, className1, className2);
    if (relations.length == 0 && expectedCount > 0) {
      throw new UmlAssertionError(Strings.getFormatted("assert.relation-not-exists", className1, className2));
    }

    if (relations.length != expectedCount) {
      throw new UmlAssertionError(Strings.getFormatted("assert.wrong-number-of-relations", className1, className2, expectedCount));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names has specified orientations. If one or more relations between
   * classes has different orientation, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   * @param expectedTypes expected types of relation orientations between classes.
   */
  public static void assertRelationsOrientationTypesBetween(UmlDiagram diagram, String className1, String className2, UmlRelationOrientationType[] expectedTypes) {
    UmlRelationOrientationType[] relationsOrientationTypes = UmlUtils.getRelationsOrientationTypesBetween(diagram, className1, className2);
    if (relationsOrientationTypes.length == 0) {
        throw new UmlAssertionError(Strings.getFormatted("assert.relation-not-exists", className1, className2));
    }

    boolean orientationExists = true;
    if (relationsOrientationTypes.length == expectedTypes.length) {
      for (int i = 0; i < relationsOrientationTypes.length; i++) {
        if (expectedTypes[i] != UmlRelationOrientationType.UNI
            && relationsOrientationTypes[i] == UmlRelationOrientationType.UNI) {
          orientationExists = false;
          break;
        }
      }
    }

    if (!orientationExists) {
      throw new UmlAssertionError(Strings.getFormatted("assert.no-relation-orientation", className1, className2));
    }

    if (!Arrays.equals(relationsOrientationTypes, expectedTypes)) {
      String key = (expectedTypes.length == 1) ? "assert.wrong-relation-orientation" : "assert.wrong-relations-orientations";
      throw new UmlAssertionError(Strings.getFormatted(key, className1, className2));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names has specified types. If one or more relations between
   * classes has different type, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   * @param expectedTypes expected types of relations between classes.
   */
  public static void assertRelationsTypesBetween(UmlDiagram diagram, String className1, String className2, UmlRelationType[] expectedTypes) {
    UmlRelationType[] relationsTypes = UmlUtils.getRelationsTypesBetween(diagram, className1, className2);
    if (relationsTypes.length == 0) {
      throw new UmlAssertionError(Strings.getFormatted("assert.relation-not-exists", className1, className2));
    }

    if (!Arrays.equals(relationsTypes, expectedTypes)) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < expectedTypes.length; i++) {
        UmlRelationType relationType = expectedTypes[i];
        sb.append(UmlTranslationUtils.translateRelationType(relationType));
        if (i != expectedTypes.length - 1) {
          sb.append(", ");
        }
      }

      String key = (expectedTypes.length == 1) ? "assert.wrong-relation-type" : "assert.wrong-relations-types";
      throw new UmlAssertionError(Strings.getFormatted(key, className1, className2, sb));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names contains no stereotypes. If some relation contains
   * stereotype, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   */
  public static void assertRelationsWithNoStereotypeBetween(UmlDiagram diagram, String className1, String className2) {
    String[] stereotypes = UmlUtils.getRelationsStereotypesBetween(diagram, className1, className2);
    if (!Arrays.equals(stereotypes, new String[stereotypes.length])) {
      throw new UmlAssertionError(Strings.getFormatted("assert.redundant-stereotypes", className1, className2));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names have correct stereotypes (expected one). If relation has
   * different stereotype (or none), the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   * @param expectedStereotypes expected stereotype of relations.
   */
  public static void assertRelationsStereotypesBetween(UmlDiagram diagram, String className1, String className2, String[] expectedStereotypes) {
    String[] stereotypes = UmlUtils.getRelationsStereotypesBetween(diagram, className1, className2);
    if (stereotypes.length == 0) {
        throw new UmlAssertionError(Strings.getFormatted("assert.relation-not-exists", className1, className2));
    }

    if (!Arrays.equals(stereotypes, expectedStereotypes)) {
      StringBuilder sb = new StringBuilder();
      int count = 0;
      for (int i = 0; i < expectedStereotypes.length; i++) {
        if (stereotypes[i] == null) {
          sb.append(stereotypes[i]).append(", ");
          count++;
        }
      }

      String key = (count == 1) ? "assert.wrong-stereotype" : "assert.wrong-stereotypes";
      throw new UmlAssertionError(Strings.getFormatted(key, className1, className2));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names have no qualifiers. If some relation have qualifier, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   */
  public static void assertRelationsWithNoQualifiersBetween(UmlDiagram diagram, String className1, String className2) {
    String[][] qualifiers = UmlUtils.getRelationsQualifiersBetween(diagram, className1, className2);

    boolean equals = true;
    for (int i = 0; i < qualifiers.length; i++) {
      equals &= Arrays.equals(qualifiers[i], new String[2]);
    }

    if (!equals) {
      throw new UmlAssertionError(Strings.getFormatted("assert.redundant-qualifiers", className1, className2));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names have correct qualifiers (expected one). If relation has
   * different qualifiers (or none), the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   * @param expectedQualifiers expected qualifiers of relations.
   */
  public static void assertRelationsQualifiersBetween(UmlDiagram diagram, String className1, String className2, String[][] expectedQualifiers) {
    String[][] qualifiers = UmlUtils.getRelationsQualifiersBetween(diagram, className1, className2);
    if (qualifiers.length == 0) {
        throw new UmlAssertionError(Strings.getFormatted("assert.relation-not-exists", className1, className2));
    }

    boolean equals = true;
    for (int i = 0; i < qualifiers.length; i++) {
      equals &= Arrays.equals(qualifiers[i], expectedQualifiers[i]);
    }

    if (!equals) {
      throw new UmlAssertionError(Strings.getFormatted("assert.wrong-qualifiers", className1, className2));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names have no multiplicity. If some relation have qualifier, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   */
  public static void assertRelationsWithNoMultiplicityBetween(UmlDiagram diagram, String className1, String className2) {
    String[][] multiplicity = UmlUtils.getRelationsMultiplicityBetween(diagram, className1, className2);

    boolean equals = true;
    for (int i = 0; i < multiplicity.length; i++) {
      equals &= Arrays.equals(multiplicity[i], new String[2]);
    }

    if (!equals) {
      throw new UmlAssertionError(Strings.getFormatted("assert.redundant-multiplicity", className1, className2));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names have correct multiplicity (expected one). If relation has
   * different multiplicity (or none), the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   * @param expectedMultiplicity expected multiplicity of relations.
   */
  public static void assertRelationsMultiplicityBetween(UmlDiagram diagram, String className1, String className2, String[][] expectedMultiplicity) {
    String[][] multiplicity = UmlUtils.getRelationsMultiplicityBetween(diagram, className1, className2);
    if (multiplicity.length == 0) {
        throw new UmlAssertionError(Strings.getFormatted("assert.relation-not-exists", className1, className2));
    }

    // zadne kardinality
    boolean multiplicityExists = true;
    for (int i = 0; i < multiplicity.length; i++) {
      if (multiplicity[i].length > 0 && multiplicity[i][0] == null) {
        multiplicityExists = false;
        break;
      }
      if (multiplicity[i].length > 1 && multiplicity[i][1] == null) {
        multiplicityExists = false;
        break;
      }
    }

    if (!multiplicityExists) {
      throw new UmlAssertionError(Strings.getFormatted("assert.missing-multiplicity", className1, className2));
    }


    boolean equals = true;
    for (int i = 0; i < multiplicity.length; i++) {
      equals &= Arrays.equals(multiplicity[i], expectedMultiplicity[i]);
    }

    if (!equals) {
      throw new UmlAssertionError(Strings.getFormatted("assert.wrong-multiplicity", className1, className2));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names have no roles. If some relation have qualifier, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   */
  public static void assertRelationsWithNoRolesBetween(UmlDiagram diagram, String className1, String className2) {
    String[][] roles = UmlUtils.getRelationsRolesBetween(diagram, className1, className2);

    boolean equals = true;
    for (int i = 0; i < roles.length; i++) {
      equals &= Arrays.equals(roles[i], new String[2]);
    }

    if (!equals) {
      throw new UmlAssertionError(Strings.getFormatted("assert.redundant-roles", className1, className2));
    }
  }

  /**
   * Checks whatever the relations between classes with specified names have correct roles (expected one). If relation has different
   * roles (or none), the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param className1 name of first class.
   * @param className2 name of second class.
   * @param expectedRoles expected roles of relations.
   */
  public static void assertRelationsRolesBetween(UmlDiagram diagram, String className1, String className2, String[][] expectedRoles) {
    String[][] roles = UmlUtils.getRelationsRolesBetween(diagram, className1, className2);
    if (roles.length == 0) {
        throw new UmlAssertionError(Strings.getFormatted("assert.relation-not-exists", className1, className2));
    }

    boolean equals = true;
    for (int i = 0; i < roles.length; i++) {
      equals &= Arrays.equals(roles[i], expectedRoles[i]);
    }

    if (!equals) {
      throw new UmlAssertionError(Strings.getFormatted("assert.wrong-roles", className1, className2));
    }
  }

  /**
   * Checks whatever the diagram doesn't contains blind relations (from somewhere to nowhere). If diagram contains some blind
   * relation, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertContainsNoBlindRelations(UmlDiagram diagram) {
    int size = UmlUtils.getBlindRelations(diagram).length;
    if (UmlUtils.containsBlindRelation(diagram)) {
      throw new UmlAssertionError(Strings.getFormatted("assert.blind-relations", size));
    }
  }

  /**
   * Checks whatever the diagram doesn't contains unused relations (from nowhere to nowhere). If diagram contains some unused
   * relation, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertContainsNoUnusedRelations(UmlDiagram diagram) {
    if (UmlUtils.containsUnusedRelation(diagram)) {
      throw new UmlAssertionError(Strings.get("assert.unused-relations"));
    }
  }

  /**
   * Checks whether the diagram contains entered number of relations. If the diagram contains another count of relation, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param expectedCount expected number of relations in diagram.
   */
  public static void assertRelationsCount(UmlDiagram diagram, int expectedCount) {
    int relationsCount = diagram.getRelationsCount();
    if (relationsCount != expectedCount) {
      throw new UmlAssertionError(Strings.getFormatted("assert.wrong-number-of-relations", relationsCount, expectedCount));
    }
  }

  /**
   * Checks whatever the diagram contains acceptable number of relations crossings. If the diagram contains too many crossings, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   * @param expectedMaximalCount expected maximal number of relations crossings.
   */
  public static void assertCountOfRelationsCrossings(UmlDiagram diagram, int expectedMaximalCount) {
    int countOfRelationsCrossings = UmlGeometryUtils.getCountOfRelationsCrossings(diagram);
    if (countOfRelationsCrossings > expectedMaximalCount) {
      throw new UmlAssertionError(Strings.getFormatted("assert.too-many-crossings", countOfRelationsCrossings, expectedMaximalCount));
    }
  }

  /**
   * Checks whether the diagram doesn't contains relations whose looks like an extension of some edge of UML classes from/to which
   * leads. If diagram contains this type of relation, the {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertRelationsNotExtendingClassEdge(UmlDiagram diagram) {
    int size = UmlGeometryUtils.getRelationsExtendingClassEdge(diagram).length;
    if (UmlGeometryUtils.containsRelationsExtendingClassEdge(diagram)) {
      throw new UmlAssertionError(Strings.getFormatted("assert.relations-extending-class-edge", size));
    }
  }

  /**
   * Checks whether the relations of diagram are not crossing other elements. If some relation cross another element, the
   * {@link UmlAssertionError} is thrown.
   *
   * @param diagram UML diagram will be tested.
   */
  public static void assertRelationsNotCrossingElements(UmlDiagram diagram) {
    if (UmlGeometryUtils.containsRelationsCrossingElements(diagram)) {
      throw new UmlAssertionError(Strings.get("assert.relations-crossing-elements"));
    }
  }

}
