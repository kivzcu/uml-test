package cz.zcu.kiv.oop.uml.geometry.lines;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.geometry.UmlLine;

/**
 * Abstract implementation of iterator of parts of line shape (if the line shape is composite of more lines).
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public abstract class AbstractUmlLineIterator implements UmlLineIterator {

  /**
   * Returns the next element in the iteration.
   *
   * @return The next element in the iteration.
   * @exception NoSuchElementException iteration has no more elements.
   */
  @Override
  public UmlLine next() {
    if (hasNext()) {
      return getNext();
    }

    throw new NoSuchElementException();
  }

  /**
   * Returns the next element in the iteration. This method is called by {@link #next()} in case, that the method {@link #next()}
   * returns <code>true</code>. The main purpose of this method is simpler implementation of line iterators.
   *
   * @return The next element in the iteration.
   */
  protected abstract UmlLine getNext();

  /**
   * Always throws {@code UnsupportedOperationException} because this operation is not supported.
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException(Strings.get("exc.geometry.remove-not-supported"));
  }

  /**
   * Returns instance of this iterator.
   *
   * @return Instance of this iterator.
   */
  @Override
  public Iterator<UmlLine> iterator() {
    return this;
  }

}
