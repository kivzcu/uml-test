package cz.zcu.kiv.oop.uml.element;

import java.io.Serializable;

import cz.zcu.kiv.oop.uml.geometry.UmlRectangle;

/**
 * Abstract implementation of UML element which contains implementation of all commons method and which should be ancestor of all
 * types of UML elements.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public abstract class AbstractUmlElement implements UmlElement, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = -1218437325381317218L;

  /** Constant for locale for comparation of UML elements. */
  protected static final java.util.Locale LOCALE = new java.util.Locale("cs", "CZ");

  /** Type of UML element. */
  protected UmlElementType elementType;
  /** Coordinates of UML element. */
  protected UmlRectangle coordinates;
  /** Additional attributes of UML element. */
  protected UmlElementAttributes attributes;

  /**
   * Constructs UML element.
   */
  public AbstractUmlElement() {
    attributes = new UmlElementAttributes();
  }

  /**
   * Returns type of UML element.
   *
   * @return Type of UML element.
   */
  @Override
  public UmlElementType getElementType() {
    return elementType;
  }

  /**
   * Returns coordinates of UML element.
   *
   * @return Coordinates of UML element.
   */
  @Override
  public UmlRectangle getCoordinates() {
    return coordinates;
  }

  /**
   * Sets coordinates of UML element.
   *
   * @param coordinates coordinates to set.
   */
  @Override
  public void setCoordinates(UmlRectangle coordinates) {
    this.coordinates = coordinates;
  }

  /**
   * Returns additional attributes of UML element.
   *
   * @return Attributes of UML element.
   */
  @Override
  public UmlElementAttributes getAttributes() {
    return attributes;
  }

  /**
   * Sets additional attributes of UML element.
   *
   * @param attributes attributes of UML element to set.
   */
  public void setAttributes(UmlElementAttributes attributes) {
    this.attributes = attributes;
  }

}
