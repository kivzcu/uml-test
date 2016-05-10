package cz.zcu.kiv.oop.old.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import cz.zcu.kiv.oop.old.uml.UMLDiagram;

public class UMLObjectsTest {

  protected static UMLDiagram ud;

  @BeforeClass
  public static void setUpBeforeClass() {
    URL url = UMLObjectsTest.class.getResource("objectsTest.uxf");
    ud = new UMLDiagram(url.getFile());
  }

  @Test
  public void testClassExist() {
    assertTrue("Class exist: ", ud.classExists("SimpleClass"));
  }

  @Test
  @Ignore
  public void testAbstractClassExist() {
    assertTrue("Abstract class exist: ", ud.classExists("AbstractClass"));
  }

  @Test
  public void testEnumExist() {
    assertTrue("Enum exist: ", ud.enumExists("Work"));
  }

  @Test
  public void testInterfaceExist() {
    assertTrue("Interface exist: ", ud.interfaceExists("Visible"));
  }

  @Test
  public void testAnyNoteExist() {
    assertTrue("Any note exist: ", ud.noteExists());
  }

  @Test
  public void testWrongNameDoesNotExist() {
    assertFalse("Wrong Name is in classes", ud.classExists("WrongName"));
  }

}
