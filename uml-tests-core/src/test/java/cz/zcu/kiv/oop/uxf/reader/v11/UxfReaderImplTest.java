package cz.zcu.kiv.oop.uxf.reader.v11;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElement;
import cz.zcu.kiv.oop.uml.element.UmlNote;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uxf.exception.UxfReaderException;

/**
 * Test of implementation of UXF reader version 11.
 *
 * @author Karel Zíbar
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfReaderImplTest {

  protected static UxfReaderImpl uxfReader;
  protected static UmlDiagram expectedUmlDiagram;
  protected static UmlDiagram readUmlDiagram;

  /**
   * Method prepare expected UmlDiagram and read another for comparing.
   *
   * @throws UxfReaderException
   *    cannot open Uxf file
   */
  @BeforeClass
  public static void setUpBeforeClass() throws UxfReaderException {
    uxfReader = new UxfReaderImpl();
    expectedUmlDiagram = prepareUmlDiagram();
    readUmlDiagram = uxfReader.readDiagram(getFile("diagram-v11.uxf"));
  }

  /**
   * Method prepare expected UmlDiagram
   *
   * @return
   *    expected UmlDiagram
   */
  protected static UmlDiagram prepareUmlDiagram() {
    List<UmlElement> elements = new ArrayList<UmlElement>();

    UmlClass clazz1 = new UmlClass();
    clazz1.setClassType(UmlClassType.INTERFACE);
    clazz1.setName("Class 1");
    elements.add(clazz1);

    UmlClass clazz2 = new UmlClass();
    clazz2.setClassType(UmlClassType.ABSTRACT_CLASS);
    clazz2.setName("Class 2");
    elements.add(clazz2);

    UmlClass clazz3 = new UmlClass();
    clazz3.setClassType(UmlClassType.CLASS);
    clazz3.setName("Class 3");
    elements.add(clazz3);

    UmlRelation relation1 = new UmlRelation();
    relation1.setRelationType(UmlRelationType.IMPLEMENTATION);
    relation1.setRelationOrientationType(UmlRelationOrientationType.LEFT);
    relation1.setFrom(clazz2);
    relation1.setTo(clazz1);
    elements.add(relation1);

    UmlRelation relation2 = new UmlRelation();
    relation2.setRelationType(UmlRelationType.INHERITANCE);
    relation2.setRelationOrientationType(UmlRelationOrientationType.LEFT);
    relation2.setFrom(clazz3);
    relation2.setTo(clazz2);
    elements.add(relation2);

    UmlNote note = new UmlNote();
    note.setText("Hello World");
    elements.add(note);

    UmlDiagram diagram = new UmlDiagram(elements);

    return diagram;
  }

  /**
   * Method sets all tested diagrams to null.
   */
  @AfterClass
  public static void tearDownAfterClass() {
    uxfReader = null;
    expectedUmlDiagram = null;
    readUmlDiagram = null;
  }

  protected static String getFile(String fileName) {
    URL url = UxfReaderImplTest.class.getResource(fileName);
    if (url == null) {
      return null;
    }

    try {
      return url.toURI().getPath();
    }
    catch (URISyntaxException exc) {
      return null;
    }
  }

  /**
   * Tests if UxfReaderException is thrown when trying to read diagram from file with incorrect filename
   *
   * @throws UxfReaderException
   *    file doesn't exists
   */
  @Test(expected = UxfReaderException.class)
  public void testEmptyFileName() throws UxfReaderException {
    uxfReader.readDiagram("");
  }

  /**
   * Tests if UxfReaderException is thrown when trying to read diagram from nonexistent file
   *
   * @throws UxfReaderException
   *    file doesn't exists
   */
  @Test(expected = UxfReaderException.class)
  public void testNonexistentFile() throws UxfReaderException {
    uxfReader.readDiagram("this-file-doesnt-exist.uxf");
  }

  /**
   * Checks if correct list of elements in readDiagram has been read
   */
  @Test
  public void testReadDiagramElements() {
    Assert.assertEquals(expectedUmlDiagram.getElementsCount(), readUmlDiagram.getElementsCount());
    Assert.assertTrue(CollectionUtils.equalsIgnoreOrder(expectedUmlDiagram.getElements(), readUmlDiagram.getElements()));
  }

  /**
   * Checks if correct list of UmlClass in readDiagram has been read
   */
  @Test
  public void testReadDiagramClasses() {
    Assert.assertEquals(expectedUmlDiagram.getClassesCount(), readUmlDiagram.getClassesCount());
    Assert.assertTrue(CollectionUtils.equalsIgnoreOrder(expectedUmlDiagram.getClasses(), readUmlDiagram.getClasses()));
  }

  /**
   * Checks if correct list of UmlRelation in readDiagram has been read
   */
  @Test
  public void testReadDiagramRelations() {
    Assert.assertEquals(expectedUmlDiagram.getRelationsCount(), readUmlDiagram.getRelationsCount());
    Assert.assertTrue(CollectionUtils.equalsIgnoreOrder(expectedUmlDiagram.getRelations(), readUmlDiagram.getRelations()));
  }

  /**
   * Checks if correct list of UmlNote in readDiagram has been read
   */
  @Test
  public void testReadDiagramNotes() {
    Assert.assertEquals(expectedUmlDiagram.getNotesCount(), readUmlDiagram.getNotesCount());
    Assert.assertTrue(CollectionUtils.equalsIgnoreOrder(expectedUmlDiagram.getNotes(), readUmlDiagram.getNotes()));
  }

  /**
   * Checks if expectedUmlDiagram and readUmlDiagram are the same.
   */
  @Test
  public void testReadDiagramEquals() {
    Assert.assertEquals(expectedUmlDiagram, readUmlDiagram);
  }

}
