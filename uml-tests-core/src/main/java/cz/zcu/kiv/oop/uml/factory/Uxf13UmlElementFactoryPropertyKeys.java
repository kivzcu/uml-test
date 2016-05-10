package cz.zcu.kiv.oop.uml.factory;

import cz.zcu.kiv.oop.properties.keys.StringPropertyKey;

/**
 * Property keys for factory of UML elements which is used by UXF reader version 13.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface Uxf13UmlElementFactoryPropertyKeys {

  /** Suffix of mapping properties for newer relation from UXF version 13. */
  String NEW_RELATION_TYPE_SUFFIX = "new.";

  /** Property key for name of type for newer relation from UXF version 13. */
  StringPropertyKey NEW_RELATION_TYPE = new StringPropertyKey("uxf.diagram.element.relation.type.new", "Relation");

}
