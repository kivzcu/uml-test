package cz.zcu.kiv.oop.properties.keys;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Test of long property key.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class LongPropertyKeyTest extends PropertyKeyTestAncestor {

  protected static Properties emptyProperties;
  protected static Properties propertiesWithEmptyValues;
  protected static Properties propertiesWithCorrectValues;
  protected static Properties propertiesWithWrongValues;

  public static final Long CORRECT_VALUE = 1001L;
  public static final LongPropertyKey PROPERTY_KEY = new LongPropertyKey("long.property", CORRECT_VALUE);


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
   * Default value is returned whether there is no value in property file for wanted property
   */
  @Test
  public void testUnsetValueOfPropertyKey() {
    Assert.assertEquals(PROPERTY_KEY.defaultValue, PROPERTY_KEY.getValue(emptyProperties));
  }

  /**
   * Default value is returned whether there is no value in property file for wanted property
   */
  @Test
  public void testEmptyValueOfPropertyKey() {
    Assert.assertEquals(PROPERTY_KEY.emptyValue(), PROPERTY_KEY.getValue(propertiesWithEmptyValues));
  }

  /**
   * Checks whether correct value is returned for wanted property
   */
  @Test
  public void testCorrectValueOfPropertyKey() {
    Assert.assertEquals(CORRECT_VALUE, PROPERTY_KEY.getValue(propertiesWithCorrectValues));
  }

  /**
   * Checks whether wrong value for wanted property is set. If so, IllegalArgumentException has to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWrongValueOfPropertyKey() {
    PROPERTY_KEY.getValue(propertiesWithWrongValues);
  }

  /**
   * Checks whether wrong value for wanted property is set. If so default value is returned.
   */
  @Test
  public void testWrongPropertyKeyWithDefaultValueOnParseError() {
    Assert.assertEquals(PROPERTY_KEY.defaultValue, PROPERTY_KEY.getValue(propertiesWithWrongValues, true));
  }

}
