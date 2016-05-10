package cz.zcu.kiv.oop.uml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlElement;
import cz.zcu.kiv.oop.uml.element.UmlNote;
import cz.zcu.kiv.oop.uml.element.UmlRelation;

/**
 * UML diagram which contains different types of elements. The diagram contains elements:
 * <ul>
 * <li>UML class</li>
 * <li>UML relation</li>
 * <li>UML note</li>
 * </ul>
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlDiagram implements Cloneable, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = 1389663699220939434L;

  /** List of all elements in UML diagram. */
  protected final List<UmlElement> elements = new ArrayList<UmlElement>();
  /** List of classes in UML diagram. */
  protected final List<UmlClass> classes = new ArrayList<UmlClass>();
  /** Map of classes in UML diagram which maps lists of classes for specific class types. */
  protected final Map<UmlClassType, List<UmlClass>> classesMap = new HashMap<UmlClassType, List<UmlClass>>();
  /** List of relations in UML diagram. */
  protected final List<UmlRelation> relations = new ArrayList<UmlRelation>();
  /** List of notes in UML diagram. */
  protected final List<UmlNote> notes = new ArrayList<UmlNote>();

  /**
   * Constructs UML diagram.
   *
   * @param elements list of UML elements from which will be constructed UML diagram.
   */
  public UmlDiagram(List<UmlElement> elements) {
    this.elements.addAll(elements);
    init();
  }

  /**
   * Initializes UML diagram.
   */
  protected void init() {
    for (UmlElement element : elements) {
      if (element == null) {
        continue;
      }

      switch (element.getElementType()) {
        case CLASS :
          UmlClass clazz = (UmlClass)element;
          classes.add(clazz);

          List<UmlClass> classes = classesMap.get(clazz.getClassType());
          if (classes == null) {
            classes = new ArrayList<UmlClass>();
            classesMap.put(clazz.getClassType(), classes);
          }
          classes.add(clazz);
          break;

        case RELATION :
          UmlRelation relation = (UmlRelation)element;
          relations.add(relation);
          break;

        case NOTE :
          UmlNote note = (UmlNote)element;
          notes.add(note);
          break;
      }
    }

    // Sorts list of classes
    if (classes.size() > 1) {
      Collections.sort(classes);
    }

    // Sorts list of relations
    if (relations.size() > 1) {
      Collections.sort(relations);
    }

    // Sorts list of notes
    if (notes.size() > 1) {
      Collections.sort(notes);
    }
  }

  /**
   * Returns list of all elements in UML diagram. The list can be changed because it is independent from list of all elements in
   * diagram.
   *
   * @return List of all elements in UML diagram.
   */
  public List<UmlElement> getElements() {
    return new ArrayList<UmlElement>(elements);
  }

  /**
   * Returns number of all elements in UML diagram.
   *
   * @return Number of all elements.
   */
  public int getElementsCount() {
    return elements.size();
  }

  /**
   * Returns list of classes in UML diagram. The list can be changed because it is independent from list of classes in diagram.
   *
   * @return List of classes in UML diagram.
   */
  public List<UmlClass> getClasses() {
    return new ArrayList<UmlClass>(classes);
  }

  /**
   * Returns number of classes in UML diagram.
   *
   * @return Number of classes.
   */
  public int getClassesCount() {
    return classes.size();
  }

  /**
   * Returns list of classes of specific type in UML diagram. The list can be changed because it is independent from list of classes
   * in diagram.
   *
   * @param classType type of UML class.
   * @return List of classes of specific type in UML diagram.
   */
  public List<UmlClass> getClasses(UmlClassType classType) {
    List<UmlClass> classes = classesMap.get(classType);
    if (classes == null) {
      return new ArrayList<UmlClass>();
    }

    return new ArrayList<UmlClass>(classes);
  }

  /**
   * Returns number of classes of specific type in UML diagram.
   *
   * @param classType type of UML class.
   * @return Number of classes of specific type.
   */
  public int getClassesCount(UmlClassType classType) {
    List<UmlClass> classes = classesMap.get(classType);

    return (classes == null ? 0 : classes.size());
  }

  /**
   * Returns list of relations in UML diagram. The list can be changed because it is independent from list of relations in diagram.
   *
   * @return List of relations in UML diagram.
   */
  public List<UmlRelation> getRelations() {
    return new ArrayList<UmlRelation>(relations);
  }

  /**
   * Returns number of relations in UML diagram.
   *
   * @return Number of relations.
   */
  public int getRelationsCount() {
    return relations.size();
  }

  /**
   * Returns list of notes in UML diagram. The list can be changed because it is independent from list of notes in diagram.
   *
   * @return List of notes in UML diagram.
   */
  public List<UmlNote> getNotes() {
    return new ArrayList<UmlNote>(notes);
  }

  /**
   * Returns number of notes in UML diagram.
   *
   * @return Number of notes.
   */
  public int getNotesCount() {
    return notes.size();
  }

  /**
   * Clones UML diagram.
   *
   * @return Clone of this UML diagram.
   */
  @Override
  public UmlDiagram clone() {
    return new UmlDiagram(elements);
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

    result = prime * result + ((elements == null) ? 0 : elements.hashCode());

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

    if (!(obj instanceof UmlDiagram)) {
      return false;
    }

    UmlDiagram other = (UmlDiagram)obj;
    if (!CollectionUtils.equalsIgnoreOrder(elements, other.elements)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of UML diagram.
   *
   * @return String value of UML diagram.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [elements=" + elements + "]";
  }

}
