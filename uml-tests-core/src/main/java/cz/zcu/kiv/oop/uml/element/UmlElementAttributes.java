package cz.zcu.kiv.oop.uml.element;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents additional attributes of UML element.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlElementAttributes implements Serializable, Cloneable {

  /** Serial version UID. */
  private static final long serialVersionUID = 9044430437822079733L;

  /** Additional attributes of UML element. */
  protected final Map<String, Object> attributes = new HashMap<String, Object>();

  /**
   * Returns array of attributes names.
   *
   * @return Array of attributes names.
   */
  public String[] getAttributeNames() {
    Set<String> names = attributes.keySet();

    return names.toArray(new String[names.size()]);
  }

  /**
   * Returns attribute of UML element.
   *
   * @param attributeName name of attribute.
   * @return Value of attribute of UML element if was set; <code>null</code> otherwise.
   */
  public Object getAttribute(String attributeName) {
    return attributes.get(attributeName);
  }

  /**
   * Returns attribute of UML element.
   *
   * @param attributeName name of attribute.
   * @param attributeType expected type of attribute.
   * @return Value of attribute of UML element if was set and has correct type; <code>null</code> otherwise.
   */
  @SuppressWarnings("unchecked")
  public <T> T getAttribute(String attributeName, Class<T> attributeType) {
    Object value = attributes.get(attributeName);
    if (value != null && attributeType.isAssignableFrom(value.getClass())) {
      return (T)value;
    }

    return null;
  }

  /**
   * Sets attribute of UML element.
   *
   * @param attributeName name of attribute.
   * @param attributeValue value of attribute.
   */
  public void setAttribute(String attributeName, Object attributeValue) {
    attributes.put(attributeName, attributeValue);
  }

  @Override
  protected UmlElementAttributes clone() {
    UmlElementAttributes clone = new UmlElementAttributes();
    clone.attributes.putAll(attributes);

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

    result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());

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

    if (!(obj instanceof UmlElementAttributes)) {
      return false;
    }

    UmlElementAttributes other = (UmlElementAttributes)obj;
    if (attributes == null) {
      if (other.attributes != null) {
        return false;
      }
    }
    else if (!attributes.equals(other.attributes)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of attributes of UML element.
   *
   * @return String value of attributes of UML element.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [attributes=" + attributes + "]";
  }

}
