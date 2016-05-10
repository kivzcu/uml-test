package cz.zcu.kiv.oop.uml.geometry;

import java.awt.Shape;

/**
 * This interface contains common methods for all shapes used in UML diagrams. Each shape of UML diagram should implements this
 * interface.
 * <p>
 * The AWT interface Shape and its implementations was not used because of its complexity and also because missing implementation of
 * polyline.
 * </p>
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UmlShape {

  /**
   * Returns bounding box of this shape. The bounding box should contains whole shape.
   *
   * @return Rectangle representing bounds of this shape.
   */
  public UmlRectangle getBounds();

  /**
   * Returns information whether this shape contains point with entered coordinates.
   *
   * @param x the coordinate X of point.
   * @param y the coordinate Y of point.
   * @return <code>true</code> if this shape contains entered point with entered coordinates; <code>false</code> otherwise.
   */
  public boolean contains(double x, double y);

  /**
   * Returns information whether this shape contains entered point.
   *
   * @param point the point which will be tested whether this shape contains it.
   * @return <code>true</code> if this shape contains entered point; <code>false</code> otherwise.
   */
  public boolean contains(UmlPoint point);

  /**
   * Returns information whether rectangle with entered properties intersects this shape.
   *
   * @param x the coordinate X of rectangle.
   * @param y the coordinate Y of rectangle.
   * @param w the width of rectangle.
   * @param h the height of rectangle.
   * @return <code>true</code> if rectangle with entered properties intersects the shape; <code>false</code> otherwise.
   */
  public boolean intersects(double x, double y, double w, double h);

  /**
   * Returns information whether entered rectangle intersects this shape.
   *
   * @param rectangle the rectangle which will be tested whether intersects this shape.
   * @return <code>true</code> if entered rectangle intersects the shape; <code>false</code> otherwise.
   */
  public boolean intersects(UmlRectangle rectangle);

  /**
   * Returns information whether entered line shape intersects this shape.
   *
   * @param line the line shape which will be tested whether intersects this shape.
   * @return <code>true</code> if entered line shape intersects the shape; <code>false</code> otherwise.
   */
  public boolean intersectsLine(UmlLineShape line);

  /**
   * Returns the AWT equivalent of this shape. This method serves only for compatibility with AWT and for possibility draw this
   * shape.
   *
   * @return AWT equivalent of this shape.
   */
  public Shape getShape();

}
