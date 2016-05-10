package cz.zcu.kiv.oop.uml.element;

/**
 * Determines type of UML element.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public enum UmlElementType {

  /** The class type of UML element. Class type can have different stereotypes determined by {@link UmlClassType}. */
  CLASS,
  /** The relation type of UML element. */
  RELATION,
  /** The note type of UML element. */
  NOTE,
  ;

}
