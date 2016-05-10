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
 * Test of double list property key.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class DoublePropertyKeyListTest extends PropertyKeyTestAncestor {

  protected static Properties emptyProperties;
  protected static Properties propertiesWithEmptyValues;
  protected static Properties propertiesWithCorrectValues;
  protected static Properties propertiesWithWrongValues;

  public static final DoublePropertyKey.List PROPERTY_KEY_LIST = new DoublePropertyKey.List("double.property.list", "|");


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
    assertCollectionsEquals(new ArrayList<Double>(), PROPERTY_KEY_LIST.getListValue(emptyProperties));
  }

  /**
   * When empty list of property values is set, it has to be returned too.
   */
  @Test
  public void testEmptyValueOfPropertyKey() {
    assertCollectionsEquals(new ArrayList<Double>(), PROPERTY_KEY_LIST.getListValue(propertiesWithEmptyValues));
  }

  /**
   * Checks whether list of correct values is returned.
   */
  @Test
  public void testCorrectValueOfPropertyKey() {
    Double[] correctValues = new Double[] {11.0, 12.0, 13.0};

    Assert.assertEquals(Arrays.asList(correctValues), PROPERTY_KEY_LIST.getListValue(propertiesWithCorrectValues));
  }

  /**
   * When in list in property file is wrong value, IllegalArgumentException will be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWrongValueOfPropertyKey() {
    PROPERTY_KEY_LIST.getListValue(propertiesWithWrongValues);
  }

}
