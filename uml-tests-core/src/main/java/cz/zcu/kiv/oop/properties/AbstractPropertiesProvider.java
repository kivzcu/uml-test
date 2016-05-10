package cz.zcu.kiv.oop.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.exception.InitException;
import cz.zcu.kiv.oop.properties.keys.PropertyKey;

/**
 * Simple implementation of properties provider which primary serves for getting values of property keys.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public abstract class AbstractPropertiesProvider implements PropertiesProvider {

  /** Helper variable for properties file which will be loaded. */
  protected String propertiesFile;
  /** Properties which values will be provided by this provider. */
  protected final Properties properties = new SafeProperties();

  /**
   * Constructs and initializes provider of properties.
   *
   * @throws InitException If some error occurs during properties initialization.
   */
  public AbstractPropertiesProvider() throws InitException {
    this(null);
  }

  /**
   * Helper constructor which initializes provider of properties which should load properties from specified file.
   *
   * @throws InitException If some error occurs during properties initialization.
   */
  protected AbstractPropertiesProvider(String propertiesFile) throws InitException {
    this.propertiesFile = propertiesFile;
    initProperties();
  }

  /**
   * Initializes properties for provider.
   *
   * @throws InitException If some error occurs during properties initialization.
   */
  protected abstract void initProperties() throws InitException;

  /**
   * Loads properties file with specified path.
   *
   * @param file path to properties file which will be loaded.
   * @throws InitException If some error occurs during reading of properties file.
   */
  protected void loadPropertiesFromFile(String file) throws InitException {
    try {
      loadPropertiesFromInputStream(new FileInputStream(file), file);
    }
    catch (FileNotFoundException exc) {
        throw new InitException(Strings.getFormatted("exc.properties.file-not-found", file), exc);
    }
  }

  /**
   * Loads properties file with specified path from classpath.
   *
   * @param file path to properties file which will be loaded.
   * @throws InitException If some error occurs during reading of properties file.
   */
  protected void loadPropertiesFromClasspath(String file) throws InitException {
    try {
      URL url = getClass().getResource(file);
      if (url == null) {
        url = ClassLoader.getSystemResource(file);
      }

      if (url == null) {
          throw new InitException(Strings.getFormatted("exc.properties.file-not-found", file));
      }

      loadPropertiesFromInputStream(url.openStream(), file);
    }
    catch (IOException exc) {
      throw new InitException(Strings.getFormatted("exc.properties.file-err", file), exc);
    }
  }

  /**
   * Loads properties from specified input stream.
   *
   * @param inputStream input stream from which will be loaded.
   * @throws InitException If some error occurs during reading of properties.
   */
  protected void loadPropertiesFromInputStream(InputStream inputStream) throws InitException {
    loadPropertiesFromInputStream(inputStream, null);
  }

  /**
   * Loads properties from specified input stream.
   *
   * @param inputStream input stream from which will be loaded.
   * @param file path to properties file for which is opened the input stream.
   * @throws InitException If some error occurs during reading of properties.
   */
  protected void loadPropertiesFromInputStream(InputStream inputStream, String file) throws InitException {
    try {
      properties.load(inputStream);
    }
    catch (IOException exc) {
      if (file == null) {
        throw new InitException(Strings.get("exc.properties.stream"), exc);
      }
      else {
        throw new InitException(Strings.getFormatted("exc.properties.file-err", file), exc);
      }
    }
    finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        }
        catch (IOException exc) {
          // closing stream is not important for re-throwing of exception.
        }
      }
    }
  }

  /**
   * Returns value of property from properties. If during parsing value of property occurs some problem, the
   * {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param propertyKey property key for which will be returned value.
   * @return Value of entered property key.
   */
  @Override
  public <T> T getValue(PropertyKey<T> propertyKey) {
    return propertyKey.getValue(properties);
  }

  /**
   * Returns value of property from properties. If during parsing value of property occurs some problem and parameter
   * <code>defaultOnParseError</code> is <code>false</code>, the {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param propertyKey property key for which will be returned value.
   * @param defaultOnParseError information whether will be thrown exception in case of parsing error or default value of property.
   * @return Value of entered property key.
   */
  @Override
  public <T> T getValue(PropertyKey<T> propertyKey, boolean defaultOnParseError) {
    return propertyKey.getValue(properties, defaultOnParseError);
  }

  /**
   * Returns list of values of property from properties. If during parsing value of property occurs some problem, the
   * {@link java.lang.IllegalArgumentException} is thrown.
   *
   * @param listProperty property which contains list of values.
   * @return List of values of property.
   */
  @Override
  public <T> List<T> getListValue(PropertyKey.List<T> listProperty) {
    return listProperty.getListValue(properties);
  }

  /**
   * Returns value of property with specified name (key). If properties doesn't contains property with entered name (key), the
   * <code>null</code> is returned.
   *
   * @param key name (key) of property.
   * @return Value of property with specified name (key) or <code>null</code>.
   */
  @Override
  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  /**
   * Returns value of property with specified name (key). If properties doesn't contains property with entered name (key), the
   * default value is returned.
   *
   * @param key name (key) of property.
   * @param defaultValue default value which will be returned in case of non-existing property with specified name.
   * @return Value of property with specified name (key) or specified default value.
   */
  @Override
  public String getProperty(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }

  /**
   * Returns list of all names (keys) of properties.
   *
   * @return List of all names (keys).
   */
  @Override
  public List<String> getKeys() {
    List<String> keys = new ArrayList<String>();
    for (Object key : properties.keySet()) {
      keys.add((String)key);
    }

    return keys;
  }

  /**
   * Returns list of all properties.
   *
   * @return List of all properties.
   */
  @Override
  public List<Property> getProperties() {
    List<Property> properties = new ArrayList<Property>();
    for (Entry<Object, Object> entry : this.properties.entrySet()) {
      properties.add(new Property((String)entry.getKey(), (String)entry.getValue()));
    }

    return properties;
  }

  /**
   * Properties which controls whatever the name (key) of property which has to contains some text.
   *
   * @author Mr.FrAnTA (Michal Dékány)
   */
  protected static class SafeProperties extends Properties {

    /** Serial version UID. */
    private static final long serialVersionUID = -4172749531950708800L;

    /**
     * {@inheritDoc}
     * <p>
     * For safe properties the key has to contains text (also whitespaces are not valid).
     * </p>
     */
    @Override
    public synchronized Object put(Object key, Object value) {
      if (!StringUtils.hasText(key.toString())) {
        throw new IllegalArgumentException(Strings.get("exc.properties.empty-key"));
      }

      return super.put(key, value);
    }

  }

}
