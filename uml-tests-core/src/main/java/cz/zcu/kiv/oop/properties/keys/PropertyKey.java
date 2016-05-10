package cz.zcu.kiv.oop.properties.keys;

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;

/**
 * Represents property which value can be obtained from default system or specific properties. This property has own name (key)
 * which leads into some properties file. Using {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained value for
 * specific properties.
 * <p>
 * If the properties doesn't contains this property, the {@link #defaultValue} is returned. If value of this property in properties
 * is empty, the {@link #emptyValue()} is returned. If during parsing value of property occurs some problem, the
 * {@link java.lang.IllegalArgumentException} is thrown. Method {@link #getValue(Properties, boolean)} brings opportunity to return
 * default value instead of thrown exception in case of parsing problem.
 * </p>
 *
 * @author Mr.FrAnTA (Michal Dékány)
 * @param <T> Type of property.
 */
public abstract class PropertyKey<T> {

  /** Name (key) of property. */
  public final String key;
  /** Default value of property. */
  public final T defaultValue;

  /**
   * Constructs property key.
   *
   * @param key name (key) of property.
   * @param defaultValue default value of property.
   */
  public PropertyKey(String key, T defaultValue) {
    if (!StringUtils.hasText(key)) {
      throw new IllegalArgumentException(Strings.get("exc.properties.empty-key"));
    }

    this.key = key;
    this.defaultValue = defaultValue;
  }

  /**
   * Returns name (key) of property.
   *
   * @return Name (key) of property.
   */
  public String getKey() {
    return key;
  }

  /**
   * Returns default value of property.
   *
   * @return Default value of property.
   */
  public T getDefaultValue() {
    return defaultValue;
  }

  /**
   * Returns information whether will be value of property trimmed before parsing.
   *
   * @return <code>true</code> if value of property will be trimmed; <code>false</code> otherwise.
   */
  protected boolean trimBeforeParsing() {
    return true;
  }

  /**
   * Value for empty content of property.
   *
   * @return Empty value of property.
   */
  protected T emptyValue() {
    return null;
  }

  /**
   * Returns value of property from default system properties. If during parsing value of property occurs some problem, the
   * {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @return Value of property.
   */
  public T getValue() {
    return getValue(System.getProperties(), false);
  }

  /**
   * Returns value of property from specified properties. If during parsing value of property occurs some problem, the
   * {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param properties properties from which will be returned value of property.
   * @return Value of property.
   */
  public T getValue(Properties properties) {
    return getValue(properties, false);
  }

  /**
   * Returns value of property from default system properties. If during parsing value of property occurs some problem and parameter
   * <code>defaultOnParseError</code> is <code>false</code>, the {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param defaultOnParseError information whether will be thrown exception in case of parsing error or default value of property.
   * @return Value of property.
   */
  public T getValue(boolean defaultOnParseError) {
    return getValue(System.getProperties(), defaultOnParseError);
  }

  /**
   * Returns value of property from specified properties. If during parsing value of property occurs some problem and parameter
   * <code>defaultOnParseError</code> is <code>false</code>, the {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param properties properties from which will be returned value of property.
   * @param defaultOnParseError information whether will be thrown exception in case of parsing error or default value of property.
   * @return Value of property.
   */
  public T getValue(Properties properties, boolean defaultOnParseError) {
    String property = properties.getProperty(key);
    if (property == null) {
      return defaultValue;
    }

    try {
      String value = trimBeforeParsing() ? property.trim() : property;
      if (value.length() == 0) {
        return emptyValue();
      }

      return parse(value);
    }
    catch (IllegalArgumentException exc) {
      if (defaultOnParseError) {
        return defaultValue;
      }
      else {
        throw new IllegalArgumentException(Strings.getFormatted("exc.properties.parse-key", key, property), exc);
      }
    }
  }

  /**
   * Parses string value stored in properties into specific value of property.
   *
   * @param property string value of property.
   * @return Parsed value of property.
   * @throws IllegalArgumentException If some error occurs during parsing the value.
   */
  protected abstract T parse(String property) throws IllegalArgumentException;

  /**
   * Returns string value of property key.
   *
   * @return String value of property key.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [key=" + key + ", defaultValue=" + defaultValue + "]";
  }

  /**
   * Represents sub-type of property key which contains list of values. List can be obtained from default system or specific
   * properties. This list property has own name (key) which leads into some properties file. Using
   * {@link cz.zcu.kiv.oop.properties.PropertiesProvider} can be obtained list of values for specific properties.
   * <p>
   * If the properties doesn't contains this property, the empty list is returned. If during parsing list of values in property
   * occurs some problem, the {@link java.lang.IllegalArgumentException} is thrown.
   * </p>
   *
   * @author Mr.FrAnTA (Michal Dékány)
   * @param <T> Type of property.
   */
  public static abstract class List<T> {

    /** Property key from which will be used for obtaining list of values. */
    protected final PropertyKey<T> propertyKey;
    /** Delimiter of values in stored list in property. */
    protected final String delimiter;

    /**
     * Constructs list for obtaining values from property key.
     *
     * @param propertyKey property key from which will be obtained list of values.
     */
    protected List(PropertyKey<T> propertyKey) {
      this(propertyKey, null);
    }

    /**
     * Constructs list for obtaining values from property key.
     *
     * @param propertyKey property key from which will be obtained list of values.
     * @param delimiter delimiter of values in stored list in property.
     */
    protected List(PropertyKey<T> propertyKey, String delimiter) {
      this.propertyKey = propertyKey;
      this.delimiter = (delimiter == null ? "," : delimiter);
    }

    /**
     * Returns list of values of property from default system properties. If during parsing value of property occurs some problem,
     * the {@link java.lang.IllegalArgumentException} is thrown.
     *
     * @return List of values of property.
     */
    public java.util.List<T> getListValue() {
      return getListValue(System.getProperties());
    }

    /**
     * Returns list of values of property from specified properties. If during parsing value of property occurs some problem, the
     * {@link java.lang.IllegalArgumentException} is thrown.
     *
     * @param properties properties from which will be returned value of property.
     * @return List of values of property.
     */
    public java.util.List<T> getListValue(Properties properties) {
      java.util.List<T> list = new ArrayList<T>();
      String property = properties.getProperty(propertyKey.getKey());
      if (property != null) {
        StringTokenizer st = new StringTokenizer(property, delimiter);
        while (st.hasMoreElements()) {
          list.add(propertyKey.parse(propertyKey.trimBeforeParsing() ? st.nextToken().trim() : st.nextToken()));
        }
      }

      return list;
    }
  }

}
