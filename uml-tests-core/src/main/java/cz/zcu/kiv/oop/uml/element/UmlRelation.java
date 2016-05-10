package cz.zcu.kiv.oop.uml.element;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.exception.CloneFailedException;
import cz.zcu.kiv.oop.uml.geometry.UmlLine;
import cz.zcu.kiv.oop.uml.geometry.UmlLineShape;
import cz.zcu.kiv.oop.uml.geometry.UmlPolyline;

/**
 * Relation type of UML element which connects two UML classes. Relation can have some type and orientation type. Also the relation
 * is made by some line.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlRelation extends AbstractUmlElement implements UmlElement, Comparable<UmlRelation>, Cloneable {

  /** Serial version UID. */
  private static final long serialVersionUID = 2025026808863442053L;

  /** Type of UML relation. */
  protected UmlRelationType relationType;
  /** Orientation type of UML relation. */
  protected UmlRelationOrientationType relationOrientationType;
  /** Line shape of UML relation. */
  protected UmlLineShape relationLine;

  /** UML class from which leads this relation. */
  protected UmlClass from;
  /** UML class into which leads this relation. */
  protected UmlClass to;

  /**
   * Constructs UML relation.
   */
  public UmlRelation() {
    elementType = UmlElementType.RELATION;
    relationType = UmlRelationType.UNDEFINED;
    relationOrientationType = UmlRelationOrientationType.UNDEFINED;
  }

  /**
   * Returns type of UML relation.
   *
   * @return Type of UML relation.
   */
  public UmlRelationType getRelationType() {
    return relationType;
  }

  /**
   * Sets type of UML relation.
   *
   * @param relationType type of UML relation to set.
   */
  public void setRelationType(UmlRelationType relationType) {
    this.relationType = relationType;
  }

  /**
   * Returns orientation type of UML relation.
   *
   * @return Orientation type of UML relation.
   */
  public UmlRelationOrientationType getRelationOrientationType() {
    return relationOrientationType;
  }

  /**
   * Sets orientation type of UML relation.
   *
   * @param relationOrientationType orientation type of UML relation to set.
   */
  public void setRelationOrientationType(UmlRelationOrientationType relationOrientationType) {
    this.relationOrientationType = relationOrientationType;
  }

  /**
   * Returns line shape of UML relation.
   *
   * @return Line shape of UML relation.
   */
  public UmlLineShape getRelationLine() {
    return relationLine;
  }

  /**
   * Sets line shape of UML relation.
   *
   * @param relationLine line shape of UML relation to set.
   */
  public void setRelationLine(UmlLineShape relationLine) {
    this.relationLine = relationLine;
  }

  /**
   * Returns UML class from which leads this relation.
   *
   * @return UML class from which leads this relation.
   */
  public UmlClass getFrom() {
    return from;
  }

  /**
   * Sets UML class from which leads this relation.
   *
   * @param from UML class from which will this relation leads to set.
   */
  public void setFrom(UmlClass from) {
    this.from = from;
  }

  /**
   * Returns UML class into which leads this relation.
   *
   * @return UML class into which leads this relation.
   */
  public UmlClass getTo() {
    return to;
  }

  /**
   * Sets UML class into which leads this relation.
   *
   * @param to UML class into which will this relation leads to set.
   */
  public void setTo(UmlClass to) {
    this.to = to;
  }

  /**
   * Compares this object with the entered object for order. Returns a negative integer, zero, or a positive integer as this object
   * is less than, equal to, or greater than the entered object.
   *
   * @param other the other relation to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the entered
   *         object.
   */
  @Override
  public int compareTo(UmlRelation other) {
    if (other == null) {
      return 1;
    }

    UmlClass from = getFrom();
    if (from == null) {
      return -1;
    }

    int result = from.compareTo(other.getFrom());
    if (result == 0) {
      UmlClass to = getTo();
      if (to == null) {
        return -1;
      }

      result = to.compareTo(other.getTo());
      if (result == 0) {
        UmlRelationType type = getRelationType();
        if (type == null) {
          return -1;
        }

        UmlRelationType otherType = other.getRelationType();
        if (otherType == null) {
          return 1;
        }

        result = type.compareTo(otherType);
        result = (result > 0 ? 1 : (result < 0 ? -1 : 0));

        if (result == 0) {
          UmlRelationOrientationType orientationType = getRelationOrientationType();
          if (orientationType == null) {
            return -1;
          }

          UmlRelationOrientationType otherOrientationType = other.getRelationOrientationType();
          if (otherOrientationType == null) {
            return 1;
          }

          result = orientationType.compareTo(otherOrientationType);
          result = (result > 0 ? 1 : (result < 0 ? -1 : 0));
        }
      }
    }

    return result;
  }

  /**
   * Clones UML relation.
   *
   * @return Clone of this UML relation.
   */
  @Override
  public UmlRelation clone() {
    UmlRelation clone = new UmlRelation();
    clone.setRelationType(relationType);
    clone.setRelationOrientationType(relationOrientationType);

    if (relationLine instanceof UmlLine) {
      clone.setRelationLine(((UmlLine)relationLine).clone());
    }
    else if (relationLine instanceof UmlPolyline) {
      clone.setRelationLine(((UmlPolyline)relationLine).clone());
    }
    else if (relationLine != null) {
      throw new CloneFailedException(Strings.get("exc.uml.clone-not-supported"));
    }

    clone.setFrom(from == null ? null : from.clone());
    clone.setTo(to == null ? null : to.clone());

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

    result = prime * result + ((relationType == null) ? 0 : relationType.hashCode());
    result = prime * result + ((from == null) ? 0 : from.hashCode());
    result = prime * result + ((to == null) ? 0 : to.hashCode());

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

    if (!(obj instanceof UmlRelation)) {
      return false;
    }

    UmlRelation other = (UmlRelation)obj;
    if (relationType != other.relationType) {
      return false;
    }

    if (from == null) {
      if (other.from != null) {
        return false;
      }
    }
    else if (!from.equals(other.from)) {
      return false;
    }

    if (to == null) {
      if (other.to != null) {
        return false;
      }
    }
    else if (!to.equals(other.to)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of UML relation.
   *
   * @return String value of UML relation.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [relationType=" + relationType + ", relationOrientationType=" + relationOrientationType + ", from=" + from + ", to=" + to
        + "]";
  }

}
