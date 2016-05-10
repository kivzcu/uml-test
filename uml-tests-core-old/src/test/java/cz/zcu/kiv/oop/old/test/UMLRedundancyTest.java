package cz.zcu.kiv.oop.old.test;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.oop.old.uml.UMLDiagram;

public class UMLRedundancyTest {

  /** Number of classes in UML redundancyTest.uxf */
  protected static final int NUMBER_OF_CLASSES = 3;

  /** Number of interfaces in UML redundancy.uxf */
  protected static final int NUMBER_OF_INTERFACES = 3;

  /** Number of enums in UML redundancy.uxf */
  protected static final int NUMBER_OF_ENUMS = 4;

  /** Name of redundant class in redundancy.uxf */
  protected static final String REDUNDANT_CLASS_NAME = "RedundantClass";

  /** Name of redundant interface in redundancy.uxf */
  protected static final String REDUNDANT_INTERFACE_NAME = "RedundantInterface";

  /** Name of redundant enum in redundancy.uxf */
  protected static final String REDUNDANT_ENUM_NAME = "RedundantEnum";

  protected static Collection<String> expectedClasses;
  protected static Collection<String> expectedEnums;
  protected static Collection<String> expectedIntefaces;

  protected static UMLDiagram ud;

  @BeforeClass
  public static void setUpBeforeClass() {
    URL url = UMLObjectsTest.class.getResource("redundancyTest.uxf");
    ud = new UMLDiagram(url.getFile());

    expectedEnums = new ArrayList<String>();
    expectedEnums.add("Work");
    expectedEnums.add("Work2");
    expectedEnums.add("Work3");

    expectedIntefaces = new ArrayList<String>();
    expectedIntefaces.add("Visible");
    expectedIntefaces.add("Visible2");

    expectedClasses = new ArrayList<String>();
    expectedClasses.add("SimpleClass");
    expectedClasses.add("SimpleClass2");
  }

  @Test
  public void testNumberOfClasses() {
    assertTrue("Wrong number of classes: ", ud.getNumberOfClasses() == NUMBER_OF_CLASSES);
  }

  @Test
  public void testNumberOfInterfaces() {
    assertTrue("Wrong number of interfaces: ", ud.getNumberOfInterfaces() == NUMBER_OF_INTERFACES);
  }

  @Test
  public void testNumberOfEnums() {
    assertTrue("Wrong number of enums: ", ud.getNumberOfEnums() == NUMBER_OF_ENUMS);
  }

  @Test
  public void testRedundantClasses() {
    assertTrue("Number of redundat classes fail: ", ud.redundantClasses(expectedClasses).size() == 1);

    String redundantClassName = (String)ud.redundantClasses(expectedClasses).toArray()[0];
    assertTrue("Redundant class name fail: ", redundantClassName.equals(REDUNDANT_CLASS_NAME));
  }

  @Test
  public void testRedundantInterfaces() {
    assertTrue("Number of redundat interfaces fail: ", ud.redundantInterfaces(expectedIntefaces).size() == 1);

    String redundantInterfaceName = (String)ud.redundantInterfaces(expectedIntefaces).toArray()[0];
    assertTrue("Redundant class name fail: ", redundantInterfaceName.equals(REDUNDANT_INTERFACE_NAME));
  }

  @Test
  public void testRedundantEnums() {
    assertTrue("Number of redundat enums fail: ", ud.redundantEnums(expectedEnums).size() == 1);

    String redundantEnumName = (String)ud.redundantEnums(expectedEnums).toArray()[0];
    assertTrue("Redundant class name fail: ", redundantEnumName.equals(REDUNDANT_ENUM_NAME));
  }
}
