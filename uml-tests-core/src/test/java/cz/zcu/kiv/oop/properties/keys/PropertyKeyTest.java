package cz.zcu.kiv.oop.properties.keys;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Test of general property key.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class PropertyKeyTest {

  public static final String KEY = "key";
  public static final String DEFAULT_VALUE = "value";
  public static final String SPECIFIC_VALUE = "someValue";
  public static final String ILLEGAL_VALUE = "null";

  protected Properties properties;

  @Before
  public void setUp() {
    properties = System.getProperties();
    if (properties == null) {
      throw new RuntimeException("Chybějící systémové properties");
    }
  }

  @After
  public void tearDown() {
    if (properties != null && properties.containsKey(KEY)) {
      properties.remove(KEY);
    }
  }

  /**
   * Checks if IllegalArgumentException is thrown when null is used as a key.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullAsKey() {
    new MockPropertyKey(null, DEFAULT_VALUE);
  }

  /**
   * Checks if IllegalArgumentException is thrown when empty string is used as a key.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyKey() {
    new MockPropertyKey("", DEFAULT_VALUE);
  }

  /**
   * Checks if IllegalArgumentException is thrown when white-character string is used as a key.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSpaceForKey() {
    new MockPropertyKey("     ", DEFAULT_VALUE);
  }

  /**
   * Checks if property key is initialized correctly (if saved key and default value are the same as those in constructor).
   */
  @Test
  public void testInitialization() {
    PropertyKey<Object> propertyKey = new MockPropertyKey(KEY, DEFAULT_VALUE);
    Assert.assertEquals(KEY, propertyKey.key);
    Assert.assertEquals(KEY, propertyKey.getKey());

    Assert.assertEquals(DEFAULT_VALUE, propertyKey.defaultValue);
    Assert.assertEquals(DEFAULT_VALUE, propertyKey.getDefaultValue());
  }

  /**
   * Checks if correct default value is returned
   */
  @Test
  public void testDefaultValueOnMissingProperty() {
    PropertyKey<Object> propertyKey = new MockPropertyKey(KEY, DEFAULT_VALUE);
    Assert.assertEquals(DEFAULT_VALUE, propertyKey.getValue());
  }

  /**
   * Checks if correct value is returned if it is set for the key.
   */
  @Test
  public void testGetValue() {
    PropertyKey<Object> propertyKey = new MockPropertyKey(KEY, DEFAULT_VALUE);
    properties.setProperty(KEY, SPECIFIC_VALUE);
    Assert.assertEquals("someValue", propertyKey.getValue());
  }

  /**
   * Checks if correct value is returned if it is set for the key.
   */
  @Test
  public void testValueFromSpecificProperties() {
    PropertyKey<Object> propertyKey = new MockPropertyKey(KEY, DEFAULT_VALUE);
    properties.setProperty(KEY, SPECIFIC_VALUE);
    Assert.assertEquals("someValue", propertyKey.getValue(properties));
  }

  /**
   * Checks if propertyKey return null if the empty string haas been set as it's value
   */
  @Test
  public void testEmptyPropertyValue() {
    PropertyKey<Object> propertyKey = new MockPropertyKey(KEY, DEFAULT_VALUE);
    properties.setProperty(KEY, "");
    Assert.assertEquals(null, propertyKey.getValue());
  }

  /**
   * checks if value is trim before it is saved as an value of property key.
   */
  @Test
  public void testTrimBeforeParsing() {
    PropertyKey<Object> propertyKey = new MockPropertyKey(KEY, DEFAULT_VALUE);
    properties.setProperty(KEY, " " + SPECIFIC_VALUE + " ");
    Assert.assertEquals(SPECIFIC_VALUE, propertyKey.getValue());
  }

  /**
   * Checks if IllegalArgumentException is thrown when wrong value is set in property key.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testParseError() {
    PropertyKey<Object> propertyKey = new MockPropertyKey(KEY, DEFAULT_VALUE);
    properties.setProperty(KEY, ILLEGAL_VALUE);
    propertyKey.getValue();
  }

  /**
   * Checks if default value is returned when wrong value is set in property key.
   */
  @Test
  public void testDefaultValueOnParseError() {
    PropertyKey<Object> propertyKey = new MockPropertyKey(KEY, DEFAULT_VALUE);
    properties.setProperty(KEY, ILLEGAL_VALUE);
    Assert.assertEquals(DEFAULT_VALUE, propertyKey.getValue(true));
  }

  /**
   * Checks if empty List is returned if there is no value in property key
   */
  @Test
  public void testPropertyKeyListWithNoValue() {
    PropertyKey.List<Object> listProperty = new MockPropertyKey.List(KEY);
    properties.setProperty(KEY, "");

    List<Object> list = new ArrayList<Object>();
    Assert.assertEquals(list, listProperty.getListValue());
  }

  /**
   * Checks if List with correct value is returned from propertyKey
   */
  @Test
  public void testPropertyKeyListWithOneValue() {
    PropertyKey.List<Object> listProperty = new MockPropertyKey.List(KEY);
    properties.setProperty(KEY, SPECIFIC_VALUE);

    List<Object> list = new ArrayList<Object>();
    list.add(SPECIFIC_VALUE);
    Assert.assertEquals(list, listProperty.getListValue());
  }

  /**
   * Checks if List with correct values is returned from propertyKey
   */
  @Test
  public void testPropertyKeyListWithTwoValues() {
    PropertyKey.List<Object> listProperty = new MockPropertyKey.List(KEY);
    properties.setProperty(KEY, SPECIFIC_VALUE + "," + SPECIFIC_VALUE);

    List<Object> list = new ArrayList<Object>();
    list.add(SPECIFIC_VALUE);
    list.add(SPECIFIC_VALUE);
    Assert.assertEquals(list, listProperty.getListValue());
  }

  /**
   * Checks if List with correct value is returned from propertyKey
   */
  @Test
  public void testPropertyKeyListWithAsSpecific() {
    PropertyKey.List<Object> listProperty = new MockPropertyKey.List(KEY);
    properties.setProperty(KEY, SPECIFIC_VALUE + "," + SPECIFIC_VALUE);

    List<Object> list = new ArrayList<Object>();
    list.add(SPECIFIC_VALUE);
    list.add(SPECIFIC_VALUE);
    Assert.assertEquals(list, listProperty.getListValue(properties));
  }

  /**
   * Mocked property key which has specific behavior for test.
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  protected static class MockPropertyKey extends PropertyKey<Object> {

    public MockPropertyKey(String key, Object defaultValue) {
      super(key, defaultValue);
    }

    @Override
    protected Object parse(String property) throws IllegalArgumentException {
      if (ILLEGAL_VALUE.equals(property)) {
        throw new IllegalArgumentException("Value cannot be " + ILLEGAL_VALUE);
      }

      return property;
    }

    public static class List extends PropertyKey.List<Object> {

      public List(String key) {
        this(key, null);
      }

      public List(String key, String delim) {
        super(new MockPropertyKey(key, null), delim);
      }

    }

  }

}
