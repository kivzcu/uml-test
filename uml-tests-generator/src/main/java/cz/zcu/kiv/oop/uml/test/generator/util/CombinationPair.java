package cz.zcu.kiv.oop.uml.test.generator.util;

import cz.zcu.kiv.oop.util.ImmutablePair;
import cz.zcu.kiv.oop.util.Pair;

public class CombinationPair<L, R> extends Pair<L, R> {

  private static final long serialVersionUID = -585919234179778017L;

  protected L left;
  protected R right;

  public CombinationPair(L left, R right) {
    setLeft(left);
    setRight(right);
  }

  @Override
  public L getLeft() {
    return left;
  }

  public void setLeft(L left) {
    this.left = left;
  }

  @Override
  public R getRight() {
    return right;
  }

  public void setRight(R right) {
    this.right = right;
  }

  @Override
  public R setValue(R value) {
    R oldValue = getRight();
    setRight(value);

    return oldValue;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result += (left == null) ? 0 : left.hashCode();
    result += (right == null) ? 0 : right.hashCode();

    return prime * result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof CombinationPair)) {
      return false;
    }

    CombinationPair<?, ?> other = (CombinationPair<?, ?>)obj;
    Pair<?, ?> thisPair = new ImmutablePair<Object, Object>(getLeft(), getRight());
    Pair<?, ?> otherPair = new ImmutablePair<Object, Object>(other.getLeft(), other.getRight());
    if (!thisPair.equals(otherPair)) {
      otherPair = new ImmutablePair<Object, Object>(other.getRight(), other.getLeft());

      return thisPair.equals(otherPair);
    }

    return true;
  }

  @Override
  public String toString() {
    return getClass().getName() + " [left=" + left + ", right=" + right + "]";
  }

}
