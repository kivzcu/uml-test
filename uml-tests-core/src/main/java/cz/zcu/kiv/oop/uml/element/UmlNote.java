package cz.zcu.kiv.oop.uml.element;

import java.text.Collator;

/**
 * Note type of UML element which contains some text and can be formated.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlNote extends AbstractUmlElement implements UmlElement, Comparable<UmlNote>, Cloneable {

  /** Serial version UID. */
  private static final long serialVersionUID = 6521328116639319513L;

  /** Text of note. */
  protected String text;

  /**
   * Constructs UML note.
   */
  public UmlNote() {
    elementType = UmlElementType.NOTE;
  }

  /**
   * Returns text of note.
   *
   * @return Text of note.
   */
  public String getText() {
    return text;
  }

  /**
   * Sets text of note.
   *
   * @param text text of note.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Compares this object with the entered object for order. Returns a negative integer, zero, or a positive integer as this object
   * is less than, equal to, or greater than the entered object.
   *
   * @param other the other UML note to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the entered
   *         object.
   */
  @Override
  public int compareTo(UmlNote other) {
    if (other == null) {
      return 1;
    }

    String otherText = other.getText();
    if (otherText == null) {
      return 1;
    }

    String text = getText();
    if (text == null) {
      return -1;
    }

    Collator collator = Collator.getInstance(LOCALE);

    return collator.compare(text, otherText);
  }

  /**
   * Clones UML note.
   *
   * @return Clone of this UML note.
   */
  @Override
  public UmlNote clone() {
    UmlNote clone = new UmlNote();
    clone.setText(text);

    // common attributes
    clone.setCoordinates(coordinates == null ? null : coordinates.clone());
    clone.setAttributes(attributes == null ? null : attributes.clone());

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

    result = prime * result + ((text == null) ? 0 : text.hashCode());

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

    if (!(obj instanceof UmlNote)) {
      return false;
    }

    UmlNote other = (UmlNote)obj;
    if (text == null) {
      if (other.text != null) {
        return false;
      }
    }
    else if (!text.equals(other.text)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of UML note.
   *
   * @return String value of UML note.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [text=" + text + "]";
  }

}
