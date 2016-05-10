package cz.zcu.kiv.oop.properties.keys;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cz.zcu.kiv.oop.Strings;

/**
 * Represents enumeration property which value can be obtained from default system or specific properties. This property has own
 * name (key) which leads into some properties file. Using {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained
 * value for specific properties.
 * <p>
 * If the properties doesn't contains this property, the {@link #defaultValue} is returned. If value of this property in properties
 * is empty, the {@link #emptyValue()} is returned. If during parsing value of property occurs some problem, the
 * {@link java.lang.IllegalArgumentException} is thrown. Method {@link #getValue(java.util.Properties, boolean)} brings opportunity
 * to return default value instead of thrown exception in case of parsing problem.
 * </p>
 *
 * @author Mr.FrAnTA (Michal Dékány)
 * @param <E> Enumeration type which is stored in property.
 */
public class EnumPropertyKey<E extends Enum<E>> extends PropertyKey<E> {

  /** Class type of enumeration which is stored in property. */
  protected final Class<E> enumClass;

  /**
   * Constructs enumeration property key. This constructor serves for child classes because generic detection of enumeration class
   * is available.
   *
   * @param key name (key) of property.
   * @param defaultValue default value of property.
   */
  protected EnumPropertyKey(String key, E defaultValue) {
    super(key, defaultValue);

    this.enumClass = getEnumClass();
  }

  /**
   * Constructs enumeration property key.
   *
   * @param key name (key) of property.
   * @param enumClass class type of enumeration which is stored in property.
   * @param defaultValue default value of property.
   */
  public EnumPropertyKey(String key, Class<E> enumClass, E defaultValue) {
    super(key, defaultValue);

    this.enumClass = enumClass;
  }

  /**
   * Helper method which provides class type of enumeration which is stored in property. This method can be used only in property
   * keys whose extends this one and whose specifies enumeration type.
   *
   * @return Class type of enumeration which is stored in property.
   */
  @SuppressWarnings("unchecked")
  protected Class<E> getEnumClass() {
    Type type = getClass().getGenericSuperclass();
    if (type instanceof ParameterizedType) {
      for (Type typeArgument : ((ParameterizedType)type).getActualTypeArguments()) {
        try {
          return (Class<E>)typeArgument;
        }
        catch (ClassCastException exc) {
          // only for catching
        }
      }
    }

    return null;
  }

  /**
   * Parses string value stored in properties into enumeration value of property.
   *
   * @param property string value of property.
   * @return Parsed enumeration value of property.
   * @throws IllegalArgumentException If some error occurs during parsing the value.
   */
  @Override
  protected E parse(String property) {
    try {
      return Enum.valueOf(enumClass, property);
    }
    catch (IllegalArgumentException exc) {
      throw new IllegalArgumentException(Strings.getFormatted("exc.properties.parse-enum", enumClass.getName(), property), exc);
    }
  }

  /**
   * Represents sub-type of property key which contains list of enumeration values. List can be obtained from default system or
   * specific properties. This list property has own name (key) which leads into some properties file. Using
   * {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained list of values for specific properties.
   * <p>
   * If the properties doesn't contains this property, the empty list is returned. If during parsing list of enumeration values in
   * property occurs some problem, the {@link java.lang.IllegalArgumentException} is thrown.
   * </p>
   *
   * @author Mr.FrAnTA (Michal Dékány)
   * @param <E> Enumeration type which is stored in list property.
   */
  public static class List<E extends Enum<E>> extends PropertyKey.List<E> {

    /**
     * Constructs list for obtaining enumeration values from property.
     *
     * @param key name (key) of property.
     * @param enumClass class type of enumeration which is stored in property.
     */
    public List(String key, Class<E> enumClass) {
      this(key, enumClass, null);
    }

    /**
     * Constructs list for obtaining enumeration values from property.
     *
     * @param key name (key) of property.
     * @param enumClass class type of enumeration which is stored in property.
     * @param delimiter delimiter of enumeration values in stored list in property.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List(String key, Class<E> enumClass, String delimiter) {
      super(new EnumPropertyKey(key, enumClass, null), delimiter);
    }
  }

}
