package cz.zcu.kiv.oop.properties;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import cz.zcu.kiv.oop.exception.InitException;
import cz.zcu.kiv.oop.properties.keys.PropertyKey;

/**
 * Test of abstract properties provider which primary serves for getting values of property keys.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class AbstractPropertiesProviderTest {

  public static final Property FOO_PROPERTY = new Property("foo", "foo2");
  public static final Property FOO2_PROPERTY = new Property("foo2", "foo2");

  public static final String[] KEYS = new String[] {"foo", "property", "list"};
  public static final Property[] PROPERTIES = new Property[] {new Property("foo", "bar"), new Property("property", "value"),
      new Property("list", "value1|value2")};

  public static final PropertyKey<String> PROPERTY_KEY = new MockPropertyKey("property", "foo");
  public static final PropertyKey<String> INVALID_PROPERTY_KEY = new MockPropertyKey("foo", "foo2");
  public static final PropertyKey.List<String> LIST_PROPERTY_KEY = new MockPropertyKey.List("list", "|");

  protected static AbstractPropertiesProvider propertiesProvider;

  /**
   * Method sets propertiesProvider to test
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    propertiesProvider = new MockPropertiesProvider("properties_provider.properties");
  }

  @AfterClass
  public static void tearDownAfterClass() {
    propertiesProvider = null;
  }

  /**
   * When the properties file for propertiesProvider doesn't exist, InitException has to be thrown.
   */
  @Test(expected = InitException.class)
  public void testNonexistingPropertiesFile() {
    new MockPropertiesProvider("non_existing_properties.properties");
  }

  /**
   * Test whether right property key of some value is returned
   */
  @Test
  public void testGetPropertyKeyValue() {
    Assert.assertEquals("value", propertiesProvider.getValue(PROPERTY_KEY));
  }

  /**
   * When property key is not present in property file, IllegalArgumentException has to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetInvalidPropertyKeyValue() {
    propertiesProvider.getValue(INVALID_PROPERTY_KEY);
  }

  /**
   * When property key is not present in property file, default value will be returned instead.
   */
  @Test
  public void testGetDefaultValueInsteadOfInvalidPropertyKeyValue() {
    Assert.assertEquals("foo2", propertiesProvider.getValue(INVALID_PROPERTY_KEY, true));
  }

  /**
   * Checks if correct list of values is returned according to property key
   */
  @Test
  public void testGetListPropertyKeyValue() {
    Assert.assertEquals(Arrays.asList(new String[] {"value1", "value2"}), propertiesProvider.getListValue(LIST_PROPERTY_KEY));
  }

  /**
   * Checks if correct property is returned.
   */
  @Test
  public void testGetProperty() {
    Assert.assertEquals("bar", propertiesProvider.getProperty(FOO_PROPERTY.key));
  }

  /**
   * Null has to be returned, whether property is not present in property file.
   */
  @Test
  public void testGetNonexistingProperty() {
    Assert.assertEquals(null, propertiesProvider.getProperty(FOO2_PROPERTY.key));
  }

  /**
   * When property is not present in property file, default value is returned.
   */
  @Test
  public void testGetNonexistingPropertyWithDefaultValue() {
    Assert.assertEquals(FOO2_PROPERTY.value, propertiesProvider.getProperty(FOO2_PROPERTY.key, FOO2_PROPERTY.value));
  }

  /**
   * Checks whether all and correct property keys are returned
   */
  @Test
  public void testGetPropertyKeys() {
    Assert.assertTrue(CollectionUtils.equalsIgnoreOrder(Arrays.asList(KEYS), propertiesProvider.getKeys()));
  }

  /**
   * Checks whether all and correct properties are returned
   */
  @Test
  public void testGetProperties() {
    Assert.assertTrue(CollectionUtils.equalsIgnoreOrder(Arrays.asList(PROPERTIES), propertiesProvider.getProperties()));
  }

  /**
   * Mocked implementation of properties provider.
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  protected static class MockPropertiesProvider extends AbstractPropertiesProvider {

    public MockPropertiesProvider(String properties) {
      super(properties);
    }

    @Override
    protected void initProperties() throws InitException {
      loadPropertiesFromClasspath(propertiesFile);
    }
  }

  /**
   * Mock of property key with specific functionality for tests.
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  protected static class MockPropertyKey extends PropertyKey<String> {

    public MockPropertyKey(String key, String defaultValue) {
      super(key, defaultValue);
    }

    @Override
    protected boolean trimBeforeParsing() {
      return false;
    }

    @Override
    protected String parse(String property) {
      if ("bar".equals(property)) {
        throw new IllegalArgumentException("Property nesmí být bar");
      }
      return property;
    }

    public static class List extends PropertyKey.List<String> {

      public List(String key) {
        this(key, null);
      }

      public List(String key, String delim) {
        super(new MockPropertyKey(key, null), delim);
      }
    }
  }
}
