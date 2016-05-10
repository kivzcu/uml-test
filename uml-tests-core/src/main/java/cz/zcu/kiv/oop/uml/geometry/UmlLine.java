package cz.zcu.kiv.oop.uml.geometry;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.zcu.kiv.oop.uml.geometry.lines.SimpleUmlLineIterator;
import cz.zcu.kiv.oop.uml.geometry.lines.UmlLineIterator;

/**
 * Simple implementation of line shape which represents direct line from starting to ending point.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlLine implements UmlLineShape, Serializable, Cloneable {

  /** Serial version UID. */
  private static final long serialVersionUID = 611401907261264845L;

  /** Starting point of this line. */
  protected UmlPoint startPoint;
  /** Ending point of this line. */
  protected UmlPoint endPoint;

  /**
   * Constructs line.
   *
   * @param x1 coordinate X of starting point.
   * @param y1 coordinate Y of starting point.
   * @param x2 coordinate X of ending point.
   * @param y2 coordinate Y of ending point.
   */
  public UmlLine(double x1, double y1, double x2, double y2) {
    startPoint = new UmlPoint(x1, y1);
    endPoint = new UmlPoint(x2, y2);
  }

  /**
   * Constructs line.
   *
   * @param startPoint starting point of line.
   * @param endPoint ending point of line.
   */
  public UmlLine(UmlPoint startPoint, UmlPoint endPoint) {
    this.startPoint = new UmlPoint();
    this.startPoint.setLocation(startPoint);

    this.endPoint = new UmlPoint();
    this.endPoint.setLocation(endPoint);
  }

  /**
   * Returns coordinate X of starting point.
   *
   * @return Coordinate X of starting point.
   */
  public double getStartPointX() {
    return startPoint.getX();
  }

  /**
   * Sets coordinate X of starting point.
   *
   * @param x coordinate x to set.
   */
  public void setStartPointX(double x) {
    startPoint.setX(x);
  }

  /**
   * Returns coordinate Y of starting point.
   *
   * @return Coordinate Y of starting point.
   */
  public double getStartPointY() {
    return startPoint.getY();
  }

  /**
   * Sets coordinate Y of starting point.
   *
   * @param y coordinate y to set.
   */
  public void setStartPointY(double y) {
    startPoint.setY(y);
  }

  /**
   * Returns coordinate X of ending point.
   *
   * @return Coordinate X of ending point.
   */
  public double getEndPointX() {
    return endPoint.getX();
  }

  /**
   * Sets coordinate X of ending point.
   *
   * @param x coordinate x to set.
   */
  public void setEndPointX(double x) {
    endPoint.setX(x);
  }

  /**
   * Returns coordinate Y of ending point.
   *
   * @return Coordinate Y of ending point.
   */
  public double getEndPointY() {
    return endPoint.getY();
  }

  /**
   * Sets coordinate Y of ending point.
   *
   * @param y coordinate y to set.
   */
  public void setEndPointY(double y) {
    endPoint.setY(y);
  }

  /**
   * Returns starting point of line (the point where the line starts).
   *
   * @return Starting point of line.
   */
  @Override
  public UmlPoint getStartPoint() {
    UmlPoint point = new UmlPoint();
    point.setLocation(startPoint);

    return point;
  }

  /**
   * Sets starting point of line (the point where the line starts).
   *
   * @param point starting point of line to set.
   */
  public void setStartPoint(UmlPoint point) {
    startPoint.setLocation(point);
  }

  /**
   * Returns ending point of line (the point where the line ends).
   *
   * @return Ending point of line.
   */
  @Override
  public UmlPoint getEndPoint() {
    UmlPoint point = new UmlPoint();
    point.setLocation(endPoint);

    return point;
  }

  /**
   * Sets ending point of line (the point where the line ends).
   *
   * @param point ending point of line to set.
   */
  public void setEndPoint(UmlPoint point) {
    endPoint.setLocation(point);
  }

  /**
   * Sets starting and ending point of line from entered coordinates.
   *
   * @param x1 coordinate X of starting point.
   * @param y1 coordinate Y of starting point.
   * @param x2 coordinate X of ending point.
   * @param y2 coordinate Y of ending point.
   */
  public void setLine(double x1, double y1, double x2, double y2) {
    startPoint = new UmlPoint(x1, y1);
    endPoint = new UmlPoint(x2, y2);
  }

  /**
   * Sets starting and ending point of line.
   *
   * @param startPoint starting point to set.
   * @param endPoint ending point to set.
   */
  public void setLine(UmlPoint startPoint, UmlPoint endPoint) {
    setStartPoint(startPoint);
    setEndPoint(endPoint);
  }

  /**
   * Returns bounding box of this line. The bounding box should contains whole shape.
   *
   * @return Rectangle representing bounds of this line.
   */
  @Override
  public UmlRectangle getBounds() {
    double x1 = getStartPointX(), y1 = getStartPointY(), x2 = getEndPointX(), y2 = getEndPointY();
    double x = 0, y = 0, w = 0, h = 0;

    if (x1 < x2) {
      x = x1;
      w = x2 - x1;
    }
    else {
      x = x2;
      w = x1 - x2;
    }
    if (y1 < y2) {
      y = y1;
      h = y2 - y1;
    }
    else {
      y = y2;
      h = y1 - y2;
    }

    return new UmlRectangle(x, y, w, h);
  }

  /**
   * Returns information whether this line contains point with entered coordinates.
   *
   * @param x the coordinate X of point.
   * @param y the coordinate Y of point.
   * @return <code>true</code> if this line contains entered point with entered coordinates; <code>false</code> otherwise.
   */
  @Override
  public boolean contains(double x, double y) {
    double x1 = startPoint.getX(), y1 = startPoint.getY(), x2 = endPoint.getX(), y2 = endPoint.getY();
    double r = (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);
    if (Math.abs(r) < 0.001) { // lies on line
      return getBounds().contains(x, y);
    }

    return false;
  }

  /**
   * Returns information whether this line contains entered point.
   *
   * @param point the point which will be tested whether this line contains it.
   * @return <code>true</code> if this line contains entered point; <code>false</code> otherwise.
   */
  @Override
  public boolean contains(UmlPoint point) {
    return contains(point.getX(), point.getY());
  }

  /**
   * Returns information whether rectangle with entered properties intersects this line.
   *
   * @param x the coordinate X of rectangle.
   * @param y the coordinate Y of rectangle.
   * @param w the width of rectangle.
   * @param h the height of rectangle.
   * @return <code>true</code> if rectangle with entered properties intersects the shape; <code>false</code> otherwise.
   */
  @Override
  public boolean intersects(double x, double y, double w, double h) {
    return new UmlRectangle(x, y, w, h).intersectsLine(this);
  }

  /**
   * Returns information whether entered rectangle intersects this line.
   *
   * @param rectangle the rectangle which will be tested whether intersects this line.
   * @return <code>true</code> if entered rectangle intersects the shape; <code>false</code> otherwise.
   */
  @Override
  public boolean intersects(UmlRectangle rectangle) {
    return rectangle.intersectsLine(this);
  }

  /**
   * Returns information whether entered line shape intersects this line.
   *
   * @param line the line shape which will be tested whether intersects this line.
   * @return <code>true</code> if entered line shape intersects the shape; <code>false</code> otherwise.
   */
  @Override
  public boolean intersectsLine(UmlLineShape line) {
    for (UmlLine umlLinePart : line.getLineIterator()) {
      if (intersection(this, umlLinePart) != null) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns intersection point of entered line with this line.
   *
   * @param line the line shape for which will be returned point of intersection.
   * @return Intersection points of this and entered line.
   */
  public UmlPoint intersection(UmlLine line) {
    return intersection(this, line);
  }

  /**
   * Returns intersection points of entered line shape with this line.
   *
   * @param line the line shape for which will be returned points of intersection.
   * @return Array of intersection points of this and entered line shape.
   */
  @Override
  public UmlPoint[] intersections(UmlLineShape line) {
    List<UmlPoint> intersections = new ArrayList<UmlPoint>();

    for (UmlLine umlLinePart : line.getLineIterator()) {
      UmlPoint intersection = intersection(this, umlLinePart);
      if (intersection != null) {
        intersections.add(intersection);
      }
    }

    return intersections.toArray(new UmlPoint[intersections.size()]);
  }

  /**
   * Returns iterator with only one line.
   *
   * @return Iterator with only one line.
   */
  @Override
  public UmlLineIterator getLineIterator() {
    return new SimpleUmlLineIterator(new UmlLine[] {this});
  }

  /**
   * Returns the AWT equivalent of this line. This method serves only for compatibility with AWT and for possibility draw this
   * shape.
   *
   * @return AWT equivalent ({@link Line2D}) of this line.
   */
  @Override
  public Line2D getShape() {
    return new Line2D.Double(startPoint.getPoint(), endPoint.getPoint());
  }

  /**
   * Clones line.
   *
   * @return Clone of this line.
   */
  @Override
  public UmlLine clone() {
    return new UmlLine(startPoint.clone(), endPoint.clone());
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

    result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
    result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());

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

    if (!(obj instanceof UmlLine)) {
      return false;
    }

    UmlLine other = (UmlLine)obj;
    if (startPoint == null) {
      if (other.startPoint != null) {
        return false;
      }
    }
    else if (!startPoint.equals(other.startPoint)) {
      return false;
    }

    if (endPoint == null) {
      if (other.endPoint != null) {
        return false;
      }
    }
    else if (!endPoint.equals(other.endPoint)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of line.
   *
   * @return String value of line.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [startPoint=" + startPoint + ", endPoint=" + endPoint + "]";
  }

  /**
   * Returns information whether entered lines intersect each other.
   *
   * @param line1 the line which will be tested whether intersects other line.
   * @param line2 the line which will be tested whether intersects other line.
   * @return <code>true</code> if entered lines intersect each other; <code>false</code> otherwise.
   */
  public static UmlPoint intersection(UmlLine line1, UmlLine line2) {
    UmlPoint intersection = null;

    double sx1 = line1.getEndPointX() - line1.getStartPointX();
    double sy1 = line1.getEndPointY() - line1.getStartPointY();
    double sx2 = line2.getEndPointX() - line2.getStartPointX();
    double sy2 = line2.getEndPointY() - line2.getStartPointY();

    double s = (-sy1 * (line1.getStartPointX() - line2.getStartPointX()) + sx1 * (line1.getStartPointY() - line2.getStartPointY())) / (-sx2 * sy1 + sx1 * sy2);
    double t = (sx2 * (line1.getStartPointY() - line2.getStartPointY()) - sy2 * (line1.getStartPointX() - line2.getStartPointX())) / (-sx2 * sy1 + sx1 * sy2);

    if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
      // Collision detected
      intersection = new UmlPoint(line1.getStartPointX() + (t * sx1), line1.getStartPointY() + (t * sy1));
    }

    return intersection;
  }

}
