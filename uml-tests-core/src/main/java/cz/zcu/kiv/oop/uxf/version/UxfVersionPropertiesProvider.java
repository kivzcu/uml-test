package cz.zcu.kiv.oop.uxf.version;

import cz.zcu.kiv.oop.exception.InitException;
import cz.zcu.kiv.oop.properties.AbstractPropertiesProvider;
import cz.zcu.kiv.oop.properties.PropertiesProvider;

/**
 * Singleton implementation of properties provider for version reader of UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfVersionPropertiesProvider extends AbstractPropertiesProvider implements PropertiesProvider {

  /** Name of properties file. */
  protected static final String PROPERTY_FILE = "uxf_version_reader.properties";

  /** Singleton instance of provider. */
  protected static UxfVersionPropertiesProvider instance;

  /**
   * Private constructor which makes from this class the singleton.
   */
  private UxfVersionPropertiesProvider() {}

  /**
   * Initializes properties - reads properties from properties file {@value #PROPERTY_FILE} specified in constant
   * {@link #PROPERTY_FILE}.
   *
   * @throws InitException If some error occurs during properties initialization.
   */
  @Override
  protected void initProperties() {
    loadPropertiesFromClasspath(PROPERTY_FILE);
  }

  /**
   * Returns singleton instance of this provider.
   *
   * @return Singleton instance of this provider.
   */
  public static UxfVersionPropertiesProvider getInstance() {
    if (instance == null) {
      instance = new UxfVersionPropertiesProvider();
    }

    return instance;
  }

}
