package cz.zcu.kiv.oop.uml.element;

/**
 * Determines type of UML relation.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public enum UmlRelationType {

  /** Association relation type. */
  ASSOCIATION,
  /** Directed association relation type. */
  DIRECTED_ASSOCIATION,
  /** Dependency relation type. */
  DEPENDENCY,
  /** Composition relation type. */
  COMPOSITION,
  /** Aggregation relation type. */
  AGGREGATION,
  /** Implementace rozhrani relation type. */
  IMPLEMENTATION,
  /** Dedicnost relation type. */
  INHERITANCE,
  /** Relation type made by dashed line. */
  DASHEDLINE,
  /** Undefined relation type. */
  UNDEFINED,
  ;

}
