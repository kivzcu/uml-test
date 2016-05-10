package cz.zcu.kiv.oop.uml.element;

import cz.zcu.kiv.oop.uml.geometry.UmlRectangle;

/**
 * This interface contains common methods (properties) for all UML elements. Each element of UML diagram should implements this
 * interface.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UmlElement {

  /**
   * Returns type of UML element.
   *
   * @return Type of UML element.
   */
  public UmlElementType getElementType();

  /**
   * Returns coordinates of UML element.
   *
   * @return Coordinates of UML element.
   */
  public UmlRectangle getCoordinates();

  /**
   * Sets coordinates of UML element.
   *
   * @param coordinates coordinates to set.
   */
  public void setCoordinates(UmlRectangle coordinates);

  /**
   * Returns additional attributes of UML element.
   *
   * @return Attributes of UML element.
   */
  public UmlElementAttributes getAttributes();

}
