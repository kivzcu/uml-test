package cz.zcu.kiv.oop.uml.geometry;

import java.awt.Dimension;
import java.io.Serializable;

import cz.zcu.kiv.oop.Strings;

/**
 * This class encapsulates the width and height of some shape. The values of <code>width</code> and <code>height</code> are
 * non-negative integers.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlDimension implements Serializable, Cloneable {

  /** Serial version UID. */
  private static final long serialVersionUID = -2684821229673661683L;

  /** Width of this dimension. */
  protected double width;
  /** Height of this dimension. */
  protected double height;

  /**
   * Constructs dimension.
   */
  public UmlDimension() {
    setSize(0, 0);
  }

  /**
   * Constructs dimension with width and height of entered dimension.
   *
   * @param dimension dimension which width and height will be used for construction of new dimension.
   */
  public UmlDimension(UmlDimension dimension) {
    setSize(dimension);
  }

  /**
   * Constructs dimension with width and height.
   *
   * @param width width of dimension.
   * @param height height of dimension.
   */
  public UmlDimension(double width, double height) {
    setSize(width, height);
  }

  /**
   * Returns width of this dimension.
   *
   * @return Width of this dimension.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Sets width of this dimension.
   *
   * @param width the width to set.
   */
  public void setWidth(double width) {
    if (width < 0) {
      throw new IllegalArgumentException(Strings.get("exc.geometry.negative-width"));
    }

    this.width = width;
  }

  /**
   * Returns height of this dimension.
   *
   * @return Height of this dimension.
   */
  public double getHeight() {
    return height;
  }

  /**
   * Sets height of this dimension.
   *
   * @param height the height to set.
   */
  public void setHeight(double height) {
    if (height < 0) {
      throw new IllegalArgumentException(Strings.get("exc.geometry.negative-height"));
    }

    this.height = height;
  }

  /**
   * Sets size from entered dimension.
   *
   * @param dimension the dimension which width and height will be set.
   */
  public void setSize(UmlDimension dimension) {
    setWidth(dimension.width);
    setHeight(dimension.height);
  }

  /**
   * Sets size of dimension (width and height).
   *
   * @param width the width to set.
   * @param height the height to set.
   */
  public void setSize(double width, double height) {
    setWidth(width);
    setHeight(height);
  }

  /**
   * Returns AWT equivalent of this dimension.
   *
   * @return AWT equivalent ({@link Dimension}) of this dimension.
   */
  public Dimension getDimension() {
    return new Dimension((int)width, (int)height);
  }

  /**
   * Clones dimension.
   *
   * @return Clone of this dimension.
   */
  @Override
  public UmlDimension clone() {
    return new UmlDimension(width, height);
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

    long temp = Double.doubleToLongBits(width);
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

    if (!(obj instanceof UmlDimension)) {
      return false;
    }

    UmlDimension other = (UmlDimension)obj;
    if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width)) {
      return false;
    }

    if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of dimension.
   *
   * @return String value of dimension.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [width=" + width + ", height=" + height + "]";
  }

}
