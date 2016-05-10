package cz.zcu.kiv.oop.util;

/**
 * Simple counter which provides basic operations.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class Counter {

  /** Counter value. */
  protected long value;
  /** Initial value of counter. */
  public final long initialValue;

  /**
   * Constructs counter with initial value equals to 0.
   */
  public Counter() {
    this(0);
  }

  /**
   * Constructs counter with entered initial value.
   *
   * @param initialValue initial value of counter.
   */
  public Counter(long initialValue) {
    value = initialValue;
    this.initialValue = initialValue;
  }

  /**
   * Increases value of counter.
   *
   * @return New value of counter (oldValue + 1).
   */
  public long increase() {
    return ++value;
  }

  /**
   * Decreases value of counter.
   *
   * @return New value of counter (oldValue - 1).
   */
  public long decrease() {
    return --value;
  }

  /**
   * Returns actual value of counter.
   *
   * @return Actual value of counter.
   */
  public long getValue() {
    return value;
  }

  /**
   * Initial value of counter.
   *
   * @return Initial value of counter
   */
  public long getInitialValue() {
    return initialValue;
  }

  /**
   * Returns information whether value of counter is equal to zero.
   *
   * @return <code>true</code> if value of counter is equal to zero; <code>false</code> otherwise.
   */
  public boolean isZero() {
    return value == 0;
  }

  /**
   * Resets counter.
   */
  public void reset() {
    value = initialValue;
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

    result = prime * result + (int)(value ^ (value >>> 32));

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

    if (!(obj instanceof Counter)) {
      return false;
    }

    Counter other = (Counter)obj;
    if (value != other.value) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of this counter.
   *
   * @return String value of counter.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [value=" + getValue() + "]";
  }

}
