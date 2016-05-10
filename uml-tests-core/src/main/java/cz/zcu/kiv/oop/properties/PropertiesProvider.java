package cz.zcu.kiv.oop.properties;

import java.util.List;

import cz.zcu.kiv.oop.properties.keys.PropertyKey;

/**
 * Provider of properties which primary serves for getting values of property keys. Implementation of this provider should contains
 * some properties whose will be used for obtaining values of requested property keys.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface PropertiesProvider {

  /**
   * Returns value of property from properties. If during parsing value of property occurs some problem, the
   * {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param propertyKey property key for which will be returned value.
   * @return Value of entered property key.
   */
  public <T> T getValue(PropertyKey<T> propertyKey);

  /**
   * Returns value of property from properties. If during parsing value of property occurs some problem and parameter
   * <code>defaultOnParseError</code> is <code>false</code>, the {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param propertyKey property key for which will be returned value.
   * @param defaultOnParseError information whether will be thrown exception in case of parsing error or default value of property.
   * @return Value of entered property key.
   */
  public <T> T getValue(PropertyKey<T> propertyKey, boolean defaultOnParseError);

  /**
   * Returns list of values of property from properties. If during parsing value of property occurs some problem, the
   * {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param listProperty property which contains list of values.
   * @return List of values of property.
   */
  public <T> List<T> getListValue(PropertyKey.List<T> listProperty);

  /**
   * Returns value of property with specified name (key). If properties doesn't contains property with entered name (key), the
   * <code>null</code> is returned.
   *
   * @param key name (key) of property.
   * @return Value of property with specified name (key) or <code>null</code>.
   */
  public String getProperty(String key);

  /**
   * Returns value of property with specified name (key). If properties doesn't contains property with entered name (key), the
   * default value is returned.
   *
   * @param key name (key) of property.
   * @param defaultValue default value which will be returned in case of non-existing property with specified name.
   * @return Value of property with specified name (key) or specified default value.
   */
  public String getProperty(String key, String defaultValue);

  /**
   * Returns list of all names (keys) of properties.
   *
   * @return List of all names (keys).
   */
  public List<String> getKeys();

  /**
   * Returns list of all properties.
   *
   * @return List of all properties.
   */
  public List<Property> getProperties();

}
