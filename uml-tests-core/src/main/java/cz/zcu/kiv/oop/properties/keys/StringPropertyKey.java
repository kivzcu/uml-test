package cz.zcu.kiv.oop.properties.keys;

/**
 * Represents string property which value can be obtained from default system or specific properties. This property has own name
 * (key) which leads into some properties file. Using {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained value for
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
public class StringPropertyKey extends PropertyKey<String> {

  /**
   * Constructs string property key.
   *
   * @param key name (key) of property.
   * @param defaultValue default value of property.
   */
  public StringPropertyKey(String key, String defaultValue) {
    super(key, defaultValue);
  }

  /**
   * Returns information whether will be value of property trimmed before parsing. For string properties is returned always
   * <code>false</code>.
   *
   * @return <code>true</code> if value of property will be trimmed; <code>false</code> otherwise.
   */
  @Override
  protected boolean trimBeforeParsing() {
    return false;
  }

  /**
   * Value for empty content of property.
   *
   * @return Empty value of property.
   */
  @Override
  protected String emptyValue() {
    return "";
  }

  /**
   * Returns same value as given in parameter <code>property</code>.
   *
   * @param property string value of property.
   * @return String value of property.
   * @throws IllegalArgumentException If some error occurs during parsing the value.
   */
  @Override
  protected String parse(String property) throws IllegalArgumentException {
    return property;
  }

  /**
   * Represents sub-type of property key which contains list of string values. List can be obtained from default system or specific
   * properties. This list property has own name (key) which leads into some properties file. Using
   * {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained list of values for specific properties.
   * <p>
   * If the properties doesn't contains this property, the empty list is returned. If during parsing list of string values in
   * property occurs some problem, the {@link java.lang.IllegalArgumentException} is thrown.
   * </p>
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  public static class List extends PropertyKey.List<String> {

    /**
     * Constructs list for obtaining string values from property.
     *
     * @param key name (key) of property.
     */
    public List(String key) {
      this(key, null);
    }

    /**
     * Constructs list for obtaining string values from property.
     *
     * @param key name (key) of property.
     * @param delimiter delimiter of string values in stored list in property.
     */
    public List(String key, String delimiter) {
      super(new StringPropertyKey(key, null), delimiter);
    }

  }

}
