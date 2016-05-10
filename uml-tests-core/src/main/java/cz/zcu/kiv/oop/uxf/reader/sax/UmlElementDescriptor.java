package cz.zcu.kiv.oop.uxf.reader.sax;

import java.io.Serializable;

/**
 * Descriptor which serves for store read information of UML element from UXF file. UXF reader stores read data into descriptor and
 * this descriptor is used for creating UML elements.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlElementDescriptor implements Cloneable, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = 2720192591535464106L;

  /** Type of UML element. */
  protected String elementType;

  /** Coordinate X of UML element. */
  protected double x;
  /** Coordinate Y of UML element. */
  protected double y;
  /** Width of UML element. */
  protected double width;
  /** Height of UML element. */
  protected double height;

  /** Panel attributes of UML element. */
  protected String panelAttributes;
  /** Additional attributes of UML element. */
  protected String additionalAttributes;

  /** Zoom level of UML element. */
  protected double zoomFactor;

  /**
   * Constructs UML element descriptor.
   */
  public UmlElementDescriptor() {
    zoomFactor = 1.0;
  }

  /**
   * Returns type of UML element.
   *
   * @return Type of UML element.
   */
  public String getElementType() {
    return elementType;
  }

  /**
   * Sets type of UML element.
   *
   * @param elementType Type of UML element to set.
   */
  public void setElementType(String elementType) {
    this.elementType = elementType;
  }

  /**
   * Returns coordinate X of UML element.
   *
   * @return Coordinate X of UML element.
   */
  public double getX() {
    return x;
  }

  /**
   * Sets coordinate X of UML element.
   *
   * @param x coordinate X to set.
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Returns coordinate Y of UML element.
   *
   * @return Coordinate Y of UML element.
   */
  public double getY() {
    return y;
  }

  /**
   * Sets coordinate Y of UML element.
   *
   * @param y coordinate Y to set.
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Returns width of UML element.
   *
   * @return Width of UML element.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Sets width of UML element.
   *
   * @param width width to set.
   */
  public void setWidth(double width) {
    this.width = width;
  }

  /**
   * Returns height of UML element.
   *
   * @return Height of UML element.
   */
  public double getHeight() {
    return height;
  }

  /**
   * Sets height of UML element.
   *
   * @param height height to set.
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * Returns panel attributes of UML element.
   *
   * @return Panel attributes of UML element.
   */
  public String getPanelAttributes() {
    return panelAttributes;
  }

  /**
   * Sets panel attributes of UML element.
   *
   * @param panelAttributes panel attributes to set.
   */
  public void setPanelAttributes(String panelAttributes) {
    this.panelAttributes = panelAttributes;
  }

  /**
   * Returns additional attributes of UML element.
   *
   * @return Additional attributes of UML element.
   */
  public String getAdditionalAttributes() {
    return additionalAttributes;
  }

  /**
   * Sets additional attributes of UML element.
   *
   * @param additionalAttributes additional attributes to set.
   */
  public void setAdditionalAttributes(String additionalAttributes) {
    this.additionalAttributes = additionalAttributes;
  }

  /**
   * Returns zoom level of UML element.
   *
   * @return Zoom level of UML element.
   */
  public double getZoomFactor() {
    return zoomFactor;
  }

  /**
   * Sets zoom level of UML element.
   *
   * @param zoomFactor zoom level to set.
   */
  public void setZoomFactor(double zoomFactor) {
    this.zoomFactor = zoomFactor;
  }

  /**
   * Creates and returns a copy of this object. The precise meaning of "copy" may depend on the class of the object.
   *
   * @return A clone of this instance.
   */
  @Override
  public UmlElementDescriptor clone() {
    UmlElementDescriptor clone = new UmlElementDescriptor();
    clone.setElementType(elementType);
    clone.setX(x);
    clone.setY(y);
    clone.setWidth(width);
    clone.setHeight(height);
    clone.setPanelAttributes(panelAttributes);
    clone.setAdditionalAttributes(additionalAttributes);
    clone.setZoomFactor(zoomFactor);

    return clone;
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
    long temp = 0;

    result = prime * result + ((elementType == null) ? 0 : elementType.hashCode());
    temp = Double.doubleToLongBits(x);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(width);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(height);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    result = prime * result + ((panelAttributes == null) ? 0 : panelAttributes.hashCode());
    result = prime * result + ((additionalAttributes == null) ? 0 : additionalAttributes.hashCode());
    temp = Double.doubleToLongBits(zoomFactor);
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

    if (!(obj instanceof UmlElementDescriptor)) {
      return false;
    }

    UmlElementDescriptor other = (UmlElementDescriptor)obj;
    if (elementType == null) {
      if (other.elementType != null) {
        return false;
      }
    }
    else if (!elementType.equals(other.elementType)) {
      return false;
    }

    if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
      return false;
    }

    if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
      return false;
    }

    if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width)) {
      return false;
    }

    if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height)) {
      return false;
    }

    if (panelAttributes == null) {
      if (other.panelAttributes != null) {
        return false;
      }
    }
    else if (!panelAttributes.equals(other.panelAttributes)) {
      return false;
    }

    if (additionalAttributes == null) {
      if (other.additionalAttributes != null) {
        return false;
      }
    }
    else if (!additionalAttributes.equals(other.additionalAttributes)) {
      return false;
    }

    if (Double.doubleToLongBits(zoomFactor) != Double.doubleToLongBits(other.zoomFactor)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of element descriptor.
   *
   * @return String value of element descriptor.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [elementType=" + elementType + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", panelAttributes="
        + panelAttributes + ", additionalAttributes=" + additionalAttributes + ", zoomFactor=" + zoomFactor + "]";
  }

}
