package cz.zcu.kiv.oop.uml.element;

import java.io.Serializable;

/**
 * Represents font used for formatting of text in UML elements. The font can have be plain, bold, italic or underlined. Also is
 * possible to combine one or more font styles.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlFont implements Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = -7948991412220329051L;

  /** The plain font style constant. */
  public static final int PLAIN = 0;
  /** The bold font style constant. */
  public static final int BOLD = 1;
  /** The italic font style constant. */
  public static final int ITALIC = 2;
  /** The underlined font style constant. */
  public static final int UNDERLINED = 4;

  /** Style of UML font. */
  protected int style;

  /**
   * Constructs UML font.
   */
  public UmlFont() {
    this(PLAIN);
  }

  /**
   * Constructs UML font.
   *
   * @param style style of UML font.
   */
  public UmlFont(int style) {
    this.style = style;
  }

  /**
   * Returns style of UML font.
   *
   * @return Style of UML font.
   */
  public int getStyle() {
    return style;
  }

  /**
   * Sets style of UML font.
   *
   * @param style style of UML font to set.
   */
  public void setStyle(int style) {
    this.style = style;
  }

  /**
   * Returns information whether the font is plain.
   *
   * @return <code>true</code> if the font style is plain; <code>false</code> otherwise.
   */
  public boolean isPlain() {
    return style == 0;
  }

  /**
   * Returns information whether the font is bold.
   *
   * @return <code>true</code> if the font style (also) is bold; <code>false</code> otherwise.
   */
  public boolean isBold() {
    return (style & BOLD) != 0;
  }

  /**
   * Returns information whether the font is italic.
   *
   * @return <code>true</code> if the font style (also) is italic; <code>false</code> otherwise.
   */
  public boolean isItalic() {
    return (style & ITALIC) != 0;
  }

  /**
   * Returns information whether the font is underlined.
   *
   * @return <code>true</code> if the font style (also) is underlined; <code>false</code> otherwise.
   */
  public boolean isUnderlined() {
    return (style & UNDERLINED) != 0;
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

    result = prime * result + style;

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

    if (!(obj instanceof UmlFont)) {
      return false;
    }

    UmlFont other = (UmlFont)obj;
    if (style != other.style) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of UML font.
   *
   * @return String value of UML font.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [style=" + style + "]";
  }

}
