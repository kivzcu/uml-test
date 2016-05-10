package cz.zcu.kiv.oop.old.uml;

/**
 * Reprezentace UML relace
 *
 * @author Duong Manh Hung, A09B0205P
 */
public class UMLRelation {

  private final UMLClass from, to;

  private UMLRelationType type;

  public UMLRelation(UMLClass from, UMLClass to, UMLRelationType type) {
    this.from = from;
    this.to = to;
    this.type = type;
  }

  public UMLClass getFrom() {
    return this.from;
  }

  public UMLClass getTo() {
    return this.to;
  }

  /**
   * @return the type
   */
  public UMLRelationType getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(UMLRelationType type) {
    this.type = type;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((from == null) ? 0 : from.hashCode());
    result = prime * result + ((to == null) ? 0 : to.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    UMLRelation other = (UMLRelation)obj;
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

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    }
    else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return "UMLRelation [from=" + from + ", to=" + to + ", type=" + type + "]";
  }

}
