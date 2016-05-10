package cz.zcu.kiv.oop.util;

import java.io.Serializable;
import java.util.Map;

/**
 * A pair consisting of two elements. It refers to the elements as 'left' and 'right'. It also implements the {@code Map.Entry}
 * interface where the key is 'left' and the value is 'right'.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 * @param <L> the left element type.
 * @param <R> the right element type.
 */
public abstract class Pair<L, R> implements Map.Entry<L, R>, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = 5049451792284663972L;

  /**
   * Returns left element of pair. When treated as a key-value pair, this is the key.
   *
   * @return The left element of pair. Can be <code>null</code>.
   */
  public abstract L getLeft();

  /**
   * Returns right element of pair. When treated as a key-value pair, this is the value.
   *
   * @return The right element of pair. Can be <code>null</code>.
   */
  public abstract R getRight();

  /**
   * Returns the key of pair. This method implements the {@code Map.Entry} interface returning the left element as the key.
   *
   * @return The left element of pair. Can be <code>null</code>.
   */
  @Override
  public final L getKey() {
    return getLeft();
  }

  /**
   * Returns the value of pair. This method implements the {@code Map.Entry} interface returning the right element as the value.
   *
   * @return The right element as the value. Can be <code>null</code>.
   */
  @Override
  public R getValue() {
    return getRight();
  }

}
