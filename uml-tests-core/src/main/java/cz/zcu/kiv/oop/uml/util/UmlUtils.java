package cz.zcu.kiv.oop.uml.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributes;
import cz.zcu.kiv.oop.uml.element.UmlElementAttributesNames;
import cz.zcu.kiv.oop.uml.element.UmlNote;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uml.exception.UmlClassNotFoundException;

/**
 * Static class contains method used in final tests .
 *
 * @author Mr.FrAnTA (Michal Dékány)
 * @author Lukáš Witz
 * @author Karel Zíbar
 */
public class UmlUtils {

  /** Regular expression for personal number in diagram note. */
  public static final String PERSONAL_NUMBER_REGEX = "[AESPKFRZD][0-9]{2}[BN][0-9]{4}[PK]";

  /** Regular expression for exercise number in diagram note. */
  public static final String TEST_NUMBER_REGEX = "DU-0[0-9]";

  /**
   * Private constructor which makes this class "static".
   */
  private UmlUtils() {}

  /**
   * Checks whatever the diagram contains one note which contains text in specific format according to
   * {@link #PERSONAL_NUMBER_REGEX} and {@link #TEST_NUMBER_REGEX}.
   *
   * @param diagram diagram to check note in
   * @return true if diagram contains only one note in correct format, false otherwise
   */
  public static boolean hasNoteWithCorrectFormat(UmlDiagram diagram) {
    if (diagram.getNotesCount() != 1) {
      return false;
    }

    UmlNote note = diagram.getNotes().get(0);
    String noteText = note.getText();
    if (noteText == null) {
      return false;
    }

    String[] noteLines = noteText.split("\n");
    if (noteLines.length != 2) {
      return false;
    }

    return noteLines[0].matches(PERSONAL_NUMBER_REGEX) && noteLines[1].matches(TEST_NUMBER_REGEX);
  }

  /**
   * Checks if diagram contains only one note and it's text match to given data.
   *
   * @param diagram diagram to check
   * @param personalNumber personal number of student
   * @param testNumber number of home exercise
   * @return true if diagram contains only one note and it's text match to given data, false otherwise
   */
  public static boolean hasNoteWithCorrectData(UmlDiagram diagram, String personalNumber, String testNumber) {
    if (diagram.getNotesCount() != 1) {
      return false;
    }

    UmlNote note = diagram.getNotes().get(0);
    String noteText = note.getText();
    if (noteText == null) {
      return false;
    }

    String[] noteLines = noteText.split("\n");
    if (noteLines.length != 2) {
      return false;
    }

    String testName = testNumber;
    if (!testNumber.startsWith("DU-")) {
      testName = "DU-" + testNumber;
    }

    return noteLines[0].equals(personalNumber) && noteLines[1].equals(testName);
  }

  /**
   * Gets UmlClass from diagram according to given class name
   *
   * @param diagram UmlDiagram to find wanted class in
   * @param className name of wanted UmlClass
   * @return UmlClass from diagram according to given class name
   */
  public static UmlClass getClassByName(UmlDiagram diagram, String className) {
    for (UmlClass clazz : diagram.getClasses()) {
      if (clazz.getName().equals(className)) {
        return clazz;
      }
    }

    throw new UmlClassNotFoundException(Strings.get("exc.uml.uml-class-not-found"));
  }

  /**
   * Gets all class names
   *
   * @param classes array of UmlClass
   * @return array of names of all UmlClasses from given array
   */
  public static String[] getClassesNames(UmlClass[] classes) {
    String[] classesNames = new String[classes.length];
    for (int i = 0; i < classes.length; i++) {
      classesNames[i] = classes[i].getName();
    }

    return classesNames;
  }

  /**
   * Gets all class names
   *
   * @param diagram UmlDiagram to getting all class in
   * @return array of names of all UmlClasses from given diagram
   */
  public static String[] getClassesNames(UmlDiagram diagram) {
    List<UmlClass> umlClasses = diagram.getClasses();

    return getClassesNames(umlClasses.toArray(new UmlClass[umlClasses.size()]));
  }

  /**
   * Gets names of UmlClasses of certain UmlClassType from given diagram
   *
   * @param diagram UmlDiagram to getting all class in
   * @param classType type of classes you want to get names from
   * @return array of names of all UmlClasses of certain UmlClassType from given diagram
   */
  public static String[] getClassesNames(UmlDiagram diagram, UmlClassType classType) {
    List<UmlClass> umlClasses = diagram.getClasses(classType);

    return getClassesNames(umlClasses.toArray(new UmlClass[umlClasses.size()]));
  }

  /**
   * Checks if there are only class with unique names in diagram
   *
   * @param diagram UmlDiagram to check
   * @return true if there are only unique names of classes in UmlDiagram, false otherwise
   */
  public static boolean containsClassesWithUniqueNames(UmlDiagram diagram) {
    List<String> names = new ArrayList<String>(diagram.getClassesCount());
    for (UmlClass clazz : diagram.getClasses()) {
      String name = clazz.getName();
      if (names.contains(name)) {
        return false;
      }

      names.add(name);
    }

    return true;
  }

  /**
   * Checks if diagram contains given class
   *
   * @param diagram UmlDiagram to check
   * @param clazz UmlClass you want to search in diagram
   * @return true if given clazz is in diagram, false otherwise
   */
  public static boolean containsClass(UmlDiagram diagram, UmlClass clazz) {
    return diagram.getClasses().contains(clazz);
  }

  /**
   * Checks if diagram contains given class
   *
   * @param diagram UmlDiagram to check
   * @param className name of UmlClass you want to search in diagram
   * @return true if given UmlClass is in diagram, false otherwise
   */
  public static boolean containsClass(UmlDiagram diagram, String className) {
    for (UmlClass clazz : diagram.getClasses()) {
      if (clazz.getName().equals(className)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if diagram contains given class
   *
   * @param diagram UmlDiagram to check
   * @param className name of UmlClass you want to search in diagram
   * @param classType type of UmlClass you want to search in diagram
   * @return true if given UmlClass is in diagram, false otherwise
   */
  public static boolean containsClass(UmlDiagram diagram, UmlClassType classType, String className) {
    for (UmlClass clazz : diagram.getClasses(classType)) {
      if (clazz.getName().equals(className)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if diagram contains some reduntant UmlClasses
   *
   * @param diagram UmlDiagram to check
   * @param classesNames names of all UmlClasses which should be in diagram
   * @return true if diagram contains some redundant UmlClasses, false otherwise
   */
  public static boolean containsRedundantClasses(UmlDiagram diagram, String[] classesNames) {
    return getRedundantClases(diagram, classesNames).length > 0;
  }

  /**
   * Checks if diagram contains some reduntant UmlClasses
   *
   * @param diagram UmlDiagram to check
   * @param classes all UmlClasses which should be in diagram
   * @return true if diagram contains some redundant UmlClasses, false otherwise
   */
  public static boolean containsRedundantClasses(UmlDiagram diagram, UmlClass[] classes) {
    List<UmlClass> diagramClasses = diagram.getClasses();
    diagramClasses.removeAll(Arrays.asList(classes));

    return diagramClasses.size() > 0;
  }

  /**
   * Gets all redundant classes from diagram
   *
   * @param diagram UmlDiagram to check
   * @param classesNames names of all UmlClasses which should be in diagram
   * @return array of all redundant classes from diagram
   */
  public static UmlClass[] getRedundantClases(UmlDiagram diagram, String[] classesNames) {
    List<String> names = Arrays.asList(classesNames);
    List<UmlClass> classes = diagram.getClasses();
    List<UmlClass> redundant = new ArrayList<UmlClass>(classes);
    for (UmlClass clazz : classes) {
      if (names.contains(clazz.getName())) {
        redundant.remove(clazz);
      }
    }

    return redundant.toArray(new UmlClass[redundant.size()]);
  }

  /**
   * Gets all redundant classes from diagram
   *
   * @param diagram UmlDiagram to check
   * @param classes all UmlClasses which should be in diagram
   * @return array of all redundant classes from diagram
   */
  public static UmlClass[] getRedundantClases(UmlDiagram diagram, UmlClass[] classes) {
    List<UmlClass> diagramClasses = diagram.getClasses();
    diagramClasses.removeAll(Arrays.asList(classes));

    return diagramClasses.toArray(new UmlClass[diagramClasses.size()]);
  }

  /**
   * Checks if diagram contains some relation which points from nowhere to nowhere
   *
   * @param diagram UmlDiagram to check
   * @return true if diagram contains some relation which points from nowhere to nowhere, false otherwise
   */
  public static boolean containsUnusedRelation(UmlDiagram diagram) {
    return getUnusedRelations(diagram).length > 0;
  }

  /**
   * Gets all relations which point from nowhere to nowhere
   *
   * @param diagram UmlDiagram to check
   * @return array of all relations which point from nowhere to nowhere
   */
  public static UmlRelation[] getUnusedRelations(UmlDiagram diagram) {
    List<UmlRelation> unusedRelations = new ArrayList<UmlRelation>();
    for (UmlRelation relation : diagram.getRelations()) {
      if (relation.getFrom() == null && relation.getTo() == null) {
        unusedRelations.add(relation);
      }
    }

    return unusedRelations.toArray(new UmlRelation[unusedRelations.size()]);
  }

  /**
   * Checks if diagram contains some relation which points from some UmlClass to nowhere
   *
   * @param diagram UmlDiagram to check
   * @return true if diagram contains some relation which points from some UmlClass to nowhere, false otherwise
   */
  public static boolean containsBlindRelation(UmlDiagram diagram) {
    return getBlindRelations(diagram).length > 0;
  }

  /**
   * Gets all relations which point from some UmlClass to nowhere
   *
   * @param diagram UmlDiagram to check
   * @return array of all relations which point from some UmlClass to nowhere
   */
  public static UmlRelation[] getBlindRelations(UmlDiagram diagram) {
    List<UmlRelation> blindRelations = new ArrayList<UmlRelation>();
    for (UmlRelation relation : diagram.getRelations()) {
      UmlClass from = relation.getFrom();
      UmlClass to = relation.getTo();
      if ((from == null && to != null) || (from != null && to == null)) {
        blindRelations.add(relation);
      }
    }

    return blindRelations.toArray(new UmlRelation[blindRelations.size()]);
  }

  /**
   * Checks if diagram contains some relations which point from nowhere to nowhere or from some UmlClass to nowhere
   *
   * @param diagram UmlDiagram to check
   * @return true if diagram contains some relations which point from nowhere to nowhere or from some UmlClass to nowhere, false
   *         otherwise
   */
  public static boolean containsBlindOrUnusedRelation(UmlDiagram diagram) {
    return getBlindOrUnusedRelations(diagram).length > 0;
  }

  /**
   * Gets all relations which point from nowhere to nowhere or from some UmlClass to nowhere
   *
   * @param diagram UmlDiagram to check
   * @return array of all relations which point from nowhere to nowhere or from some UmlClass to nowhere
   */
  public static UmlRelation[] getBlindOrUnusedRelations(UmlDiagram diagram) {
    List<UmlRelation> blindOrUnusedRelations = new ArrayList<UmlRelation>();
    for (UmlRelation relation : diagram.getRelations()) {
      if (relation.getFrom() == null || relation.getTo() == null) {
        blindOrUnusedRelations.add(relation);
      }
    }

    return blindOrUnusedRelations.toArray(new UmlRelation[blindOrUnusedRelations.size()]);
  }

  /**
   * Checks if there is some relation in diagram which leads from UmlClass named className1 to UmlClass named className2 (or
   * relation of opposite direction)
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return true if there is relation between given classes in diagram, false otherwise
   */
  public static boolean isRelationBetween(UmlDiagram diagram, String className1, String className2) {
    return getRelationsBetween(diagram, className1, className2).length > 0;
  }

  /**
   * Checks if there is some relation in diagram which leads from UmlClass1 to UmlClass2 (or relation of opposite direction)
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return true if there is some relation in diagram from UmlClass1 to UmlClass2 (or relation of opposite direction)
   */
  public static boolean isRelationBetween(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    return getRelationsBetween(diagram, class1, class2).length > 0;
  }

  /**
   * Gets all relations in diagram which lead from UmlClass1 to UmlClass2 (or relation of opposite direction)
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return array of UmlRelation in diagram from UmlClass1 to UmlClass2 (or relation of opposite direction)
   */
  public static UmlRelation[] getRelationsBetween(UmlDiagram diagram, String className1, String className2) {
    UmlClass class1 = getClassByName(diagram, className1);
    UmlClass class2 = getClassByName(diagram, className2);

    return getRelationsBetween(diagram, class1, class2);
  }

  /**
   * Gets all relations in diagram which lead from UmlClass1 to UmlClass2 (or relation of opposite direction)
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return array of UmlRelation in diagram from UmlClass1 to UmlClass2 (or relation of opposite direction)
   */
  public static UmlRelation[] getRelationsBetween(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    List<UmlRelation> relations = new ArrayList<UmlRelation>();

    for (UmlRelation relation : diagram.getRelations()) {
      UmlClass from = relation.getFrom();
      UmlClass to = relation.getTo();

      if ((class1.equals(from) && class2.equals(to)) || (class1.equals(to) && class2.equals(from))) {
        relations.add(relation);
      }
    }

    return relations.toArray(new UmlRelation[relations.size()]);
  }

  /**
   * Gets all relations in diagram which lead from UmlClass1
   *
   * @param diagram UmlDiagram to check
   * @param className name of UmlClass
   * @return array of UmlRelation in diagram which lead from UmlClass1
   */
  public static UmlRelation[] getRelationsFrom(UmlDiagram diagram, String className) {
    return getRelationsFrom(diagram, getClassByName(diagram, className));
  }

  /**
   * Gets all relations in diagram which lead from UmlClass1
   *
   * @param diagram UmlDiagram to check
   * @param clazz UmlClass, the relation has to leading from
   * @return array of UmlRelation in diagram which lead from UmlClass1
   */
  public static UmlRelation[] getRelationsFrom(UmlDiagram diagram, UmlClass clazz) {
    List<UmlRelation> relations = new ArrayList<UmlRelation>();
    for (UmlRelation relation : diagram.getRelations()) {
      if (clazz.equals(relation.getFrom())) {
        relations.add(relation);
      }
      else {
        if (relation.getRelationOrientationType() == UmlRelationOrientationType.UNI && clazz.equals(relation.getTo())) {
          relations.add(relation);
        }
      }
    }

    return relations.toArray(new UmlRelation[relations.size()]);
  }

  /**
   * Gets all relations in diagram which lead to some UmlClass
   *
   * @param diagram UmlDiagram to check
   * @param className name of UmlClass, the relation has to leading to
   * @return array of UmlRelation in diagram which lead to some UmlClass
   */
  public static UmlRelation[] getRelationsTo(UmlDiagram diagram, String className) {
    return getRelationsTo(diagram, getClassByName(diagram, className));
  }

  /**
   * Gets all relations in diagram which lead to some UmlClass
   *
   * @param diagram UmlDiagram to check
   * @param clazz UmlClass, the relation has to leading to
   * @return array of UmlRelation in diagram which lead to some UmlClass
   */
  public static UmlRelation[] getRelationsTo(UmlDiagram diagram, UmlClass clazz) {
    List<UmlRelation> relations = new ArrayList<UmlRelation>();
    for (UmlRelation relation : diagram.getRelations()) {
      if (clazz.equals(relation.getTo())) {
        relations.add(relation);
      }
      else {
        if (relation.getRelationOrientationType() == UmlRelationOrientationType.UNI && clazz.equals(relation.getFrom())) {
          relations.add(relation);
        }
      }
    }

    return relations.toArray(new UmlRelation[relations.size()]);
  }

  /**
   * Checks if there is some relation in diagram which leads from UmlClass1 to UmlClass2
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return true if there is some relation in diagram from UmlClass1 to UmlClass2
   */
  public static boolean isRelationFromTo(UmlDiagram diagram, String className1, String className2) {
    UmlClass class1 = getClassByName(diagram, className1);
    UmlClass class2 = getClassByName(diagram, className2);

    return isRelationFromTo(diagram, class1, class2);
  }

  /**
   * Checks if there is some relation in diagram which leads from UmlClass1 to UmlClass2
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return true if there is some relation in diagram from UmlClass1 to UmlClass2
   */
  public static boolean isRelationFromTo(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    return getRelationsFromTo(diagram, class1, class2).length > 0;
  }

  /**
   * Gets all relations from diagram which leads from UmlClass1 to UmlClass2
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return array of all relations from diagram which leads from UmlClass1 to UmlClass2
   */
  public static UmlRelation[] getRelationsFromTo(UmlDiagram diagram, String className1, String className2) {
    UmlClass class1 = getClassByName(diagram, className1);
    UmlClass class2 = getClassByName(diagram, className2);

    return getRelationsFromTo(diagram, class1, class2);
  }

  /**
   * Gets all relations from diagram which leads from UmlClass1 to UmlClass2
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return array of all relations from diagram which leads from UmlClass1 to UmlClass2
   */
  public static UmlRelation[] getRelationsFromTo(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    List<UmlRelation> relations = new ArrayList<UmlRelation>();

    for (UmlRelation relation : diagram.getRelations()) {
      UmlClass from = relation.getFrom();
      UmlClass to = relation.getTo();

      if (relation.getRelationOrientationType() == UmlRelationOrientationType.UNI) {
        if ((class1.equals(from) && class2.equals(to)) || (class1.equals(to) && class2.equals(from))) {
          relations.add(relation);
        }
      }
      else if (class1.equals(from) && class2.equals(to)) {
        relations.add(relation);
      }
    }

    return relations.toArray(new UmlRelation[relations.size()]);
  }

  /**
   * Gets types of all relations
   *
   * @param relations array of UmlRelations from diagram
   * @return array of types of given relations
   */
  public static UmlRelationType[] getRelationsTypes(UmlRelation[] relations) {
    UmlRelationType[] types = new UmlRelationType[relations.length];
    for (int i = 0; i < relations.length; i++) {
      types[i] = relations[i] == null ? null : relations[i].getRelationType();
    }

    return types;
  }

  /**
   * Gets types of all relations in diagram which lead from UmlClass1 to UmlClass2 (or relation of opposite direction)
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return array types of UmlRelation in diagram from UmlClass1 to UmlClass2 (or relation of opposite direction)
   */
  public static UmlRelationType[] getRelationsTypesBetween(UmlDiagram diagram, String className1, String className2) {
    UmlClass c1 = getClassByName(diagram, className1);
    UmlClass c2 = getClassByName(diagram, className2);

    return getRelationsTypesBetween(diagram, c1, c2);
  }

  /**
   * Gets types of all relations in diagram which lead from UmlClass1 to UmlClass2 (or relation of opposite direction)
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return array types of UmlRelation in diagram from UmlClass1 to UmlClass2 (or relation of opposite direction)
   */
  public static UmlRelationType[] getRelationsTypesBetween(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    UmlRelation[] relations = getRelationsBetween(diagram, class1, class2);

    return getRelationsTypes(relations);
  }

  /**
   * Gets types of all relations in diagram which lead from some UmlClass
   *
   * @param diagram UmlDiagram to check
   * @param className name of UmlClass, the relations has to leading from
   * @return array types of UmlRelation in diagram from some UmlClass
   */
  public static UmlRelationType[] getRelationsTypesFrom(UmlDiagram diagram, String className) {
    return getRelationsTypesFrom(diagram, getClassByName(diagram, className));
  }

  /**
   * Gets types of all relations in diagram which lead from some UmlClass
   *
   * @param diagram UmlDiagram to check
   * @param clazz UmlClass, the relations has to leading from
   * @return array types of UmlRelation in diagram from some UmlClass
   */
  public static UmlRelationType[] getRelationsTypesFrom(UmlDiagram diagram, UmlClass clazz) {
    UmlRelation[] relations = getRelationsFrom(diagram, clazz);

    return getRelationsTypes(relations);
  }

  /**
   * Gets types of all relations in diagram which lead to some UmlClass
   *
   * @param diagram UmlDiagram to check
   * @param className name of UmlClass, the relations has to leading to
   * @return array types of UmlRelation in diagram to some UmlClass
   */
  public static UmlRelationType[] getRelationsTypesTo(UmlDiagram diagram, String className) {
    return getRelationsTypesTo(diagram, getClassByName(diagram, className));
  }

  /**
   * Gets types of all relations in diagram which lead to some UmlClass
   *
   * @param diagram UmlDiagram to check
   * @param clazz UmlClass, the relations has to leading to
   * @return array types of UmlRelation in diagram to some UmlClass
   */
  public static UmlRelationType[] getRelationsTypesTo(UmlDiagram diagram, UmlClass clazz) {
    UmlRelation[] relations = getRelationsTo(diagram, clazz);

    return getRelationsTypes(relations);
  }

  /**
   * Gets types of all relations from diagram which leads from UmlClass1 to UmlClass2
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return array of types of all relations from diagram which leads from UmlClass1 to UmlClass2
   */
  public static UmlRelationType[] getRelationsTypesFromTo(UmlDiagram diagram, String className1, String className2) {
    UmlClass c1 = getClassByName(diagram, className1);
    UmlClass c2 = getClassByName(diagram, className2);

    return getRelationsTypesFromTo(diagram, c1, c2);
  }

  /**
   * Gets types of all relations from diagram which leads from UmlClass1 to UmlClass2
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return array of types of all relations from diagram which leads from UmlClass1 to UmlClass2
   */
  public static UmlRelationType[] getRelationsTypesFromTo(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    UmlRelation[] relations = getRelationsFromTo(diagram, class1, class2);

    return getRelationsTypes(relations);
  }

  /**
   * Gets orientations of from given relations
   *
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @param relations array of relations you want to get orientation types from
   * @return array of orientation types of given relations
   */
  public static UmlRelationOrientationType[] getRelationsOrientationTypes(UmlDiagram diagram, UmlRelation[] relations, UmlClass class1, UmlClass class2) {
    UmlRelationOrientationType[] orientationTypes = new UmlRelationOrientationType[relations.length];
    for (int i = 0; i < relations.length; i++) {
      UmlRelation relation = relations[i];
      switch (relation.getRelationOrientationType()) {
        case UNI :
        case UNDEFINED :
          orientationTypes[i] = relation.getRelationOrientationType();
          break;

        case LEFT :
        case RIGHT :
          if (relation.getFrom().equals(class1) && relation.getTo().equals(class2)) {
            orientationTypes[i] = UmlRelationOrientationType.RIGHT;
          }
          else {
            orientationTypes[i] = UmlRelationOrientationType.LEFT;
          }
          break;
      }
    }

    return orientationTypes;
  }

  /**
   * Gets orientations of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction)
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return array of orientation types of given relations
   */
  public static UmlRelationOrientationType[] getRelationsOrientationTypesBetween(UmlDiagram diagram, String className1, String className2) {
    UmlClass c1 = getClassByName(diagram, className1);
    UmlClass c2 = getClassByName(diagram, className2);

    return getRelationsOrientationTypesBetween(diagram, c1, c2);
  }

  /**
   * Gets orientations of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction)
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return array of orientation types of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of
   *         opposite direction)
   */
  public static UmlRelationOrientationType[] getRelationsOrientationTypesBetween(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    UmlRelation[] relations = getRelationsBetween(diagram, class1, class2);
    UmlRelationOrientationType[] orientationTypes = new UmlRelationOrientationType[relations.length];
    for (int i = 0; i < relations.length; i++) {
      UmlRelation relation = relations[i];
      switch (relation.getRelationOrientationType()) {
        case UNI :
        case UNDEFINED :
          orientationTypes[i] = relation.getRelationOrientationType();
          break;
          
        case LEFT :
        case RIGHT :
          if (relation.getFrom().equals(class1) && relation.getTo().equals(class2)) {
            orientationTypes[i] = UmlRelationOrientationType.RIGHT;
          }
          else {
            orientationTypes[i] = UmlRelationOrientationType.LEFT;
          }
          break;
      }
    }

    return orientationTypes;
  }

  /**
   * Gets stereotypes of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction).
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return Array of stereotypes of given relations
   */
  public static String[] getRelationsStereotypesBetween(UmlDiagram diagram, String className1, String className2) {
    UmlClass c1 = getClassByName(diagram, className1);
    UmlClass c2 = getClassByName(diagram, className2);

    return getRelationsStereotypesBetween(diagram, c1, c2);
  }

  /**
   * Gets stereotypes of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction).
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return Array of stereotypes of given relations
   */
  public static String[] getRelationsStereotypesBetween(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    UmlRelation[] relations = getRelationsBetween(diagram, class1, class2);
    String[] stereotypes = new String[relations.length];
    for (int i = 0; i < relations.length; i++) {
      stereotypes[i] = relations[i].getAttributes().getAttribute(UmlElementAttributesNames.STEREOTYPE, String.class);
    }

    return stereotypes;
  }

  /**
   * Gets qualifiers of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction).
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return Array of qualifiers of given relations
   */
  public static String[][] getRelationsQualifiersBetween(UmlDiagram diagram, String className1, String className2) {
    UmlClass c1 = getClassByName(diagram, className1);
    UmlClass c2 = getClassByName(diagram, className2);

    return getRelationsQualifiersBetween(diagram, c1, c2);
  }

  /**
   * Gets qualifiers of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction).
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return Array of qualifiers of given relations
   */
  public static String[][] getRelationsQualifiersBetween(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    UmlRelation[] relations = getRelationsBetween(diagram, class1, class2);
    String[][] qualifiers = new String[relations.length][2];
    for (int i = 0; i < relations.length; i++) {
      UmlElementAttributes attributes = relations[i].getAttributes();
      qualifiers[i][0] = attributes.getAttribute(UmlElementAttributesNames.QUALIFIER_FROM, String.class);
      qualifiers[i][1] = attributes.getAttribute(UmlElementAttributesNames.QUALIFIER_TO, String.class);
    }

    return qualifiers;
  }

  /**
   * Gets multiplicity of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction).
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return Array of multiplicity of given relations
   */
  public static String[][] getRelationsMultiplicityBetween(UmlDiagram diagram, String className1, String className2) {
    UmlClass c1 = getClassByName(diagram, className1);
    UmlClass c2 = getClassByName(diagram, className2);

    return getRelationsMultiplicityBetween(diagram, c1, c2);
  }

  /**
   * Gets multiplicity of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction).
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return Array of multiplicity of given relations
   */
  public static String[][] getRelationsMultiplicityBetween(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    UmlRelation[] relations = getRelationsBetween(diagram, class1, class2);
    String[][] multiplicity = new String[relations.length][2];
    for (int i = 0; i < relations.length; i++) {
      UmlElementAttributes attributes = relations[i].getAttributes();
      multiplicity[i][0] = attributes.getAttribute(UmlElementAttributesNames.MULTIPLICITY_FROM, String.class);
      multiplicity[i][1] = attributes.getAttribute(UmlElementAttributesNames.MULTIPLICITY_TO, String.class);
      if (multiplicity[i][0] != null) { 
        multiplicity[i][0] = multiplicity[i][0].trim();
      }
      if (multiplicity[i][1] != null) { 
        multiplicity[i][1] = multiplicity[i][1].trim();
      }
    }

    return multiplicity;
  }

  /**
   * Gets roles of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction).
   *
   * @param diagram UmlDiagram to check
   * @param className1 name of first UmlClass
   * @param className2 name of second UmlClass
   * @return Array of roles of given relations
   */
  public static String[][] getRelationsRolesBetween(UmlDiagram diagram, String className1, String className2) {
    UmlClass c1 = getClassByName(diagram, className1);
    UmlClass c2 = getClassByName(diagram, className2);

    return getRelationsRolesBetween(diagram, c1, c2);
  }

  /**
   * Gets roles of all relations from diagram which leads from UmlClass1 to UmlClass2 (or relations of opposite direction).
   *
   * @param diagram UmlDiagram to check
   * @param class1 first UmlClass
   * @param class2 second UmlClass
   * @return Array of roles of given relations
   */
  public static String[][] getRelationsRolesBetween(UmlDiagram diagram, UmlClass class1, UmlClass class2) {
    UmlRelation[] relations = getRelationsBetween(diagram, class1, class2);
    String[][] roles = new String[relations.length][2];
    for (int i = 0; i < relations.length; i++) {
      UmlElementAttributes attributes = relations[i].getAttributes();
      roles[i][0] = attributes.getAttribute(UmlElementAttributesNames.ROLE_FROM, String.class);
      roles[i][1] = attributes.getAttribute(UmlElementAttributesNames.ROLE_TO, String.class);
    }

    return roles;
  }
}
