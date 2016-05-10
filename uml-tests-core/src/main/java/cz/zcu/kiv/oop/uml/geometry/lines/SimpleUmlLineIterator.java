package cz.zcu.kiv.oop.uml.geometry.lines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cz.zcu.kiv.oop.uml.geometry.UmlLine;

/**
 * Simple implementation of iterator of parts of line shape (if the line shape is composite of more lines) which can be constructed
 * by array or collection of lines.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class SimpleUmlLineIterator extends AbstractUmlLineIterator implements UmlLineIterator {

  /** Actual index of iteration. */
  protected int actual;
  /** List of lines whose will be iterated. */
  protected final List<UmlLine> lines = new ArrayList<UmlLine>();

  /**
   * Constructs iterator.
   *
   * @param lines collection of lines which will be iterated.
   */
  public SimpleUmlLineIterator(Collection<UmlLine> lines) {
    this.lines.addAll(lines);
  }

  /**
   * Constructs iterator.
   *
   * @param lines array of lines which will be iterated.
   */
  public SimpleUmlLineIterator(UmlLine[] lines) {
    for (UmlLine line : lines) {
      this.lines.add(line);
    }
  }

  /**
   * Returns <code>true</code> if the iteration has more elements. (In other words, returns <code>true</code> if <code>next</code>
   * would return an element rather than throwing an exception.)
   *
   * @return <code>true</code> if the iterator has more elements.
   */
  @Override
  public boolean hasNext() {
    return actual < lines.size();
  }

  /**
   * Returns the next element in the iteration. This method is called by {@link #next()} in case, that the method {@link #next()}
   * returns <code>true</code>.
   *
   * @return The next element in the iteration.
   */
  @Override
  protected UmlLine getNext() {
    return lines.get(actual++);
  }

}
