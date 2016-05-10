package cz.zcu.kiv.oop.uml.geometry;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import cz.zcu.kiv.oop.Strings;

/**
 * The rectangle implementation of shape which is on some position and have width and height.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlRectangle implements UmlShape, Serializable, Cloneable {

  /** Serial version UID. */
  private static final long serialVersionUID = 730327274036758957L;

  /** The bitmask that indicates that a point lies to the left of this rectangle. */
  public static final int OUT_LEFT = 1;
  /** The bitmask that indicates that a point lies above this rectangle. */
  public static final int OUT_TOP = 2;
  /** The bitmask that indicates that a point lies to the right of this rectangle. */
  public static final int OUT_RIGHT = 4;
  /** The bitmask that indicates that a point lies below this rectangle. */
  public static final int OUT_BOTTOM = 8;

  /** Coordinate X of this rectangle. */
  protected double x;
  /** Coordinate Y of this rectangle. */
  protected double y;

  /** Width of this rectangle. */
  protected double width;
  /** Height of this rectangle. */
  protected double height;

  /**
   * Constructs rectangle.
   *
   * @param x the coordinate X of rectangle.
   * @param y the coordinate Y of rectangle.
   * @param width the width of rectangle.
   * @param height the height of rectangle.
   */
  public UmlRectangle(double x, double y, double width, double height) {
    setRect(x, y, width, height);
  }

  /**
   * Returns coordinate X of this rectangle.
   *
   * @return Coordinate X of this rectangle.
   */
  public double getX() {
    return x;
  }

  /**
   * Sets coordinate X of this rectangle.
   *
   * @param x coordinate X of rectangle to set.
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Returns coordinate Y of this rectangle.
   *
   * @return Coordinate Y of this rectangle.
   */
  public double getY() {
    return y;
  }

  /**
   * Sets coordinate Y of this rectangle.
   *
   * @param y coordinate Y of rectangle to set.
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Returns coordinate X of center of this rectangle.
   *
   * @return Coordinate X of center of this rectangle.
   */
  public double getCenterX() {
    return getX() + getWidth() / 2.0;
  }

  /**
   * Returns coordinate Y of center of this rectangle.
   *
   * @return Coordinate Y of center of this rectangle.
   */
  public double getCenterY() {
    return getY() + getHeight() / 2.0;
  }

  /**
   * Returns maximal coordinate X of center of this rectangle (coordinate X + width).
   *
   * @return Maximal coordinate X of center of this rectangle.
   */
  public double getMaxX() {
    return x + width;
  }

  /**
   * Returns maximal coordinate Y of center of this rectangle (coordinate Y + width).
   *
   * @return Maximal coordinate Y of center of this rectangle.
   */
  public double getMaxY() {
    return y + height;
  }

  /**
   * Returns width of this rectangle.
   *
   * @return Width of this rectangle.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Sets width of this rectangle.
   *
   * @param width width of rectangle to set.
   */
  public void setWidth(double width) {
    if (width < 0) {
      throw new IllegalArgumentException(Strings.get("exc.geometry.negative-width"));
    }

    this.width = width;
  }

  /**
   * Returns height of this rectangle.
   *
   * @return Height of this rectangle.
   */
  public double getHeight() {
    return height;
  }

  /**
   * Sets height of this rectangle.
   *
   * @param height height of rectangle to set.
   */
  public void setHeight(double height) {
    if (height < 0) {
      throw new IllegalArgumentException(Strings.get("exc.geometry.negative-height"));
    }

    this.height = height;
  }

  /**
   * Returns position of this rectangle.
   *
   * @return Position of this rectangle.
   */
  public UmlPoint getPosition() {
    return new UmlPoint(getX(), getY());
  }

  /**
   * Returns size of this rectangle.
   *
   * @return Size of this rectangle.
   */
  public UmlDimension getSize() {
    return new UmlDimension(getWidth(), getHeight());
  }

  /**
   * Sets properties of this rectangle.
   *
   * @param x the coordinate X of rectangle.
   * @param y the coordinate Y of rectangle.
   * @param width the width of rectangle.
   * @param height the height of rectangle.
   */
  public void setRect(double x, double y, double width, double height) {
    setX(x);
    setY(y);
    setWidth(width);
    setHeight(height);
  }

  /**
   * Sets properties of this rectangle from entered rectangle.
   *
   * @param rectangle the rectangle which properties will be set to this rectangle.
   */
  public void setRect(UmlRectangle rectangle) {
    setX(rectangle.getX());
    setY(rectangle.getY());
    setWidth(rectangle.getWidth());
    setHeight(rectangle.getHeight());
  }

  /**
   * Determines where the specified coordinates lie with respect to this rectangle. This method computes a binary OR of the
   * appropriate mask values indicating, for each side of this rectangle, whether or not the specified coordinates are on the same
   * side of the edge as the rest of this rectangle.
   *
   * @param x the specified X coordinate.
   * @param y the specified Y coordinate.
   * @return the logical OR of all appropriate out codes.
   * @see #OUT_LEFT
   * @see #OUT_TOP
   * @see #OUT_RIGHT
   * @see #OUT_BOTTOM
   */
  public int outcode(double x, double y) {
    int out = 0;
    if (this.width <= 0) {
      out |= OUT_LEFT | OUT_RIGHT;
    }
    else if (x < this.x) {
      out |= OUT_LEFT;
    }
    else if (x > this.x + this.width) {
      out |= OUT_RIGHT;
    }
    if (this.height <= 0) {
      out |= OUT_TOP | OUT_BOTTOM;
    }
    else if (y < this.y) {
      out |= OUT_TOP;
    }
    else if (y > this.y + this.height) {
      out |= OUT_BOTTOM;
    }

    return out;
  }

  /**
   * Determines where the specified point lies with respect to this rectangle. This method computes a binary OR of the appropriate
   * mask values indicating, for each side of this rectangle, whether or not the specified point is on the same side of the edge as
   * the rest of this rectangle.
   *
   * @param p The specified point.
   * @return The logical OR of all appropriate out codes.
   * @see #OUT_LEFT
   * @see #OUT_TOP
   * @see #OUT_RIGHT
   * @see #OUT_BOTTOM
   */
  public int outcode(UmlPoint p) {
    return outcode(p.getX(), p.getY());
  }

  /**
   * Returns bounding box of this rectangle. The bounding box should contains whole rectangle.
   *
   * @return Rectangle representing bounds of this rectangle.
   */
  @Override
  public UmlRectangle getBounds() {
    return new UmlRectangle(getX(), getY(), getWidth(), getHeight());
  }

  /**
   * Returns information whether this rectangle contains point with entered coordinates.
   *
   * @param x the coordinate X of point.
   * @param y the coordinate Y of point.
   * @return <code>true</code> if this rectangle contains entered point with entered coordinates; <code>false</code> otherwise.
   */
  @Override
  public boolean contains(double x, double y) {
    double x0 = getX();
    double y0 = getY();

    return (x >= x0 && y >= y0 && x <= x0 + getWidth() && y <= y0 + getHeight());
  }

  /**
   * Returns information whether this rectangle contains entered point.
   *
   * @param point the point which will be tested whether this rectangle contains it.
   * @return <code>true</code> if this rectangle contains entered point; <code>false</code> otherwise.
   */
  @Override
  public boolean contains(UmlPoint point) {
    return contains(point.getX(), point.getY());
  }

  /**
   * Returns information whether rectangle with entered properties intersects this rectangle.
   *
   * @param x the coordinate X of rectangle.
   * @param y the coordinate Y of rectangle.
   * @param w the width of rectangle.
   * @param h the height of rectangle.
   * @return <code>true</code> if rectangle with entered properties intersects the rectangle; <code>false</code> otherwise.
   */
  @Override
  public boolean intersects(double x, double y, double w, double h) {
    double x0 = getX();
    double y0 = getY();

    return (x + w > x0 && y + h > y0 && x < x0 + getWidth() && y < y0 + getHeight());
  }

  /**
   * Returns information whether entered rectangle intersects this rectangle.
   *
   * @param rectangle the rectangle which will be tested whether intersects this rectangle.
   * @return <code>true</code> if entered rectangle intersects the rectangle; <code>false</code> otherwise.
   */
  @Override
  public boolean intersects(UmlRectangle rectangle) {
    return intersects(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
  }

  /**
   * Returns information whether entered line shape intersects this rectangle.
   *
   * @param line the line shape which will be tested whether intersects this rectangle.
   * @return <code>true</code> if entered line shape intersects the rectangle; <code>false</code> otherwise.
   */
  @Override
  public boolean intersectsLine(UmlLineShape line) {
    for (UmlLine umlLinePart : line.getLineIterator()) {
      if (intersectsLine(umlLinePart)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns information whether entered line intersects this rectangle.
   *
   * @param line the line which will be tested whether intersects this rectangle.
   * @return <code>true</code> if entered line intersects the rectangle; <code>false</code> otherwise.
   */
  public boolean intersectsLine(UmlLine line) {
    int out1 = 0;
    int out2 = 0;

    double x1 = line.getStartPointX();
    double y1 = line.getStartPointY();
    double x2 = line.getEndPointX();
    double y2 = line.getEndPointY();

    if ((out2 = outcode(x2, y2)) == 0) {
      return true;
    }

    while ((out1 = outcode(x1, y1)) != 0) {
      if ((out1 & out2) != 0) {
        return false;
      }
      if ((out1 & (OUT_LEFT | OUT_RIGHT)) != 0) {
        double x = getX();
        if ((out1 & OUT_RIGHT) != 0) {
          x += getWidth();
        }
        y1 = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
        x1 = x;
      }
      else {
        double y = getY();
        if ((out1 & OUT_BOTTOM) != 0) {
          y += getHeight();
        }
        x1 = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
        y1 = y;
      }
    }

    return true;
  }

  /**
   * Returns the AWT equivalent of this rectangle. This method serves only for compatibility with AWT and for possibility draw this
   * rectangle.
   *
   * @return AWT equivalent ({@link Rectangle2D}) of this rectangle.
   */
  @Override
  public Rectangle2D getShape() {
    return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
  }

  /**
   * Clones rectangle.
   *
   * @return Clone of this rectangle.
   */
  @Override
  public UmlRectangle clone() {
    return new UmlRectangle(x, y, width, height);
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
    temp = Double.doubleToLongBits(width);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(height);
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

    if (!(obj instanceof UmlRectangle)) {
      return false;
    }

    UmlRectangle other = (UmlRectangle)obj;
    if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height)) {
      return false;
    }

    if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width)) {
      return false;
    }

    if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
      return false;
    }

    if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of rectangle.
   *
   * @return String value of rectangle.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
  }

}
