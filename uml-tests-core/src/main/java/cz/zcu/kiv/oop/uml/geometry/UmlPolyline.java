package cz.zcu.kiv.oop.uml.geometry;

import java.awt.Polygon;
import java.awt.Shape;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.geometry.lines.AbstractUmlLineIterator;
import cz.zcu.kiv.oop.uml.geometry.lines.UmlLineIterator;

public class UmlPolyline implements UmlLineShape, Serializable, Cloneable {

  /** Serial version UID. */
  private static final long serialVersionUID = -10101879941188147L;

  /** Points of polyline which is composite of lines between each points. */
  protected UmlPoint[] points;
  /** Cashed bounding box of polyline. */
  protected UmlRectangle bounds;

  /**
   * Constructs polyline.
   *
   * @param points array with coordinates of points of polyline. The minimal length of array have to be 4 and also have to be even.
   */
  public UmlPolyline(double[] points) {
    setPoints(points);
  }

  /**
   * Constructs polyline.
   *
   * @param points array with coordinates of points of polyline. The minimal length of array have to be 4 and also have to be even.
   */
  public UmlPolyline(Double[] points) {
    setPoints(points);
  }

  /**
   * Constructs polyline.
   *
   * @param points array of points of polyline. The minimal length of array have to be 2.
   */
  public UmlPolyline(UmlPoint[] points) {
    setPoints(points);
  }

  /**
   * Returns starting point of polyline (the point where the line starts).
   *
   * @return Starting point of polyline.
   */
  @Override
  public UmlPoint getStartPoint() {
    return points[0];
  }

  /**
   * Returns starting point of polyline (the point where the line ends).
   *
   * @return Ending point of polyline.
   */
  @Override
  public UmlPoint getEndPoint() {
    return points[points.length - 1];
  }

  /**
   * Returns points of polyline which is composite of lines between each points.
   *
   * @return Array of point of polyline.
   */
  public UmlPoint[] getPoints() {
    return Arrays.copyOf(points, points.length);
  }

  /**
   * Sets coordinates of points of polyline which is composite of lines between each points.
   *
   * @param points array with coordinates of points of polyline. The minimal length of array have to be 4 and also have to be even.
   */
  public void setPoints(Double[] points) {
    double[] pointsPrimitive = new double[points.length];
    for (int i = 0; i < pointsPrimitive.length; i++) {
      pointsPrimitive[i] = (points[i] == null ? 0 : points[i]);
    }

    setPoints(pointsPrimitive);
  }

  /**
   * Sets coordinates of points of polyline which is composite of lines between each points.
   *
   * @param points array with coordinates of points of polyline. The minimal length of array have to be 4 and also have to be even.
   */
  public void setPoints(double[] points) {
    int n = points.length;
    if (n % 2 != 0) {
      throw new IllegalArgumentException(Strings.get("exc.geometry.polyline.odd-points"));
    }

    if (n / 2 < 2) {
      throw new IllegalArgumentException(Strings.get("exc.geometry.polyline.low-points"));
    }

    this.points = new UmlPoint[n / 2];
    for (int i = 0; i < this.points.length; i++) {
      this.points[i] = new UmlPoint(points[2 * i], points[2 * i + 1]);
    }

    bounds = null;
  }

  /**
   * Sets points of polyline which is composite of lines between each points.
   *
   * @param points array of points of polyline. The minimal length of array have to be 2.
   */
  public void setPoints(UmlPoint[] points) {
    if (points == null || points.length < 2) {
      throw new IllegalArgumentException(Strings.get("exc.geometry.polyline.low-points"));
    }

    this.points = Arrays.copyOf(points, points.length);
    bounds = null;
  }

  /**
   * Returns bounding box of this polyline. The bounding box should contains whole shape.
   *
   * @return Rectangle representing bounds of this polyline.
   */
  @Override
  public UmlRectangle getBounds() {
    if (bounds != null) {
      return bounds;
    }

    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double maxY = Double.MIN_VALUE;

    for (UmlPoint point : points) {
      minX = Math.min(point.getX(), minX);
      minY = Math.min(point.getY(), minY);
      maxX = Math.max(point.getX(), maxX);
      maxY = Math.max(point.getY(), maxY);
    }

    bounds = new UmlRectangle(minX, minY, maxX - minX, maxY - minY);

    return bounds;
  }

  /**
   * Returns information whether this polyline contains point with entered coordinates.
   *
   * @param x the coordinate X of point.
   * @param y the coordinate Y of point.
   * @return <code>true</code> if this polyline contains entered point with entered coordinates; <code>false</code> otherwise.
   */
  @Override
  public boolean contains(double x, double y) {
    for (UmlLine thisLinePart : getLineIterator()) {
      if (thisLinePart.contains(x, y)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns information whether this polyline contains entered point.
   *
   * @param point the point which will be tested whether this polyline contains it.
   * @return <code>true</code> if this polyline contains entered point; <code>false</code> otherwise.
   */
  @Override
  public boolean contains(UmlPoint point) {
    return contains(point.getX(), point.getY());
  }

  /**
   * Returns information whether rectangle with entered properties intersects this polyline.
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
   * Returns information whether entered rectangle intersects this polyline.
   *
   * @param rectangle the rectangle which will be tested whether intersects this polyline.
   * @return <code>true</code> if entered rectangle intersects the shape; <code>false</code> otherwise.
   */
  @Override
  public boolean intersects(UmlRectangle rectangle) {
    return rectangle.intersectsLine(this);
  }

  /**
   * Returns information whether entered line shape intersects this polyline.
   *
   * @param line the line shape which will be tested whether intersects this polyline.
   * @return <code>true</code> if entered line shape intersects the shape; <code>false</code> otherwise.
   */
  @Override
  public boolean intersectsLine(UmlLineShape line) {
    for (UmlLine otherLinePart : line.getLineIterator()) {
      for (UmlLine thisLinePart : getLineIterator()) {
        if (UmlLine.intersection(otherLinePart, thisLinePart) != null) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Returns intersection points of entered line shape with this polyline.
   *
   * @param line the line shape for which will be returned points of intersections.
   * @return Array of intersection points of this and entered line shape.
   */
  @Override
  public UmlPoint[] intersections(UmlLineShape line) {
    List<UmlPoint> intersections = new ArrayList<UmlPoint>();

    for (UmlLine otherLinePart : line.getLineIterator()) {
      for (UmlLine thisLinePart : getLineIterator()) {
        UmlPoint intersection = UmlLine.intersection(otherLinePart, thisLinePart);
        if (intersection != null) {
          intersections.add(intersection);
        }
      }
    }

    return intersections.toArray(new UmlPoint[intersections.size()]);
  }

  /**
   * Returns the AWT equivalent of polyline. This method serves only for compatibility with AWT and for possibility draw this shape.
   * <p>
   * The polyline was mocked into polygon for possibility of drawing in AWT.
   * </p>
   *
   * @return AWT equivalent ({@link Polygon} of polyline.
   */
  @Override
  public Shape getShape() {
    Polygon polygon = new Polygon();
    for (int i = 0; i < points.length; i++) {
      UmlPoint point = points[i];
      polygon.addPoint((int)point.getX(), (int)point.getY());
    }

    for (int i = points.length - 2; i > 0; i--) {
      UmlPoint point = points[i];
      polygon.addPoint((int)point.getX(), (int)point.getY());
    }

    return polygon;
  }

  /**
   * Returns iterator of parts of polyline.
   *
   * @return Iterator of parts of polyline.
   */
  @Override
  public UmlLineIterator getLineIterator() {
    return new AbstractUmlLineIterator() {
      protected int actual = 0;

      @Override
      public boolean hasNext() {
        return (actual < points.length - 1);
      }

      @Override
      protected UmlLine getNext() {
        return new UmlLine(points[actual], points[++actual]);
      }
    };
  }

  /**
   * Clones polyline.
   *
   * @return Clone of this polyline.
   */
  @Override
  public UmlPolyline clone() {
    UmlPoint[] points = new UmlPoint[this.points.length];
    for (int i = 0; i < points.length; i++) {
      points[i] = this.points[i].clone();
    }

    return new UmlPolyline(points);
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

    result = prime * result + Arrays.hashCode(points);

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

    if (!(obj instanceof UmlPolyline)) {
      return false;
    }

    UmlPolyline other = (UmlPolyline)obj;
    if (!Arrays.equals(points, other.points)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of polyline.
   *
   * @return String value of polyline.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [points=" + Arrays.toString(points) + "]";
  }

}
