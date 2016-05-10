package cz.zcu.kiv.oop.uml.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElement;
import cz.zcu.kiv.oop.uml.element.UmlNote;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uml.geometry.UmlLine;
import cz.zcu.kiv.oop.uml.geometry.UmlPolyline;
import cz.zcu.kiv.oop.uml.geometry.UmlRectangle;

/**
 * Class contains test method to test functionality of class <code>UmlUtils</code>.
 *
 * @author Karel Zíbar
 */
public class UmlUtilsTest {

  protected static UmlDiagram diagram;
  protected static UmlDiagram diagram2;
  protected static List<UmlElement> elements;
  protected static List<UmlElement> elements2;

  /**
   * Method will prepare UmlDiagrams for testing. First diagram diagram is right and the second one diagram2 contains duplicate and
   * redundant classes
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    elements = new ArrayList<UmlElement>();
    elements.addAll(createElements());
    diagram = new UmlDiagram(elements);

    elements2 = new ArrayList<UmlElement>(elements);
    UmlClass clazz1 = new UmlClass();
    clazz1.setClassType(UmlClassType.CLASS);
    clazz1.setName("RedundantClass1");

    UmlClass clazz2 = new UmlClass();
    clazz2.setClassType(UmlClassType.ABSTRACT_CLASS);
    clazz2.setName("RedundantClass2");

    UmlRelation unUsedRelation = new UmlRelation();
    UmlRelation blindRelation = new UmlRelation();
    blindRelation.setFrom(null);
    blindRelation.setTo(clazz2);

    UmlNote note = new UmlNote();
    note.setText("Bad personal number format\nBad number format");

    elements2.add(elements.get(0));
    elements2.add(elements.get(1));
    elements2.add(clazz1);
    elements2.add(clazz2);
    elements2.add(unUsedRelation);
    elements2.add(blindRelation);
    elements2.add(note);

    diagram2 = new UmlDiagram(elements2);
  }

  /**
   * Method will prepare elements for UmlDiagram
   *
   * @return collection of created UmlElements
   */
  private static List<UmlElement> createElements() {
    List<UmlElement> elements = new ArrayList<UmlElement>();

    // prepare classes:
    UmlClass clazz1 = new UmlClass();
    clazz1.setClassType(UmlClassType.CLASS);
    clazz1.setName("Class1");
    clazz1.setCoordinates(new UmlRectangle(1.0, 10.0, 10, 5));

    UmlClass clazz2 = new UmlClass();
    clazz2.setClassType(UmlClassType.ABSTRACT_CLASS);
    clazz2.setName("Class2");
    clazz2.setCoordinates(new UmlRectangle(1.0, 10.0, 5, 2));

    UmlClass clazz3 = new UmlClass();
    clazz3.setClassType(UmlClassType.ENUM);
    clazz3.setName("Class3");
    clazz3.setCoordinates(new UmlRectangle(5, 5, 10, 20));

    UmlClass clazz4 = new UmlClass();
    clazz4.setClassType(UmlClassType.INTERFACE);
    clazz4.setName("Class4");

    // prepare relations:
    UmlRelation rel1 = new UmlRelation();
    rel1.setRelationType(UmlRelationType.INHERITANCE);
    rel1.setFrom(clazz1);
    rel1.setTo(clazz2);
    rel1.setRelationLine(new UmlLine(10.0, 10.0, 10.0, 230.0));

    UmlRelation rel2 = new UmlRelation();
    rel2.setRelationType(UmlRelationType.AGGREGATION);
    rel2.setFrom(clazz2);
    rel2.setTo(clazz3);
    rel2.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 10.0, 80.0, 280.0, 80.0, 280.0, 140.0}));

    UmlRelation rel3 = new UmlRelation();
    rel3.setRelationType(UmlRelationType.ASSOCIATION);
    rel3.setFrom(clazz3);
    rel3.setTo(clazz4);
    rel3.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 60.0, 10.0, 60.0, 150.0}));

    UmlRelation rel4 = new UmlRelation();
    rel4.setRelationType(UmlRelationType.COMPOSITION);
    rel4.setFrom(clazz4);
    rel4.setTo(clazz1);
    rel4.setRelationLine(new UmlPolyline(new double[] {100.0, 100.0, 160.0, 110.0, 160.0, 150.0}));

    UmlRelation rel5 = new UmlRelation();
    rel5.setRelationType(UmlRelationType.DASHEDLINE);
    rel5.setFrom(clazz1);
    rel5.setTo(clazz3);
    rel5.setRelationLine(new UmlPolyline(new double[] {110.0, 110.0, 111.0, 111.0}));

    UmlRelation rel6 = new UmlRelation();
    rel6.setRelationType(UmlRelationType.DEPENDENCY);
    rel6.setFrom(clazz2);
    rel6.setTo(clazz4);
    rel6.setRelationLine(new UmlPolyline(new double[] {112.0, 112.0, 113.0, 113.0}));

    UmlRelation rel7 = new UmlRelation();
    rel7.setRelationType(UmlRelationType.IMPLEMENTATION);
    rel7.setFrom(clazz3);
    rel7.setTo(clazz1);
    rel7.setRelationLine(new UmlPolyline(new double[] {114.0, 114.0, 115.0, 115.0}));

    UmlRelation rel8 = new UmlRelation();
    rel8.setRelationType(UmlRelationType.UNDEFINED);
    rel8.setFrom(clazz4);
    rel8.setTo(clazz1);
    rel8.setRelationLine(new UmlPolyline(new double[] {117.0, 117.0, 118.0, 118.0}));

    UmlNote note = new UmlNote();
    note.setText("A11B0542P\nDU-06");

    // adding elements
    elements.add(clazz1);
    elements.add(clazz2);
    elements.add(clazz3);
    elements.add(clazz4);
    elements.add(rel1);
    elements.add(rel2);
    elements.add(rel3);
    elements.add(rel4);
    elements.add(rel5);
    elements.add(rel6);
    elements.add(rel7);
    elements.add(rel8);
    elements.add(note);

    return elements;
  }

  /**
   * Tests checking if diagram contains UmlNote with text in correcf format
   */
  @Test
  public void testHasNoteWithCorrectFormat() {
    boolean b1 = UmlUtils.hasNoteWithCorrectFormat(diagram);
    boolean b2 = UmlUtils.hasNoteWithCorrectFormat(diagram2);

    Assert.assertTrue(b1);
    Assert.assertFalse(b2);
  }

  /**
   * Tests text in UmlNote if it contains personal id of student and number of home exercise.
   */
  @Test
  public void testHasNoteWithCorrectData() {
    boolean b1 = UmlUtils.hasNoteWithCorrectData(diagram, "A11B0542P", "DU-06");
    boolean b2 = UmlUtils.hasNoteWithCorrectData(diagram2, "A11B0542P", "DU-06");

    Assert.assertTrue(b1);
    Assert.assertFalse(b2);
  }

  /**
   * Tests method for checking whether some class is in diagram
   */
  @Test
  public void testContainsClass() {
    UmlClass clazz = new UmlClass();
    clazz.setName("NotInDiagram");
    clazz.setClassType(UmlClassType.CLASS);

    Assert.assertTrue(UmlUtils.containsClass(diagram, "Class1"));
    Assert.assertFalse(UmlUtils.containsClass(diagram, "ClassDontExists"));
    Assert.assertTrue(UmlUtils.containsClass(diagram, (UmlClass)elements.get(0)));
    Assert.assertFalse(UmlUtils.containsClass(diagram, clazz));
    Assert.assertTrue(UmlUtils.containsClass(diagram, UmlClassType.CLASS, "Class1"));
    Assert.assertFalse(UmlUtils.containsClass(diagram, UmlClassType.INTERFACE, "Class1"));
  }

  /**
   * Tests checking if diagram contains classes with unique names
   */
  @Test
  public void testContainsClassesWithUniqueNames() {
    Assert.assertTrue(UmlUtils.containsClassesWithUniqueNames(diagram));
    Assert.assertFalse(UmlUtils.containsClassesWithUniqueNames(diagram2));
  }

  /**
   * Check if correct class names are returned from diagram
   */
  @Test
  public void testGetClassNames() {
    String[] classesNames = UmlUtils.getClassesNames(diagram);
    String[] patternNames = {"Class1", "Class2", "Class3", "Class4"};
    String[] normalClassesNames = UmlUtils.getClassesNames(diagram2, UmlClassType.CLASS);
    String[] normalPatternNames = {"Class1", "Class1", "RedundantClass1"};

    Assert.assertArrayEquals(patternNames, classesNames);
    Assert.assertArrayEquals(normalPatternNames, normalClassesNames);
  }

  /**
   * Tests checking if diagram contains some classes that are not present in pattern diagram
   */
  @Test
  public void testContainsRedundantClasses() {
    String[] names = new String[] {"Class1", "Class2", "Class3", "Class4"};
    List<UmlClass> classList = new ArrayList<UmlClass>();
    for (UmlElement element : elements.subList(0, 4)) {
      classList.add((UmlClass)element);
    }
    UmlClass[] classes = new UmlClass[classList.size()];
    classes = classList.toArray(classes);

    Assert.assertTrue(UmlUtils.containsRedundantClasses(diagram2, classes));
    Assert.assertFalse(UmlUtils.containsRedundantClasses(diagram, classes));

    Assert.assertTrue(UmlUtils.containsRedundantClasses(diagram2, names));
    Assert.assertFalse(UmlUtils.containsRedundantClasses(diagram, names));
  }

  /**
   * Tests if correct redundant classes are returned.
   */
  @Test
  public void testGetRedundantClasses() {
    String[] names = new String[] {"Class1", "Class2", "Class3", "Class4"};
    List<UmlClass> classList = new ArrayList<UmlClass>();
    for (UmlElement element : elements.subList(0, 4)) {
      classList.add((UmlClass)element);
    }
    UmlClass[] classes = new UmlClass[classList.size()];
    classes = classList.toArray(classes);

    UmlClass[] redundantClasses = UmlUtils.getRedundantClases(diagram2, names);
    UmlClass[] redundantClasses2 = UmlUtils.getRedundantClases(diagram2, classes);
    UmlClass[] patternRedundantClasses = {(UmlClass)elements2.get(15), (UmlClass)elements2.get(16)};

    Assert.assertArrayEquals(patternRedundantClasses, redundantClasses);
    Assert.assertArrayEquals(patternRedundantClasses, redundantClasses2);
  }

  /**
   * Tests checking if diagram contains some unused relations
   */
  @Test
  public void testContainsUnUsedRelation() {
    Assert.assertTrue(UmlUtils.containsUnusedRelation(diagram2));
    Assert.assertFalse(UmlUtils.containsUnusedRelation(diagram));
  }

  /**
   * Tests checking if diagram contains some blind relations
   */
  @Test
  public void testContainsBlindRelations() {
    Assert.assertTrue(UmlUtils.containsBlindRelation(diagram2));
    Assert.assertFalse(UmlUtils.containsBlindRelation(diagram));
  }

  /**
   * Tests checking if diagram contains some unused or blind relations
   */
  @Test
  public void testContainsBlindOrUnusedRelation() {
    Assert.assertTrue(UmlUtils.containsBlindOrUnusedRelation(diagram2));
    Assert.assertFalse(UmlUtils.containsBlindOrUnusedRelation(diagram));
  }

  /**
   * Tests checking if there is some relation between two classes
   */
  @Test
  public void testIsRelationBetween() {
    Assert.assertTrue(UmlUtils.isRelationBetween(diagram2, "Class1", "Class2"));
    Assert.assertTrue(UmlUtils.isRelationBetween(diagram2, "Class2", "Class1"));
    Assert.assertFalse(UmlUtils.isRelationBetween(diagram2, "RedundantClass1", "Class1"));

    List<UmlClass> classes = diagram2.getClasses();
    Assert.assertTrue(UmlUtils.isRelationBetween(diagram2, classes.get(1), classes.get(2)));
    Assert.assertFalse(UmlUtils.isRelationBetween(diagram2, classes.get(1), classes.get(1)));
  }

  /**
   * Tests getting relation between two classes
   */
  @Test
  public void testGetRelationsBetween() {
    UmlRelation[] relations = UmlUtils.getRelationsBetween(diagram, "Class1", "Class2");
    List<UmlRelation> rels = new ArrayList<UmlRelation>();
    for (UmlElement element : elements.subList(4, 12)) {
      rels.add((UmlRelation)element);
    }
    UmlRelation[] patternRelations = {rels.get(0)};

    Assert.assertArrayEquals(patternRelations, relations);
    Assert.assertTrue(UmlUtils.isRelationBetween(diagram2, "Class2", "Class1"));
    Assert.assertFalse(UmlUtils.isRelationBetween(diagram2, "RedundantClass1", "Class1"));
  }

  /**
   * Tests checking if there is some relation leading from one class to another class
   */
  @Test
  public void testIsRelationFromTo() {
    Assert.assertTrue(UmlUtils.isRelationFromTo(diagram, "Class1", "Class2"));
    Assert.assertFalse(UmlUtils.isRelationFromTo(diagram, "Class2", "Class1"));
  }

  /**
   * Tests getting relations leading from one class to another class
   */
  @Test
  public void testGetRelationsFromTo() {
    UmlRelation[] patternRelations = {diagram.getRelations().get(0)};
    UmlRelation[] relations = UmlUtils.getRelationsFromTo(diagram, "Class1", "Class2");

    Assert.assertArrayEquals(patternRelations, relations);
  }

  /**
   * Tests getting types of relations pointing out of som class
   */
  @Test
  public void testGetRelationTypesFrom() {
    UmlRelationType[] patternTypes = {UmlRelationType.INHERITANCE, UmlRelationType.DASHEDLINE};
    UmlRelationType[] types = UmlUtils.getRelationsTypesFrom(diagram, "Class1");

    Assert.assertArrayEquals(patternTypes, types);
  }

  /**
   * Tests getting types of relations pointing to some class
   */
  @Test
  public void testGetRelationTypesTo() {
    UmlRelationType[] patternTypes = {UmlRelationType.IMPLEMENTATION, UmlRelationType.COMPOSITION, UmlRelationType.UNDEFINED};
    UmlRelationType[] types = UmlUtils.getRelationsTypesTo(diagram, "Class1");

    Assert.assertArrayEquals(patternTypes, types);
  }

  /**
   * Tests getting types of relations leading from one class to another class
   */
  @Test
  public void testGetRelationTypesFromTo() {
    UmlRelationType[] patternTypes = {UmlRelationType.INHERITANCE};
    UmlRelationType[] types = UmlUtils.getRelationsTypesFromTo(diagram, "Class1", "Class2");

    Assert.assertArrayEquals(patternTypes, types);
  }

  /**
   * Tests getting types of relations between two classes
   */
  @Test
  public void testGetRelationTypesBetween() {
    UmlRelationType[] patternTypes = {UmlRelationType.INHERITANCE};
    UmlRelationType[] types = UmlUtils.getRelationsTypesBetween(diagram, "Class1", "Class2");

    Assert.assertArrayEquals(patternTypes, types);
  }

  /**
   * Tests getting relations pointing out of some class
   */
  @Test
  public void testGetRelationsFrom() {
    UmlRelation[] patternRelations = {(UmlRelation)elements.get(4), (UmlRelation)elements.get(8)};
    UmlRelation[] relations = UmlUtils.getRelationsFrom(diagram, "Class1");

    Assert.assertArrayEquals(patternRelations, relations);
  }

  /**
   * Tests getting relations pointing to some class
   */
  @Test
  public void testGetRelationsTo() {
    UmlRelation[] patternRelations = {(UmlRelation)elements.get(10), (UmlRelation)elements.get(7), (UmlRelation)elements.get(11)};
    UmlRelation[] relations = UmlUtils.getRelationsTo(diagram, "Class1");

    Assert.assertArrayEquals(patternRelations, relations);
  }

}
