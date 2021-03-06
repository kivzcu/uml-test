package cz.zcu.kiv.oop.properties.keys;

import cz.zcu.kiv.oop.Strings;

/**
 * Represents character property which value can be obtained from default system or specific properties. This property has own name
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
public class CharacterPropertyKey extends PropertyKey<Character> {

  /**
   * Constructs character property key.
   *
   * @param key name (key) of property.
   * @param defaultValue default value of property.
   */
  public CharacterPropertyKey(String key, Character defaultValue) {
    super(key, defaultValue);
  }

  /**
   * Returns information whether will be value of property trimmed before parsing. For character properties is returned always
   * <code>false</code>.
   *
   * @return <code>true</code> if value of property will be trimmed; <code>false</code> otherwise.
   */
  @Override
  protected boolean trimBeforeParsing() {
    return false;
  }

  /**
   * Parses string value stored in properties into character value of property.
   *
   * @param property string value of property.
   * @return Parsed character value of property.
   * @throws IllegalArgumentException If some error occurs during parsing the value.
   */
  @Override
  protected Character parse(String property) throws IllegalArgumentException {
    if (property.length() == 1) {
      return new Character(property.charAt(0));
    }

    throw new IllegalArgumentException(Strings.getFormatted("exc.properties.parse-char", property));
  }

  /**
   * Represents sub-type of property key which contains list of character values. List can be obtained from default system or
   * specific properties. This list property has own name (key) which leads into some properties file. Using
   * {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained list of values for specific properties.
   * <p>
   * If the properties doesn't contains this property, the empty list is returned. If during parsing list of character values in
   * property occurs some problem, the {@link java.lang.IllegalArgumentException} is thrown.
   * </p>
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  public static class List extends PropertyKey.List<Character> {

    /**
     * Constructs list for obtaining character values from property.
     *
     * @param key name (key) of property.
     */
    public List(String key) {
      this(key, null);
    }

    /**
     * Constructs list for obtaining character values from property.
     *
     * @param key name (key) of property.
     * @param delimiter delimiter of character values in stored list in property.
     */
    public List(String key, String delimiter) {
      super(new CharacterPropertyKey(key, null), delimiter);
    }

  }

}
