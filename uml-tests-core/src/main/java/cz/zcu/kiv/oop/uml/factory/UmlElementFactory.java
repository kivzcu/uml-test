package cz.zcu.kiv.oop.uml.factory;

import cz.zcu.kiv.oop.uml.element.UmlElement;
import cz.zcu.kiv.oop.uml.exception.UmlElementException;
import cz.zcu.kiv.oop.uxf.reader.sax.UmlElementDescriptor;

/**
 * Factory of UML elements. This factory should be used by UXF reader for creating new UML elements from read descriptors.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UmlElementFactory {

  /**
   * Returns factored UML element from entered descriptor.
   *
   * @param umlElementDescriptor descriptor of UML element which contains read data from UXF file.
   * @return Factored UML element.
   * @throws UmlElementException If some error occurs during element factoring.
   */
  public UmlElement getUmlElement(UmlElementDescriptor umlElementDescriptor) throws UmlElementException;

}
