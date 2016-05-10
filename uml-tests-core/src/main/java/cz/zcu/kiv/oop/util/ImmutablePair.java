package cz.zcu.kiv.oop.util;

/**
 * A immutable pair consisting of two elements. It refers to the elements as 'left' and 'right'. It also implements the
 * {@code Map.Entry} interface where the key is 'left' and the value is 'right'.
 * <p>
 * Although the implementation is immutable, there is no restriction on the objects that may be stored. If mutable objects are
 * stored in the pair, then the pair itself effectively becomes mutable. The class is also not {@code final}, so a subclass could
 * add undesirable behavior.
 * </p>
 *
 * @author Mr.FrAnTA (Michal Dékány)
 * @param <L> the left element type.
 * @param <R> the right element type.
 */
public class ImmutablePair<L, R> extends Pair<L, R> {

  /** Serial version UID. */
  private static final long serialVersionUID = -5613377546872564368L;

  /** Left element of pair. */
  protected final L left;
  /** Right element of pair. */
  protected final R right;

  /**
   * Constructs immutable pair.
   *
   * @param left the left element of pair.
   * @param right the right element of pair.
   */
  public ImmutablePair(L left, R right) {
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
   * Returns right element of pair. When treated as a key-value pair, this is the value.
   *
   * @return The right element of pair. Can be <code>null</code>.
   */
  @Override
  public R getRight() {
    return right;
  }

  /**
   * Always throws {@code UnsupportedOperationException} because this pair is immutable, so this operation is not supported.
   *
   * @param value the value to set.
   * @return This method returns nothing.
   * @throws UnsupportedOperationException because this operation is not supported.
   */
  @Override
  public R setValue(R value) {
    throw new UnsupportedOperationException();
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
