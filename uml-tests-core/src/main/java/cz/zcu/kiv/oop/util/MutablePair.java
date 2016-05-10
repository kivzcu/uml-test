package cz.zcu.kiv.oop.util;

/**
 * A mutable pair consisting of two elements. It refers to the elements as 'left' and 'right'. It also implements the
 * {@code Map.Entry} interface where the key is 'left' and the value is 'right'.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 * @param <L> the left element type.
 * @param <R> the right element type.
 */
public class MutablePair<L, R> extends Pair<L, R> {

  /** Serial version UID. */
  private static final long serialVersionUID = 2834856025458903857L;

  /** Left element of pair. */
  protected L left;
  /** Right element of pair. */
  protected R right;

  /**
   * Constructs mutable pair with two <code>null</code>s.
   */
  public MutablePair() {}

  /**
   * Constructs mutable pair.
   *
   * @param left the left element of pair.
   * @param right the right element of pair.
   */
  public MutablePair(L left, R right) {
    this.left = left;
    this.right = right;
  }

  /**
   * Returns left element of pair. When treated as a key-value pair, this is the key.
   *
   * @return The left element of pair. Can be <code>null</code>.
   */
  @Override
  public L getLeft() {
    return left;
  }

  /**
   * Sets left element of pair.
   *
   * @param left the left element of pair. Can be <code>null</code>.
   */
  public void setLeft(L left) {
    this.left = left;
  }

  /**
   * Returns right element of pair. When treated as a key-value pair, this is the value.
   *
   * @return The right element of pair. Can be <code>null</code>.
   */
  @Override
  public R getRight() {
    return right;
  }

  /**
   * Sets right element of pair.
   *
   * @param right the right element of pair. Can be <code>null</code>.
   */
  public void setRight(R right) {
    this.right = right;
  }

  /**
   * Sets the {@code Map.Entry} value. This sets the right element of the pair.
   *
   * @param value the right element of pair. Should not be <code>null</code>.
   * @return The old value of the right element.
   */
  @Override
  public R setValue(R value) {
    R oldValue = getRight();
    setRight(value);

    return oldValue;
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

    result = prime * result + ((left == null) ? 0 : left.hashCode());
    result = prime * result + ((right == null) ? 0 : right.hashCode());

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

    if (!(obj instanceof ImmutablePair)) {
      return false;
    }

    ImmutablePair<?, ?> other = (ImmutablePair<?, ?>)obj;
    if (left == null) {
      if (other.left != null) {
        return false;
      }
    }
    else if (!left.equals(other.left)) {
      return false;
    }

    if (right == null) {
      if (other.right != null) {
        return false;
      }
    }
    else if (!right.equals(other.right)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of pair.
   *
   * @return String value of pair.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [left=" + left + ", right=" + right + "]";
  }

}
