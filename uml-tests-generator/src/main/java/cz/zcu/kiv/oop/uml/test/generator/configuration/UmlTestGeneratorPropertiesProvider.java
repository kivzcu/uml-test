package cz.zcu.kiv.oop.uml.test.generator.configuration;

import cz.zcu.kiv.oop.exception.InitException;
import cz.zcu.kiv.oop.properties.AbstractPropertiesProvider;
import cz.zcu.kiv.oop.properties.PropertiesProvider;

public class UmlTestGeneratorPropertiesProvider extends AbstractPropertiesProvider implements PropertiesProvider {

  public UmlTestGeneratorPropertiesProvider(String propertiesFile) throws InitException {
    super(propertiesFile);
  }

  @Override
  protected void initProperties() throws InitException {
    loadPropertiesFromFile(propertiesFile);
  }

}
