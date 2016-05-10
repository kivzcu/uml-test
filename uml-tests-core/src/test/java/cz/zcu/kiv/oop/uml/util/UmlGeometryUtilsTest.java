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
import cz.zcu.kiv.oop.uml.geometry.UmlPolyline;
import cz.zcu.kiv.oop.uml.geometry.UmlRectangle;
import cz.zcu.kiv.oop.util.Pair;

/**
 * Class contains test method to test functionality of class <code>UmlGeometryUtils</code>.
 *
 * @author Karel Zíbar
 */
public class UmlGeometryUtilsTest {

  protected static UmlDiagram diagram;
  protected static List<UmlElement> elements;

  /**
   * Method will prepare UmlDiagrams for testing. First diagram diagram is right and the second one diagram2 contains duplicate and
   * redundant classes
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    elements = new ArrayList<UmlElement>();
    elements.addAll(createElements());
    diagram = new UmlDiagram(elements);
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
    clazz1.setCoordinates(new UmlRectangle(0.0, 0.0, 5, 5));

    UmlClass clazz2 = new UmlClass();
    clazz2.setClassType(UmlClassType.ABSTRACT_CLASS);
    clazz2.setName("Class2");
    clazz2.setCoordinates(new UmlRectangle(0.0, 0.0, 5, 2));

    UmlClass clazz3 = new UmlClass();
    clazz3.setClassType(UmlClassType.ENUM);
    clazz3.setName("Class3");
    clazz3.setCoordinates(new UmlRectangle(6, 6, 3, 3));

    UmlClass clazz4 = new UmlClass();
    clazz4.setClassType(UmlClassType.INTERFACE);
    clazz4.setName("Class4");
    clazz4.setCoordinates(new UmlRectangle(10.0, 10.0, 5, 5));

    // prepare relations:
    UmlRelation rel1 = new UmlRelation();
    rel1.setRelationType(UmlRelationType.INHERITANCE);
    rel1.setFrom(clazz1);
    rel1.setTo(clazz2);
    rel1.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 20.0, 80.0, 280.0, 80.0, 280.0, 140.0}));

    UmlRelation rel2 = new UmlRelation();
    rel2.setRelationType(UmlRelationType.AGGREGATION);
    rel2.setFrom(clazz2);
    rel2.setTo(clazz3);
    rel2.setRelationLine(new UmlPolyline(new double[] {0.0, 5.0, 0.0, 10.0}));

    UmlRelation rel3 = new UmlRelation();
    rel3.setRelationType(UmlRelationType.ASSOCIATION);
    rel3.setFrom(clazz3);
    rel3.setTo(clazz4);
    rel3.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 60.0, 10.0, 60.0, 150.0}));

    UmlRelation rel4 = new UmlRelation();
    rel4.setRelationType(UmlRelationType.COMPOSITION);
    rel4.setFrom(clazz4);
    rel4.setTo(clazz1);
    rel4.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 60.0, 10.0, 60.0, 150.0}));

    UmlRelation rel5 = new UmlRelation();
    rel5.setRelationType(UmlRelationType.DASHEDLINE);
    rel5.setFrom(clazz1);
    rel5.setTo(clazz3);
    rel5.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 60.0, 10.0, 60.0, 150.0}));

    UmlRelation rel6 = new UmlRelation();
    rel6.setRelationType(UmlRelationType.DEPENDENCY);
    rel6.setFrom(clazz2);
    rel6.setTo(clazz4);
    rel6.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 60.0, 10.0, 60.0, 150.0}));

    UmlRelation rel7 = new UmlRelation();
    rel7.setRelationType(UmlRelationType.IMPLEMENTATION);
    rel7.setFrom(clazz3);
    rel7.setTo(clazz1);
    rel7.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 60.0, 10.0, 60.0, 150.0}));

    UmlRelation rel8 = new UmlRelation();
    rel8.setRelationType(UmlRelationType.UNDEFINED);
    rel8.setFrom(clazz4);
    rel8.setTo(clazz1);
    rel8.setRelationLine(new UmlPolyline(new double[] {10.0, 10.0, 60.0, 10.0, 60.0, 150.0}));

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
   * Tests checking if diagram contains some over-lapped UmlClasses
   */
  @Test
  public void testGetOverlappedClasses() {
    Assert.assertTrue(UmlGeometryUtils.containsOverlappedClasses(diagram));
  }

  /**
   * Tests if correct pair of over-lapped UmlClasses from diagram is returned
   */
  @Test
  public void testGetPairsOfOverlappedClasses() {
    Pair<UmlClass, UmlClass>[] pairs = UmlGeometryUtils.getPairsOfOverlappedClasses(diagram);

    List<UmlClass> classes = new ArrayList<UmlClass>();
    for (UmlElement element : elements.subList(0, 4)) {
      classes.add((UmlClass)element);
    }
    Assert.assertEquals(1, pairs.length);
    Assert.assertEquals(classes.get(0), pairs[0].getLeft());
    Assert.assertEquals(classes.get(1), pairs[0].getRight());
  }

  /**
   * Tests checking if diagram contains relation which goes from/to some corner of UmlClass and it is perpendicular to some edge of
   * UmlClass
   */
  @Test
  public void testContainsRelationsExtendingClassEdge() {
    Assert.assertTrue(UmlGeometryUtils.containsRelationsExtendingClassEdge(diagram));
  }

  /**
   * Tests getting count of crossed relations
   */
  @Test
  public void testGetCountOfRelationsCrossings() {
    int count = UmlGeometryUtils.getCountOfRelationsCrossings(diagram);
    Assert.assertEquals(21, count);
  }

}
