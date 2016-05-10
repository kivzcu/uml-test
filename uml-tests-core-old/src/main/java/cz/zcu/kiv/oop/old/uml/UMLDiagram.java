package cz.zcu.kiv.oop.old.uml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import cz.zcu.kiv.oop.old.UMLUtils;
import cz.zcu.kiv.oop.old.xml.IUMLReader;
import cz.zcu.kiv.oop.old.xml.UMLElement;
import cz.zcu.kiv.oop.old.xml.UMLElementType;
import cz.zcu.kiv.oop.old.xml.UMLReaderSax;

/**
 * Dotazy na UML diagramem
 *
 * @version 1.3 Added method for right validation.
 * @version 1.2 Added trim() method call in relations initialization.
 * @author Duong Manh Hung, A09B0205P
 * @author Stepan Cais, A12P0004P
 */
public class UMLDiagram {

  private final ArrayList<UMLElement> elements; // vsechny elementy z UML
  private final ArrayList<UMLElement> baseClasses; // pouze elementy typu trida
  private final ArrayList<UMLElement> baseRelations; // pouze elementy typu relace
  private final ArrayList<UMLClass> classes; // seznam trid v diagramu
  private final ArrayList<UMLRelation> relations; // seznam relaci v diagramu

  public UMLDiagram(String filename) {
    IUMLReader reader = null;

    try {
      reader = new UMLReaderSax(filename);
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    catch (SAXException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    elements = reader.getAllElements();

    baseClasses = new ArrayList<UMLElement>();
    classes = new ArrayList<UMLClass>();
    baseRelations = new ArrayList<UMLElement>();
    relations = new ArrayList<UMLRelation>();

    initClasses();
    initRelations();
  }

  /**
   * Vytvoreni seznamu trid z elementu
   */
  private void initClasses() {
    for (UMLElement ue : elements) {
      if (ue.getType().equals(UMLElementType.CLASS.getValue())) {
        if (!attributesValidationOK(ue.getPanelAttributes())) {
          continue;
        }

        baseClasses.add(ue);

        String[] typeAndName = UMLUtils.getTypeAndName(ue.getPanelAttributes());
        classes.add(new UMLClass(typeAndName[0], typeAndName[1]));
      }
    }
  }

  /**
   * Method checks if name of class / interface / enum etc. is in right format.
   *
   * @since 1.3
   * @param Attributes of class / interface / enum
   * @return True, if attributes are ok, otherwise false.
   */
  private boolean attributesValidationOK(String panelAttributes) {
    if (!panelAttributes.contains("\n") && panelAttributes.contains(">>")) {
      return false;
    }

    return true;
  }

  /**
   * Vytvoreni seznamu relaci z elementu
   */
  private void initRelations() {
    for (UMLElement ue : elements) {
      if (ue.getType().equals(UMLElementType.RELATION.getValue())) {
        baseRelations.add(ue);
      }
    }

    for (UMLElement relation : baseRelations) {
      UMLClass from = null, to = null;

      String[] attributes = relation.getAdtlAttributes().split(";");
      int beginPointX = Integer.parseInt(attributes[attributes.length - 2]);
      int beginPointY = Integer.parseInt(attributes[attributes.length - 1]);
      int endPointX = Integer.parseInt(attributes[0]);
      int endPointY = Integer.parseInt(attributes[1]);

      int beginningX = relation.getX() + beginPointX;
      int beginningY = relation.getY() + beginPointY;
      int endX = relation.getX() + endPointX;
      int endY = relation.getY() + endPointY;

      for (UMLElement classElement : baseClasses) {
        if ((beginningX >= classElement.getX() && beginningX <= (classElement.getX() + classElement.getW()))
            && (beginningY >= classElement.getY() && beginningY <= (classElement.getY() + classElement.getH()))) {
          String[] typeAndName = UMLUtils.getTypeAndName(classElement.getPanelAttributes());
          from = new UMLClass(typeAndName[0], typeAndName[1]);
          continue;
        }
        if ((endX >= classElement.getX() && endX <= (classElement.getX() + classElement.getW()))
            && (endY >= classElement.getY() && endY <= (classElement.getY() + classElement.getH()))) {
          String[] typeAndName = UMLUtils.getTypeAndName(classElement.getPanelAttributes());
          to = new UMLClass(typeAndName[0], typeAndName[1]);
        }
      }

      // if any of relations is not connected, ignore it TODO: any better
      // solution?
      if (from == null || to == null) {
        continue;
      }

      int typeEndIndex = relation.getPanelAttributes().indexOf("\n");

      if (typeEndIndex == -1) {
        typeEndIndex = relation.getPanelAttributes().length();
      }

      String arrowType;
      // Pokud je spojeni pouze cara bez sipky
      if (typeEndIndex == 0 && relation.getPanelAttributes().trim().length() == 1) {
        arrowType = "-";
      }
      // in case of empty line (it should happen if user uses a "line"
      // without
      // parameters)
      else if (typeEndIndex == 0 && relation.getPanelAttributes().trim().length() == 0) {
        arrowType = "-";
      }
      else {
        arrowType = relation.getPanelAttributes().trim().substring(3, typeEndIndex);
      }

      if (UMLUtils.isFromLeftToRight(arrowType)) {
        if (arrowType.equals("-") || arrowType.equals("<->")) {
          relations.add(new UMLRelation(from, to, UMLUtils.getRelationType(arrowType)));
        }
        else {
          relations.add(new UMLRelation(to, from, UMLUtils.getRelationType(arrowType)));
        }
      }
      else {
        if (arrowType.equals("-") || arrowType.equals("<->")) {
          relations.add(new UMLRelation(to, from, UMLUtils.getRelationType(arrowType)));
        }
        else {
          relations.add(new UMLRelation(from, to, UMLUtils.getRelationType(arrowType)));
        }
      }
    }
  }

  /**
   * @since 1.7 Returns all elements connected to element in parameter.
   * @param elementName Name of element, which connected elements will be returned.
   * @return Collection of connected elements to parameter element.
   */
  public Collection<String> getAllConnectedElements(String elementName) {
    Collection<String> connections = new HashSet<String>();
    for (UMLRelation ur : relations) {
      if (ur.getFrom().getName().equals(elementName)) {
        connections.add(ur.getTo().getName());
      }
      else if (ur.getTo().getName().equals(elementName)) {
        connections.add(ur.getFrom().getName());
      }
    }

    return connections;
  }

  /**
   * Existuje poznamka a je neprazdna?
   *
   * @return ano/ne
   */
  public boolean noteExists() {
    for (UMLElement ue : elements) {
      if (ue.getType().equals("com.umlet.element.Note")) {
        if (!ue.getPanelAttributes().isEmpty()) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Existuje trida?
   *
   * @param name nazev tridy
   * @return ano/ne
   */
  public boolean classExists(String name) {
    for (UMLClass uc : classes) {
      if (uc.getName().equals(name)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Existuje rozhrani s timto nazvem?
   *
   * @param name nazev rozhrani
   * @return ano/ne
   */
  public boolean interfaceExists(String name) {
    for (UMLClass uc : classes) {
      if (uc.getName().equals(name) && uc.getType().equals("<<interface>>")) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if exists enum with given name.
   *
   * @param name Name of enum.
   * @return True, if enum exists, otherwise false.
   */
  public boolean enumExists(String name) {
    for (UMLClass uc : classes) {
      if (uc.getName().equals(name) && uc.getType().equals("<<enum>>")) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns unexpected classes in UML. User sets as a parameter collection of classes, which are expected to be in UML. All other
   * classes in UML would be returned (are redundant).
   *
   * @param expectedClasses Collection of names of expected classes.
   * @return Collection of redundant classes.
   */
  public Collection<String> redundantClasses(Collection<String> expectedClasses) {
    Set<String> classSet = new HashSet<String>();
    for (UMLClass uc : classes) {
      if (uc.getType().equals("<<interface>>") || uc.getType().equals("<<enum>>")) {
        continue;
      }
      classSet.add(uc.getName());
    }

    classSet.removeAll(expectedClasses);

    return classSet;
  }

  /**
   * Returns unexpected interfaces in UML. User sets as a parameter collection of interfaces, which are expected to be in UML. All
   * other classes in UML would be returned (are redundant).
   *
   * @param expectedInterfaces Collection of names of expected interfaces.
   * @return Collection of redundant interfaces.
   */
  public Collection<String> redundantInterfaces(Collection<String> expectedInterfaces) {
    Set<String> interfaceSet = new HashSet<String>();
    for (UMLClass uc : classes) {
      if (uc.getType().equals("<<interface>>")) {
        interfaceSet.add(uc.getName());
      }
    }

    interfaceSet.removeAll(expectedInterfaces);

    return interfaceSet;
  }

  /**
   * Returns unexpected enums in UML. User sets as a parameter collection of enums, which are expected to be in UML. All other enums
   * in UML would be returned (are redundant).
   *
   * @param expectedEnums Collection of names of expected enums.
   * @return Collection of redundant enums.
   */
  public Set<String> redundantEnums(Collection<String> expectedEnums) {
    Set<String> enumSet = new HashSet<String>();
    for (UMLClass uc : classes) {
      if (uc.getType().equals("<<enum>>")) {
        enumSet.add(uc.getName());
      }
    }

    enumSet.removeAll(expectedEnums);

    return enumSet;
  }

  /**
   * Returns number of classes.
   *
   * @return Number of classes in UML.
   */
  public int getNumberOfClasses() {
    int counter = 0;
    for (UMLClass uc : classes) {
      if (uc.getType().equals("<<interface>>") || uc.getType().equals("<<enum>>")) {
        continue;
      }

      counter++;
    }

    return counter;
  }

  /**
   * Returns number of enums.
   *
   * @return Number of enums in UML.
   */
  public int getNumberOfEnums() {
    int counter = 0;
    for (UMLClass uc : classes) {
      if (uc.getType().equals("<<enum>>")) {
        counter++;
      }
    }

    return counter;
  }

  /**
   * Returns number of interfaces.
   *
   * @return Number of interfaces in UML.
   */
  public int getNumberOfInterfaces() {
    int counter = 0;
    for (UMLClass uc : classes) {
      if (uc.getType().equals("<<interface>>")) {
        counter++;
      }
    }

    return counter;
  }

  /**
   * Gets the number of relations between two uml objects, where the parameter is a name of an uml object, from where or where the
   * relation could point.
   *
   * @param name Name of an uml object, from where or where can point a relation.
   * @return Number of Relations leading to or from object with parameter�s name.
   */
  public int getNumberOfRelations(String name) {
    return getNumberOfRelationFrom(name) + getNumberOfRelationsTo(name);
  }

  /**
   * Gets the number of relations between two uml objects, where the parameter is a name of an uml object, from where the relation
   * could point.
   *
   * @param name Name of an uml object, from where can point a relation.
   * @return Number of Relations leading to object with parameter�s name.
   */
  public int getNumberOfRelationFrom(String name) {
    int counter = 0;
    for (UMLRelation ur : relations) {
      if (ur.getFrom().getName().equals(name)) {
        counter++;
      }
    }

    return counter;
  }

  /**
   * Gets the number of relations between two uml objects, where the parameter is a name of an uml object, where the relation could
   * point.
   *
   * @param name Name of an uml object, where can point a relation.
   * @return Number of Relations pointing to object with parameter�s name.
   */
  public int getNumberOfRelationsTo(String name) {
    int counter = 0;
    for (UMLRelation ur : relations) {
      if (ur.getTo().getName().equals(name)) {
        counter++;
      }
    }

    return counter;
  }

  /**
   * Existuje relaci z prvni tridy do druhe?
   *
   * @param from nazev tridy ze ktere vede relace
   * @param to nazev tridy do ktere vede relace
   * @return ano/ne
   */
  public boolean checkRelationFromTo(String from, String to) {
    for (UMLRelation relation : relations) {
      if (relation.getFrom().getName().equals(from) && relation.getTo().getName().equals(to)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if exist any relation between two given uml objects.
   *
   * @param umlObjectName1 Name of first object.
   * @param umlObjectName2 Name of second object.
   * @return True, if any relation exists, otherwise false.
   */
  public boolean existAnyRelationBetween(String umlObjectName1, String umlObjectName2) {
    for (UMLRelation relation : relations) {
      if (relation.getFrom().getName().equals(umlObjectName1) && relation.getTo().getName().equals(umlObjectName2)) {
        return true;
      }
      else if (relation.getFrom().getName().equals(umlObjectName2) && relation.getTo().getName().equals(umlObjectName1)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Existuje tento typ relace mezi prvni tridou a druhou?
   *
   * @param type typ relace
   * @param from nazev tridy ze ktere vede relace
   * @param to nazev tridy do ktere vede relace
   * @return ano/ne
   */
  public boolean checkRelationTypeFromTo(UMLRelationType type, String from, String to) {
    for (UMLRelation relation : relations) {
      if (relation.getType().equals(type) && relation.getFrom().getName().equals(from) && relation.getTo().getName().equals(to)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks type of relation between two uml objects. This method should be used only for test of relations without any direction
   * i.e. association without arrows, dashed line and two-arrowed dependency. For normal (with any direction) relations
   * {@code checkRelationTypeFromTo} method should be used.
   *
   * @param type Expected type of relation.
   * @param umlObjectName1 Name of first uml object from relation.
   * @param umlObjectName2 Name of second uml object from relation.
   * @return True, if relation type is the same as expected type of relation.
   */
  public boolean checkRelationType(UMLRelationType type, String umlObjectName1, String umlObjectName2) {
    for (UMLRelation relation : relations) {
      if (relation.getType().equals(type) && relation.getFrom().getName().equals(umlObjectName1) && relation.getTo().getName().equals(umlObjectName2)) {
        return true;
      }

      else if (relation.getType().equals(type) && relation.getFrom().getName().equals(umlObjectName2) && relation.getTo().getName().equals(umlObjectName1)) {
        return true;
      }
    }

    return false;
  }

  /**
   * @since 1.6 Return relation type between two uml object. If there is no relation or unknown relation is found,
   *        UMLRelationType.UNDEFINED is returned.
   * @param umlObjectName1 Name of the first object, who is connected with relation.
   * @param umlObjectName2 Name of the second object, who is connected with relation.
   * @return Relation type betweean two uml object.
   */
  public UMLRelationType getRelationTypeBetween(String umlObjectName1, String umlObjectName2) {
    for (UMLRelation relation : relations) {
      if (relation.getFrom().getName().equals(umlObjectName1) && relation.getTo().getName().equals(umlObjectName2)) {
        return relation.getType();
      }

      else if (relation.getFrom().getName().equals(umlObjectName2) && relation.getTo().getName().equals(umlObjectName1)) {
        return relation.getType();
      }
    }

    return UMLRelationType.UNDEFINED;
  }

  /**
   * Vraci obsah poznamky
   *
   * @return obsah poznamky; pokud poznamka neexistuje vraci null
   */
  public String getNoteContents() {
    for (UMLElement element : elements) {
      if (element.getType().equals(UMLElementType.NOTE.getValue())) {
        return element.getPanelAttributes();
      }
    }

    return null;
  }

  public void writeRelationsToStdOutput() {
    for (UMLRelation rel : relations) {
      System.out.println(rel);
    }
  }

}
