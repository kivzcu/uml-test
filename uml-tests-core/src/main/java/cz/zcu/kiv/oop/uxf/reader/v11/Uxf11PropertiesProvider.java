package cz.zcu.kiv.oop.uxf.reader.v11;

import cz.zcu.kiv.oop.exception.InitException;
import cz.zcu.kiv.oop.properties.AbstractPropertiesProvider;
import cz.zcu.kiv.oop.properties.PropertiesProvider;

/**
 * Singleton implementation of properties provider for UXF reader version 13.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class Uxf11PropertiesProvider extends AbstractPropertiesProvider implements PropertiesProvider {

  /** Name of properties file. */
  protected static final String PROPERTY_FILE = "uxf_11_reader.properties";

  /** Singleton instance of provider. */
  protected static Uxf11PropertiesProvider instance;

  /**
   * Private constructor which makes from this class the singleton.
   */
  private Uxf11PropertiesProvider() {}

  /**
   * Initializes properties - reads properties from properties file {@value #PROPERTY_FILE} specified in constant
   * {@link #PROPERTY_FILE}.
   *
   * @throws InitException If some error occurs during properties initialization.
   */
  @Override
  protected void initProperties() throws InitException {
    loadPropertiesFromClasspath(PROPERTY_FILE);
  }

  /**
   * Returns singleton instance of this provider.
   *
   * @return Singleton instance of this provider.
   */
  public static Uxf11PropertiesProvider getInstance() {
    if (instance == null) {
      instance = new Uxf11PropertiesProvider();
    }

    return instance;
  }

}
