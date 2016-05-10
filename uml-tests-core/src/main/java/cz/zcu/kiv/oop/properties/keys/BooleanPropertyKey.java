package cz.zcu.kiv.oop.properties.keys;

import java.util.Properties;
import cz.zcu.kiv.oop.Strings;

/**
 * Represents boolean property which value can be obtained from default system or specific properties. This property has own name
 * (key) which leads into some properties file. Using {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained value for
 * specific properties.
 * <p>
 * If the properties doesn't contains this property, the {@link #defaultValue} is returned. If value of this property in properties
 * is empty, the {@link #emptyValue()} is returned. If during parsing value of property occurs some problem, the
 * {@link java.lang.IllegalArgumentException} is thrown. Method {@link #getValue(Properties, boolean)} brings opportunity to return
 * default value instead of thrown exception in case of parsing problem.
 * </p>
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class BooleanPropertyKey extends PropertyKey<Boolean> {

  /**
   * Constructs boolean property key.
   *
   * @param key name (key) of property.
   * @param defaultValue default value of property.
   */
  public BooleanPropertyKey(String key, Boolean defaultValue) {
    super(key, defaultValue);
  }

  /**
   * Parses string value stored in properties into boolean value of property.
   *
   * @param property string value of property.
   * @return Parsed boolean value of property.
   * @throws IllegalArgumentException If some error occurs during parsing the value.
   */
  @Override
  protected Boolean parse(String property) throws IllegalArgumentException {
    if (property.equalsIgnoreCase("true")) {
      return Boolean.TRUE;
    }

    if (property.equalsIgnoreCase("false")) {
      return Boolean.FALSE;
    }

    throw new IllegalArgumentException(Strings.getFormatted("exc.properties.parse-boolean", property));
  }

  /**
   * Represents sub-type of property key which contains list of boolean values. List can be obtained from default system or specific
   * properties. This list property has own name (key) which leads into some properties file. Using
   * {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained list of values for specific properties.
   * <p>
   * If the properties doesn't contains this property, the empty list is returned. If during parsing list of boolean values in
   * property occurs some problem, the {@link java.lang.IllegalArgumentException} is thrown.
   * </p>
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  public static class List extends PropertyKey.List<Boolean> {

    /**
     * Constructs list for obtaining boolean values from property.
     *
     * @param key name (key) of property.
     */
    public List(String key) {
      this(key, null);
    }

    /**
     * Constructs list for obtaining boolean values from property.
     *
     * @param key name (key) of property.
     * @param delimiter delimiter of boolean values in stored list in property.
     */
    public List(String key, String delimiter) {
      super(new BooleanPropertyKey(key, null), delimiter);
    }

  }

}
