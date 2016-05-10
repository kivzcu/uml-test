package cz.zcu.kiv.oop.properties.keys;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Test of boolean property key.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
@RunWith(Parameterized.class)
public class BooleanPropertyKeyTest extends PropertyKeyTestAncestor {

  protected static Properties emptyProperties;
  protected static Properties propertiesWithEmptyValues;
  protected static Properties propertiesWithCorrectValues;
  protected static Properties propertiesWithWrongValues;

  @Parameters
  public static Collection<Object[]> getParameters() {
    List<Object[]> parameters = new ArrayList<Object[]>();
    parameters.add(new Object[] {new BooleanPropertyKey("boolean.property.big", true), Boolean.TRUE});
    parameters.add(new Object[] {new BooleanPropertyKey("boolean.property.small", true), Boolean.TRUE});
    parameters.add(new Object[] {new BooleanPropertyKey("boolean.property.mixed", true), Boolean.TRUE});

    return parameters;
  }


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

  @Parameter(0)
  public PropertyKey<Boolean> propertyKey;

  @Parameter(1)
  public Boolean correctValue;

  /**
   * Default value is returned whether there is no value in property file for wanted property
   */
  @Test
  public void testUnsetValueOfPropertyKey() {
    Assert.assertEquals(propertyKey.getDefaultValue(), propertyKey.getValue(emptyProperties));
  }

  /**
   * Default value is returned whether there is no value in property file for wanted property
   */
  @Test
  public void testEmptyValueOfPropertyKey() {
    Assert.assertEquals(propertyKey.emptyValue(), propertyKey.getValue(propertiesWithEmptyValues));
  }

  /**
   * Checks whether correct value is returned for wanted property
   */
  @Test
  public void testCorrectValueOfPropertyKey() {
    Assert.assertEquals(correctValue, propertyKey.getValue(propertiesWithCorrectValues));
  }

  /**
   * Checks whether wrong value for wanted property is set. If so, IllegalArgumentException has to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWrongValueOfPropertyKey() {
    Assert.assertFalse(propertyKey.getValue(propertiesWithWrongValues));
  }

  /**
   * Checks whether wrong value for wanted property is set. If so default value is returned.
   */
  @Test
  public void testWrongPropertyKeyWithDefaultValueOnParseError() {
    Assert.assertEquals(propertyKey.getDefaultValue(), propertyKey.getValue(propertiesWithWrongValues, true));
  }

}
