package cz.zcu.kiv.oop.uml.element;

import java.text.Collator;

/**
 * Class type of UML element which has some name and. Also UML class can have type which is identified by stereotype.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlClass extends AbstractUmlElement implements UmlElement, Comparable<UmlClass>, Cloneable {

  /** Serial version UID. */
  private static final long serialVersionUID = -406405624689313135L;

  /** Name of class. */
  protected String name;
  /** Type of class. */
  protected UmlClassType classType;

  /**
   * Constructs UML class.
   */
  public UmlClass() {
    elementType = UmlElementType.CLASS;
    classType = UmlClassType.CLASS;
  }

  /**
   * Constructs UML class.
   *
   * @param name name of class.
   * @param classType type of class.
   */
  public UmlClass(String name, UmlClassType classType) {
    elementType = UmlElementType.CLASS;

    this.name = name;
    this.classType = classType;
  }

  /**
   * Returns name of class.
   *
   * @return Name of class.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name of class.
   *
   * @param name class name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns type of class.
   *
   * @return Type of class.
   */
  public UmlClassType getClassType() {
    return classType;
  }

  /**
   * Sets type of class.
   *
   * @param classType class type to set.
   */
  public void setClassType(UmlClassType classType) {
    this.classType = classType;
  }

  /**
   * Compares this object with the entered object for order. Returns a negative integer, zero, or a positive integer as this object
   * is less than, equal to, or greater than the entered object.
   *
   * @param other the other UML class to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the entered
   *         object.
   */
  @Override
  public int compareTo(UmlClass other) {
    if (other == null) {
      return 1;
    }

    String otherName = other.getName();
    if (otherName == null) {
      return 1;
    }

    String name = getName();
    if (name == null) {
      return -1;
    }

    Collator collator = Collator.getInstance(LOCALE);

    return collator.compare(name, otherName);
  }

  /**
   * Clones UML class.
   *
   * @return Clone of this UML class.
   */
  @Override
  public UmlClass clone() {
    UmlClass clone = new UmlClass();
    clone.setName(name);
    clone.setClassType(classType);

    // common attributes
    clone.setCoordinates(coordinates == null ? null : coordinates.clone());
    clone.setAttributes(attributes == null ? null : attributes.clone());

    return clone;
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by
   * {@link java.util.HashMap}.
   *
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((classType == null) ? 0 : classType.hashCode());

    return result;
  }

  /**
   * Indicates whether some object is "equal to" this one.
   *
   * @param obj the reference object with which to compare.
   * @return <code>true</code> if this object is the same as the obj argument; <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof UmlClass)) {
      return false;
    }

    UmlClass other = (UmlClass)obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    }
    else if (!name.equals(other.name)) {
      return false;
    }

    if (classType != other.classType) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of UML class.
   *
   * @return String value of UML class.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [name=" + name + ", classType=" + classType + "]";
  }

}
