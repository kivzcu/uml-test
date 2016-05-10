package cz.zcu.kiv.oop.uml;

import cz.zcu.kiv.oop.uml.element.*;
import org.junit.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test of UML diagram class.
 *
 * @author Karel Zíbar
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlDiagramTest {

  protected static UmlDiagram diagram;

  protected static List<UmlElement> elements;
  protected static List<UmlClass> classes;
  protected static List<UmlRelation> relations;
  protected static List<UmlNote> notes;

  /**
   * Sets up diagram for testing
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    createElements();

    diagram = new UmlDiagram(elements);
  }

  /**
   * Null all used static stuffs
   */
  @AfterClass
  public static void tearDownAfterClass() {
    diagram = null;
    elements = null;
    classes = null;
    relations = null;
    notes = null;
  }

  /**
   * Create elements for diagram to test
   */
  protected static void createElements() {
    // prepare classes:
    classes = new ArrayList<UmlClass>();

    UmlClass class1 = new UmlClass();
    class1.setClassType(UmlClassType.CLASS);
    class1.setName("Class1");
    classes.add(class1);

    UmlClass class2 = new UmlClass();
    class2.setClassType(UmlClassType.CLASS);
    class2.setName("Class2");
    classes.add(class2);

    UmlClass class3 = new UmlClass();
    class3.setClassType(UmlClassType.CLASS);
    class3.setName("Class3");
    classes.add(class3);

    UmlClass class4 = new UmlClass();
    class4.setClassType(UmlClassType.ABSTRACT_CLASS);
    class4.setName("AbstractClass1");
    classes.add(class4);

    UmlClass class5 = new UmlClass();
    class5.setClassType(UmlClassType.ABSTRACT_CLASS);
    class5.setName("AbstractClass2");
    classes.add(class5);

    UmlClass class6 = new UmlClass();
    class6.setClassType(UmlClassType.ENUM);
    class6.setName("Enum1");
    classes.add(class6);

    UmlClass class7 = new UmlClass();
    class7.setClassType(UmlClassType.INTERFACE);
    class7.setName("Interface1");
    classes.add(class7);

    // prepare relations:
    relations = new ArrayList<UmlRelation>();

    UmlRelation relation1 = new UmlRelation();
    relation1.setRelationType(UmlRelationType.INHERITANCE);
    relation1.setFrom(class1);
    relation1.setTo(class2);
    relations.add(relation1);

    UmlRelation relation2 = new UmlRelation();
    relation2.setRelationType(UmlRelationType.AGGREGATION);
    relation2.setFrom(class2);
    relation2.setTo(class3);
    relations.add(relation2);

    UmlRelation relation3 = new UmlRelation();
    relation3.setRelationType(UmlRelationType.ASSOCIATION);
    relation3.setFrom(class3);
    relation3.setTo(class4);
    relations.add(relation3);

    UmlRelation relation4 = new UmlRelation();
    relation4.setRelationType(UmlRelationType.COMPOSITION);
    relation4.setFrom(class4);
    relation4.setTo(class5);
    relations.add(relation4);

    UmlRelation relation5 = new UmlRelation();
    relation5.setRelationType(UmlRelationType.DASHEDLINE);
    relation5.setFrom(class5);
    relation5.setTo(class6);
    relations.add(relation5);

    UmlRelation relation6 = new UmlRelation();
    relation6.setRelationType(UmlRelationType.DEPENDENCY);
    relation6.setFrom(class6);
    relation6.setTo(class7);
    relations.add(relation6);

    UmlRelation relation7 = new UmlRelation();
    relation7.setRelationType(UmlRelationType.IMPLEMENTATION);
    relation7.setFrom(class7);
    relation7.setTo(class1);
    relations.add(relation7);

    UmlRelation relation8 = new UmlRelation();
    relation8.setRelationType(UmlRelationType.UNDEFINED);
    relation8.setFrom(class1);
    relation8.setTo(class3);
    relations.add(relation8);

    // prepare notes:
    notes = new ArrayList<UmlNote>();

    UmlNote note = new UmlNote();
    note.setText("Hello World!");
    notes.add(note);

    // prepare elements list
    elements = new ArrayList<UmlElement>();
    elements.addAll(classes);
    elements.addAll(relations);
    elements.addAll(notes);
  }

  /**
   * Tests getting count of elements in diagram
   */
  @Test
  public void testElementsCount() {
    assertEquals(elements.size(), diagram.getElementsCount());
  }

  /**
   * Tests getting elements in diagram
   */
  @Test
  public void tesElementsList() {
    assertCollectionsEquals(elements, diagram.getElements());
  }

  /**
   * Tests getting count of classes in diagram
   */
  @Test
  public void testClassesCount() {
    assertEquals(classes.size(), diagram.getClassesCount());
  }

  /**
   * Tests getting classes in diagram
   */
  @Test
  public void tesClassesList() {
    assertCollectionsEquals(classes, diagram.getClasses());
  }

  /**
   * Tests getting count of notes in diagram
   */
  @Test
  public void testNotesCount() {
    assertEquals(notes.size(), diagram.getNotesCount());
  }

  /**
   * Tests getting notes in diagram
   */
  @Test
  public void tesNotesList() {
    assertCollectionsEquals(notes, diagram.getNotes());
  }

  /**
   * Tests getting count of relations in diagram
   */
  @Test
  public void testRelationsCount() {
    assertEquals(relations.size(), diagram.getRelationsCount());
  }

  /**
   * Tests getting relations in diagram
   */
  @Test
  public void tesRelationsList() {
    assertCollectionsEquals(relations, diagram.getRelations());
  }

  /**
   * Tests getting count of classes of certain type in diagram
   */
  @Test
  public void testClassesTypesCount() {
    assertEquals(3, diagram.getClassesCount(UmlClassType.CLASS));
    assertEquals(2, diagram.getClassesCount(UmlClassType.ABSTRACT_CLASS));
    assertEquals(1, diagram.getClassesCount(UmlClassType.ENUM));
    assertEquals(1, diagram.getClassesCount(UmlClassType.INTERFACE));
  }

  /**
   * Tests getting classes of certain type in diagram
   */
  @Test
  public void testListOfClassesOfSpecificType() {
    List<UmlClass> normalClasses = diagram.getClasses(UmlClassType.CLASS);
    List<UmlClass> abstractClasses = diagram.getClasses(UmlClassType.ABSTRACT_CLASS);
    List<UmlClass> enums = diagram.getClasses(UmlClassType.ENUM);
    List<UmlClass> interfaces = diagram.getClasses(UmlClassType.INTERFACE);

    assertCollectionsEquals(classes.subList(0, 3), normalClasses);
    assertCollectionsEquals(classes.subList(3, 5), abstractClasses);
    assertCollectionsEquals(classes.subList(5, 6), enums);
    assertCollectionsEquals(classes.subList(6, 7), interfaces);
  }

  /**
   * Tests if clone method return correct instance of UmlDiagram
   */
  @Test
  public void testClone() {
    UmlDiagram newDiagram = diagram.clone();
    assertTrue(diagram.equals(newDiagram));
  }

  /**
   * Tests if two instances of UmlDiagram are the same if they are created with the same list of UmlElements
   */
  @Test
  public void testDifferentDiagramsNotEqual() {
    List<UmlElement> elements2 = new ArrayList<UmlElement>(elements);
    elements2.add(new UmlClass());

    UmlDiagram diagram2 = new UmlDiagram(elements2);
    Assert.assertNotEquals(diagram2, diagram);
  }

  public static <E> void assertCollectionsEquals(Collection<E> expected, Collection<E> actual) {
    boolean equals = CollectionUtils.equalsIgnoreOrder(expected, actual);

    if (!equals) {
      throw new ComparisonFailure("Collections are not equal", expected + "", actual + "");
    }
  }
}
