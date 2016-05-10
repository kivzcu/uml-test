package cz.zcu.kiv.oop.uml.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.element.UmlClass;
import cz.zcu.kiv.oop.uml.element.UmlElement;
import cz.zcu.kiv.oop.uml.element.UmlNote;
import cz.zcu.kiv.oop.uml.element.UmlRelation;
import cz.zcu.kiv.oop.uml.geometry.UmlLine;
import cz.zcu.kiv.oop.uml.geometry.UmlLineShape;
import cz.zcu.kiv.oop.uml.geometry.UmlPoint;
import cz.zcu.kiv.oop.uml.geometry.UmlRectangle;
import cz.zcu.kiv.oop.uml.geometry.lines.UmlLineIterator;
import cz.zcu.kiv.oop.util.ImmutablePair;
import cz.zcu.kiv.oop.util.Pair;

/**
 * This static class contains helper methods for getting geometry information about UML diagram.
 *
 * @author Lukáš Witz
 * @author Karel Zíbar
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlGeometryUtils {

  /**
   * Private constructor which makes this class "static".
   */
  private UmlGeometryUtils() {}

  /**
   * Checks whatever two UML elements intersect.
   *
   * @param element1 first UML element.
   * @param element2 second UML element.
   * @return <code>true</code> if given UML elements intersect; <code>false</code> otherwise.
   */
  public static boolean intersects(UmlElement element1, UmlElement element2) {
    UmlRectangle coords1 = element1.getCoordinates();
    UmlRectangle coords2 = element2.getCoordinates();

    return coords1.intersects(coords2);
  }

  /**
   * Checks whether some note overlapping another note or class.
   *
   * @param diagram UML diagram whose notes and classes will be tested.
   * @return <code>true</code> if some note overlapping another note or class; <code>false</code> otherwise.
   */
  public static boolean containsOverlappingNotes(UmlDiagram diagram) {
    List<UmlElement> testedElements = new ArrayList<UmlElement>(diagram.getNotes());
    testedElements.addAll(diagram.getClasses());

    for (UmlNote note : diagram.getNotes()) {
      for (UmlElement element : testedElements) {
        if (note != element && intersects(note, element)) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Checks whatever diagram contains some overlapped classes.
   *
   * @param diagram UML diagram whose classes will be tested.
   * @return <code>true</code> if diagram contains some overlapped classes; <code>false</code> otherwise.
   */
  public static boolean containsOverlappedClasses(UmlDiagram diagram) {
    return getOverlappedClasses(diagram).length > 0;
  }

  /**
   * Returns classes whose overlap (or are overlapped by) another class(es).
   *
   * @param diagram UML diagram whose classes will be tested.
   * @return Array of classes whose overlap (or are overlapped by) another class(es).
   */
  public static UmlClass[] getOverlappedClasses(UmlDiagram diagram) {
    List<UmlClass> classes = diagram.getClasses();
    Set<UmlClass> overlapped = new HashSet<UmlClass>();
    for (int i = 0; i < classes.size() - 1; i++) {
      UmlClass class1 = classes.get(i);
      for (int j = i + 1; j < classes.size(); j++) {
        UmlClass class2 = classes.get(j);
        if (intersects(class1, class2)) {
          overlapped.add(class1);
          overlapped.add(class2);
        }
      }
    }

    return overlapped.toArray(new UmlClass[overlapped.size()]);
  }

  /**
   * Returns pairs of classes whose overlaps each other.
   *
   * @param diagram UML diagram whose classes will be tested.
   * @return Array of pairs of classes whose overlaps each other.
   */
  @SuppressWarnings("unchecked")
  public static Pair<UmlClass, UmlClass>[] getPairsOfOverlappedClasses(UmlDiagram diagram) {
    List<UmlClass> classes = diagram.getClasses();
    List<Pair<UmlClass, UmlClass>> overlapped = new ArrayList<Pair<UmlClass, UmlClass>>();
    for (int i = 0; i < classes.size() - 1; i++) {
      UmlClass clazz1 = classes.get(i);
      for (int j = i + 1; j < classes.size(); j++) {
        UmlClass clazz2 = classes.get(j);
        if (intersects(clazz1, clazz2)) {
          overlapped.add(new ImmutablePair<UmlClass, UmlClass>(clazz1, clazz2));
        }
      }
    }

    return overlapped.toArray(new Pair[overlapped.size()]);
  }

  /**
   * Checks whatever diagram contains some relations whose crosses each other.
   *
   * @param diagram UML diagram whose classes will be tested.
   * @return <code>true</code> if diagram contains some relations whose crosses each other; <code>false</code> otherwise.
   */
  public static boolean containsCrossedRelations(UmlDiagram diagram) {
    return getCrossedRelations(diagram).length > 0;
  }

  /**
   * Returns information whether two lines of relations intersects each other. If two lines intersects at starting or ending point,
   * it doesn't counts as intersection.
   *
   * @param relation1 first relation to test.
   * @param relation2 second relation to test.
   * @return <code>true</code> if two lines of relations intersects each other; <code>false</code> otherwise.
   */
  public static boolean intersects(UmlRelation relation1, UmlRelation relation2) {
    UmlLineShape line1 = relation1.getRelationLine();
    UmlLineShape line2 = relation2.getRelationLine();
    UmlPoint[] intersections = line1.intersections(line2);
    if (intersections == null || intersections.length == 0) {
      return false;
    }

    List<UmlPoint> intersectionsPoints = new ArrayList<UmlPoint>(Arrays.asList(intersections));
    UmlPoint point = line1.getStartPoint();
    if (intersectionsPoints.contains(point)) {
      if (point.equals(line2.getStartPoint()) || point.equals(line2.getEndPoint())) {
        intersectionsPoints.remove(point);
      }
    }

    point = line1.getEndPoint();
    if (intersectionsPoints.contains(point)) {
      if (point.equals(line2.getStartPoint()) || point.equals(line2.getEndPoint())) {
        intersectionsPoints.remove(point);
      }
    }

    return intersectionsPoints.size() > 0;
  }

  /**
   * Returns number of relations crossings.
   *
   * @param diagram UML diagram whose relations will be tested.
   * @return Number of relations crossings.
   */
  public static int getCountOfRelationsCrossings(UmlDiagram diagram) {
    List<UmlRelation> relations = diagram.getRelations();
    int counter = 0;
    for (int i = 0; i < relations.size() - 1; i++) {
      UmlRelation relation1 = relations.get(i);
      for (int j = i + 1; j < relations.size(); j++) {
        UmlRelation relation2 = relations.get(j);
        if (intersects(relation1, relation2)) {
          counter++;
        }
      }
    }

    return counter;
  }

  /**
   * Returns relations whose crosses each other.
   *
   * @param diagram UML diagram whose relations will be tested.
   * @return Array of relations whose crosses each other.
   */
  public static UmlRelation[] getCrossedRelations(UmlDiagram diagram) {
    List<UmlRelation> relations = diagram.getRelations();
    Set<UmlRelation> crossed = new HashSet<UmlRelation>();
    for (int i = 0; i < relations.size() - 1; i++) {
      UmlRelation relation1 = relations.get(i);
      for (int j = i + 1; j < relations.size(); j++) {
        UmlRelation relation2 = relations.get(j);
        if (intersects(relation1, relation2)) {
          crossed.add(relation1);
          crossed.add(relation2);
        }
      }
    }

    return crossed.toArray(new UmlRelation[crossed.size()]);
  }

  /**
   * Returns pairs of relations whose crosses each other.
   *
   * @param diagram UML diagram whose relations will be tested.
   * @return Array of pairs of relations whose crosses each other.
   */
  @SuppressWarnings("unchecked")
  public static Pair<UmlRelation, UmlRelation>[] getPairsOfCrossedRelations(UmlDiagram diagram) {
    List<UmlRelation> relations = diagram.getRelations();
    List<Pair<UmlRelation, UmlRelation>> crossed = new ArrayList<Pair<UmlRelation, UmlRelation>>();
    for (int i = 0; i < relations.size() - 1; i++) {
      UmlRelation relation1 = relations.get(i);
      for (int j = i + 1; j < relations.size(); j++) {
        UmlRelation relation2 = relations.get(j);
        if (intersects(relation1, relation2)) {
          crossed.add(new ImmutablePair<UmlRelation, UmlRelation>(relation1, relation2));
        }
      }
    }

    return crossed.toArray(new Pair[crossed.size()]);
  }

  /**
   * Checks whatever diagram contains relations whose looks like an extension of some edge of UML classes from/to which leads.
   *
   * @param diagram UML diagram whose relations will be tested.
   * @return <code>true</code> if diagram contains relations whose looks like an extension of some edge of UML classes from/to which
   *         leads; <code>false</code> otherwise.
   */
  public static boolean containsRelationsExtendingClassEdge(UmlDiagram diagram) {
    return getRelationsExtendingClassEdge(diagram).length > 0;
  }

  /**
   * Returns relations whose looks like an extension of some edge of UML classes from/to which leads.
   *
   * @param diagram UML diagram whose relations will be tested.
   * @return Array of relations whose looks like an extension of some edge of UML classes from/to which leads.
   */
  public static UmlRelation[] getRelationsExtendingClassEdge(UmlDiagram diagram) {
    List<UmlRelation> extendingRelations = new ArrayList<UmlRelation>();
    for (UmlRelation relation : diagram.getRelations()) {
      UmlLineIterator iterator = relation.getRelationLine().getLineIterator();
      if (!iterator.hasNext()) {
        continue;
      }

      UmlLine line = iterator.next();
      if (isLineExtendingClassEdge(line, relation.getFrom())) {
        extendingRelations.add(relation);
      }

      // iterate to last line
      while (iterator.hasNext()) {
        line = iterator.next();
      }

      if (isLineExtendingClassEdge(line, relation.getTo())) {
        extendingRelations.add(relation);
      }
    }

    return extendingRelations.toArray(new UmlRelation[extendingRelations.size()]);
  }

  /**
   * Checks whatever specified line extending at least one edge of specified class.
   *
   * @param line line which will be tested.
   * @param clazz class which edges will be tested.
   * @return <code>true</code> if specified line extending at least one edge of specified class; <code>false</code> otherwise.
   */
  protected static boolean isLineExtendingClassEdge(UmlLine line, UmlClass clazz) {
    if (clazz == null) {
      return false;
    }

    if (line.getStartPointX() == line.getEndPointX()) {
      UmlRectangle rect = clazz.getCoordinates();
      if (rect.getX() == line.getStartPointX() || rect.getMaxX() == line.getStartPointX()) {
        return true;
      }
    }
    else if (line.getStartPointY() == line.getEndPointY()) {
      UmlRectangle rect = clazz.getCoordinates();
      if (rect.getY() == line.getStartPointY() || rect.getMaxY() == line.getStartPointY()) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks whether diagram contains some note or class which is crossed by relation.
   *
   * @param diagram UML diagram whose elements will be tested.
   * @return <code>true</code> if diagram contains some note or class which is crossed by relation; <code>false</code> otherwise.
   */
  public static boolean containsRelationsCrossingElements(UmlDiagram diagram) {
    List<UmlRelation> relations = diagram.getRelations();

    for (UmlNote note : diagram.getNotes()) {
      if (isRelationCrossingElement(note, relations)) {
        return true;
      }
    }

    for (UmlClass clazz : diagram.getClasses()) {
      if (isRelationCrossingElement(clazz, relations)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks whether specified UML element is crossed by specified relation.
   *
   * @param element element which will be tested.
   * @param relations list of relations whose will be tested.
   * @return <code>true</code> if specified UML element is crossed by specified relation; <code>false</code> otherwise.
   */
  protected static boolean isRelationCrossingElement(UmlElement element, List<UmlRelation> relations) {
    UmlRectangle coords = element.getCoordinates().clone();
    // make the rectangle smaller because of start/end point of UML line.
    coords.setX(coords.getX() + 1);
    coords.setY(coords.getY() + 1);
    coords.setHeight(coords.getHeight() - 2);
    coords.setWidth(coords.getWidth() - 2);

    for (UmlRelation relation : relations) {
      if (coords.intersectsLine(relation.getRelationLine())) {
        return true;
      }
    }

    return false;
  }

}
