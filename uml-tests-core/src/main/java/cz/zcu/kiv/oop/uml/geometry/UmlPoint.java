package cz.zcu.kiv.oop.uml.geometry;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class UmlPoint implements Cloneable, Comparable<UmlPoint>, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = 4977165277514115458L;

  /** Coordinate X of the point. */
  protected double x;
  /** Coordinate Y of the point. */
  protected double y;

  /**
   * Constructs point at location [0, 0].
   */
  public UmlPoint() {
    this.x = 0;
    this.y = 0;
  }

  /**
   * Constructs point at location [x, y].
   *
   * @param x coordinate X of the point.
   * @param y coordinate Y of the point.
   */
  public UmlPoint(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns coordinate X of this point.
   *
   * @return Coordinate X of this point.
   */
  public double getX() {
    return x;
  }

  /**
   * Sets coordinate X of this point.
   *
   * @param x coordinate X of this point.
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Returns coordinate Y of this point.
   *
   * @return Coordinate Y of this point.
   */
  public double getY() {
    return y;
  }

  /**
   * Sets coordinate Y of this point.
   *
   * @param y coordinate Y of this point.
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Sets new coordinates (location) of this point.
   *
   * @param x coordinate X of point which will be used for setting of new coordinates (location).
   * @param y coordinate YF of point which will be used for setting of new coordinates (location).
   */
  public void setLocation(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Sets new coordinates (location) of this point.
   *
   * @param point point which will be used for setting of new coordinates (location).
   */
  public void setLocation(UmlPoint point) {
    setLocation(point.getX(), point.getY());
  }

  /**
   * Returns distance between this point and the entered one.
   *
   * @param px the coordinate X of the point for which will be calculated distance from this point.
   * @param py the coordinate Y of the point for which will be calculated distance from this point.
   * @return Distance between this and entered points.
   */
  public double distanceFrom(double px, double py) {
    return distance(px, py, x, y);
  }

  /**
   * Returns distance between this point and the entered one.
   *
   * @param point the point for which will be calculated distance from this point.
   * @return Distance between this and entered points.
   */
  public double distanceFrom(UmlPoint point) {
    return distance(point, this);
  }

  /**
   * Returns AWT equivalent of this point.
   *
   * @return AWT equivalent ({@link Point2D}) of this point.
   */
  public Point2D getPoint() {
    return new Point.Double(x, y);
  }

  /**
   * Compares this object with the entered object for order. Returns a negative integer, zero, or a positive integer as this object
   * is less than, equal to, or greater than the entered object.
   *
   * @param other the other UML point to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the entered
   *         object.
   */
  @Override
  public int compareTo(UmlPoint other) {
    if (other == null) {
      return 1;
    }

    int result = Double.compare(getX(), other.getX());
    if (result == 0) {
      result = Double.compare(getY(), other.getY());
    }

    return result;
  }

  /**
   * Clones point.
   *
   * @return Clone of this point.
   */
  @Override
  public UmlPoint clone() {
    return new UmlPoint(x, y);
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
    long temp = Double.doubleToLongBits(x);
    result = prime * result + (int)(temp ^ (temp >>> 32));

    temp = Double.doubleToLongBits(y);
    result = prime * result + (int)(temp ^ (temp >>> 32));

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

    if (!(obj instanceof UmlPoint)) {
      return false;
    }

    UmlPoint other = (UmlPoint)obj;
    if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
      return false;
    }

    if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of point.
   *
   * @return String value of point.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [x=" + x + ", y=" + y + "]";
  }

  /**
   * Returns distance between two points.
   *
   * @param x1 coordinate X of first point.
   * @param y1 coordinate Y of first point.
   * @param x2 coordinate X of second point.
   * @param y2 coordinate Y of second point.
   * @return Distance between two points.
   */
  public static double distance(double x1, double y1, double x2, double y2) {
    double a = x1 - x2;
    double b = y1 - y2;

    return Math.sqrt(a * a + b * b);
  }

  /**
   * Returns distance between two points.
   *
   * @param p1 first point.
   * @param p2 second point.
   * @return Distance between two points.
   */
  public static double distance(UmlPoint p1, UmlPoint p2) {
    return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
  }

}
