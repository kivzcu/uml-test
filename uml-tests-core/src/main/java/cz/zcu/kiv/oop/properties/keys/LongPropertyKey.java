package cz.zcu.kiv.oop.properties.keys;

import cz.zcu.kiv.oop.Strings;

/**
 * Represents long property which value can be obtained from default system or specific properties. This property has own name (key)
 * which leads into some properties file. Using {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained value for
 * specific properties.
 * <p>
 * If the properties doesn't contains this property, the {@link #defaultValue} is returned. If value of this property in properties
 * is empty, the {@link #emptyValue()} is returned. If during parsing value of property occurs some problem, the
 * {@link java.lang.IllegalArgumentException} is thrown. Method {@link #getValue(java.util.Properties, boolean)} brings opportunity
 * to return default value instead of thrown exception in case of parsing problem.
 * </p>
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class LongPropertyKey extends PropertyKey<Long> {

  /**
   * Constructs long property key.
   *
   * @param key name (key) of property.
   * @param defaultValue default value of property.
   */
  public LongPropertyKey(String key, Long defaultValue) {
    super(key, defaultValue);
  }

  /**
   * Parses string value stored in properties into long value of property.
   *
   * @param property string value of property.
   * @return Parsed long value of property.
   * @throws IllegalArgumentException If some error occurs during parsing the value.
   */
  @Override
  protected Long parse(String property) throws IllegalArgumentException {
    try {
      return new Long(property);
    }
    catch (NumberFormatException exc) {
      throw new IllegalArgumentException(Strings.getFormatted("exc.properties.parse-long", property), exc);
    }
  }

  /**
   * Represents sub-type of property key which contains list of long values. List can be obtained from default system or specific
   * properties. This list property has own name (key) which leads into some properties file. Using
   * {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained list of values for specific properties.
   * <p>
   * If the properties doesn't contains this property, the empty list is returned. If during parsing list of long values in property
   * occurs some problem, the {@link java.lang.IllegalArgumentException} is thrown.
   * </p>
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  public static class List extends PropertyKey.List<Long> {

    /**
     * Constructs list for obtaining long values from property.
     *
     * @param key name (key) of property.
     */
    public List(String key) {
      this(key, null);
    }

    /**
     * Constructs list for obtaining long values from property.
     *
     * @param key name (key) of property.
     * @param delimiter delimiter of long values in stored list in property.
     */
    public List(String key, String delimiter) {
      super(new LongPropertyKey(key, null), delimiter);
    }

  }

}
