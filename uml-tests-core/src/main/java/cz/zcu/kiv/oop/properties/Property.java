package cz.zcu.kiv.oop.properties;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;

/**
 * This class represents one property in properties (file).
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class Property implements Cloneable, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = -4984638326358219943L;

  /** Name (key) of property. */
  public final String key;
  /** Value of property. */
  public final String value;

  /**
   * Constructs property.
   *
   * @param key name (key) of property.
   * @param value value of property.
   */
  public Property(String key, String value) {
    if (!StringUtils.hasText(key)) {
      throw new IllegalArgumentException(Strings.get("exc.properties.empty-key"));
    }

    this.key = key;
    this.value = value;
  }

  /**
   * Returns name (key) of property.
   *
   * @return Name (key) of property.
   */
  public String getKey() {
    return key;
  }

  /**
   * Returns value of property.
   *
   * @return Value of property.
   */
  public String getValue() {
    return value;
  }

  /**
   * Clones property.
   *
   * @return Clone of this property.
   */
  @Override
  public Property clone() {
    return new Property(key, value);
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

    result = prime * result + ((key == null) ? 0 : key.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());

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

    if (!(obj instanceof Property)) {
      return false;
    }

    Property other = (Property)obj;
    if (key == null) {
      if (other.key != null) {
        return false;
      }
    }
    else if (!key.equals(other.key)) {
      return false;
    }

    if (value == null) {
      if (other.value != null) {
        return false;
      }
    }
    else if (!value.equals(other.value)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of property.
   *
   * @return String value of property.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [key=" + key + ", value=" + value + "]";
  }

}
