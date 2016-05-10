package cz.zcu.kiv.oop.properties;

/**
 * Empty properties provider which contains (and provides) no properties. Also all property keys will returns their default values.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class EmptyPropertiesProvider extends AbstractPropertiesProvider {

  /**
   * Constructs empty properties provider.
   */
  public EmptyPropertiesProvider() {}

  /**
   * This method do nothing, because this is empty properties provider and there is nothing to provide.
   */
  @Override
  protected void initProperties() {
    // this properties provider is empty and all property keys will return default values.
  }

}
