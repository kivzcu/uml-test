package cz.zcu.kiv.oop.uml.test.generator;

import cz.zcu.kiv.oop.properties.PropertiesProvider;
import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.test.generator.exception.UmlTestGeneratorException;

public interface UmlTestGenerator {

  public void setPropertiesProvider(PropertiesProvider propertiesProvider);

  public void generateTest(UmlDiagram diagram, UmlTestClassDescriptor testClassDescriptor, String outputDirectory) throws UmlTestGeneratorException;

}
