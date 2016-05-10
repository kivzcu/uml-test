package cz.zcu.kiv.oop.uml.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.properties.PropertiesProvider;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.element.UmlRelationOrientationType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;
import cz.zcu.kiv.oop.uml.exception.UmlElementException;
import cz.zcu.kiv.oop.uml.geometry.UmlLineShape;
import cz.zcu.kiv.oop.uml.geometry.UmlPolyline;
import cz.zcu.kiv.oop.uxf.reader.sax.UmlElementDescriptor;

/**
 * Implementation of UML elements factory which is used by UXF reader version 13 for creating new UML elements from read
 * descriptors. The UXF version 13 has two different types of relations and their mapping to arrows of relation. This implementation
 * of factory solves the problem with different mapping.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class Uxf13UmlElementFactory extends DefaultUmlElementFactory {

  /** Information whether the actual initialized UML relation is newer relation from UXF version 13. */
  protected boolean newRelationType;

  /** Cache of properties for mapping of relation types for newer relation from UXF version 13. */
  protected Map<String, List<String>> newRelationTypePropertiesCache;
  /** Cache of properties for mapping of relation orientation types for newer relation from UXF version 13. */
  protected Map<String, List<String>> newRelationOrientationTypePropertiesCache;

  /**
   * Constructs factory.
   *
   * @param propertiesProvider provider which provides properties for factoring UML elements and their properties.
   */
  public Uxf13UmlElementFactory(PropertiesProvider propertiesProvider) {
    super(propertiesProvider);
  }

  /**
   * Initializes factory. This method is called at the end of factory construction.
   */
  @Override
  protected void init() {
    super.init();

    newRelationTypePropertiesCache = new HashMap<String, List<String>>();
    newRelationOrientationTypePropertiesCache = new HashMap<String, List<String>>();

    String newRelationTypeSuffix = Uxf13UmlElementFactoryPropertyKeys.NEW_RELATION_TYPE_SUFFIX;
    initCache(DefaultUmlElementFactoryPropertyKeys.RELATION_TYPE_PROPERTY_PREFIX + newRelationTypeSuffix, newRelationTypePropertiesCache);
    initCache(DefaultUmlElementFactoryPropertyKeys.RELATION_ORIENTATION_TYPE_PROPERTY_PREFIX + newRelationTypeSuffix, newRelationOrientationTypePropertiesCache);
  }

  /**
   * Initializes UML relation. This method sets type of relation and additional attributes from descriptor.
   *
   * @param relation UML relation which will be initialized.
   * @param umlElementDescriptor descriptor of UML element from which will be initialized the relation.
   * @throws UmlElementException If some error occurs during relation initialization.
   */
  @Override
  protected void initUmlRelation(UmlRelation relation, UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    String newType = propertiesProvider.getValue(Uxf13UmlElementFactoryPropertyKeys.NEW_RELATION_TYPE);
    newRelationType = umlElementDescriptor.getElementType().equals(newType);
    super.initUmlRelation(relation, umlElementDescriptor);
    newRelationType = false;
  }

  /**
   * Returns relation type from entered arrow.
   *
   * @param relationArrow arrow of relation.
   * @return Type of relation for entered arrow.
   */
  @Override
  protected UmlRelationType getUmlRelationType(String relationArrow) {
    if (newRelationType) {
      UmlRelationType type = getType(UmlRelationType.class, relationArrow, DefaultUmlElementFactoryPropertyKeys.RELATION_PROPERTY_SUFFIX,
          newRelationTypePropertiesCache);

      return (type == null) ? UmlRelationType.UNDEFINED : type;
    }

    return super.getUmlRelationType(relationArrow);
  }

  /**
   * Returns relation orientation type from entered arrow.
   *
   * @param relationArrow arrow of relation.
   * @return Type of relation orientation for entered arrow.
   */
  @Override
  protected UmlRelationOrientationType getUmlRelationOrientationType(String relationArrow) {
    if (newRelationType) {
      UmlRelationOrientationType orientationType = getType(UmlRelationOrientationType.class, relationArrow,
          DefaultUmlElementFactoryPropertyKeys.RELATION_PROPERTY_SUFFIX, newRelationOrientationTypePropertiesCache);

      return orientationType == null ? UmlRelationOrientationType.UNDEFINED : orientationType;
    }

    return super.getUmlRelationOrientationType(relationArrow);
  }

  /**
   * Returns factored line (shape) of UML relation.
   *
   * @param umlElementDescriptor descriptor of UML element from which will be factored the line (shape) of UML relation.
   * @return Factored line (shape).
   * @throws UmlElementException If some error occurs during line factoring.
   */
  @Override
  protected UmlLineShape getUmlRelationLine(UmlElementDescriptor umlElementDescriptor) throws UmlElementException {
    if(!newRelationType) {
      return super.getUmlRelationLine(umlElementDescriptor);
    }

    String panelAttributes = umlElementDescriptor.getAdditionalAttributes();
    if (StringUtils.hasText(panelAttributes)) {
      String separator = propertiesProvider.getValue(DefaultUmlElementFactoryPropertyKeys.RELATION_COORDINATES_SEPARATOR);
      String[] coordinates = panelAttributes.split(separator);

      List<Double> coordinatesValues = new ArrayList<Double>();
      for (int i = 0; i < coordinates.length; i++) {
        double coordinate = Double.valueOf(Double.valueOf(coordinates[i])) * umlElementDescriptor.getZoomFactor();
        coordinate += (i % 2 == 0 ? umlElementDescriptor.getX() : umlElementDescriptor.getY());
        coordinatesValues.add(coordinate);
      }

      return new UmlPolyline(coordinatesValues.toArray(new Double[coordinatesValues.size()]));
    }

    return null;
  }

}
