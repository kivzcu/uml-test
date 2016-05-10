package cz.zcu.kiv.oop.properties.keys;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * Test of string list property key.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class StringPropertyKeyListTest extends PropertyKeyTestAncestor {

  protected static Properties emptyProperties;
  protected static Properties propertiesWithEmptyValues;
  protected static Properties propertiesWithCorrectValues;
  protected static Properties propertiesWithWrongValues;

  public static final StringPropertyKey.List PROPERTY_KEY_LIST = new StringPropertyKey.List("string.property.list", "|");


  /**
   * Init all properties which will be used for testing
   *
   * @throws IOException
   * Property file cannot be opened due to it doesn't exists in most cases or some other error.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws IOException {
    emptyProperties = new Properties();

    propertiesWithEmptyValues = new Properties();
    InputStream is = getResourceAsInputStream(PROPERTIES_WITH_EMPTY_VALUES);
    propertiesWithEmptyValues.load(is);
    is.close();

    is = getResourceAsInputStream(PROPERTIES_WITH_CORRECT_VALUES);
    propertiesWithCorrectValues = new Properties();
    propertiesWithCorrectValues.load(is);
    is.close();

    is = getResourceAsInputStream(PROPERTIES_WITH_WRONG_VALUES);
    propertiesWithWrongValues = new Properties();
    propertiesWithWrongValues.load(is);
    is.close();
  }

  /**
   * Sets all used properties to null
   */
  @AfterClass
  public static void tearDownAfterClass() {
    emptyProperties = null;
    propertiesWithEmptyValues = null;
    propertiesWithCorrectValues = null;
    propertiesWithWrongValues = null;
  }


  /**
   * When list of property values is not set, empty ArrayList has to be returned.
   */
  @Test
  public void testUnsetValueOfListPropertyKey() {
    assertCollectionsEquals(new ArrayList<String>(), PROPERTY_KEY_LIST.getListValue(emptyProperties));
  }

  /**
   * When empty list of property values is set, it has to be returned too.
   */
  @Test
  public void testEmptyValueOfPropertyKey() {
    assertCollectionsEquals(new ArrayList<String>(), PROPERTY_KEY_LIST.getListValue(propertiesWithEmptyValues));
  }

  /**
   * Checks whether list of correct values is returned.
   */
  @Test
  public void testCorrectValueOfPropertyKey() {
    String[] correctValues = new String[] {"Hello", "World", "!"};

    Assert.assertEquals(Arrays.asList(correctValues), PROPERTY_KEY_LIST.getListValue(propertiesWithCorrectValues));
  }

  /**
   * When in list in property file is wrong value, IllegalArgumentException will be thrown.
   */
  @Test
  public void testWrongValueOfPropertyKey() {
    String[] wrongValues = new String[] {"string1", "string2"};

    Assert.assertEquals(Arrays.asList(wrongValues), PROPERTY_KEY_LIST.getListValue(propertiesWithWrongValues));
  }

}
