package cz.zcu.kiv.oop.uml.geometry;

import cz.zcu.kiv.oop.uml.geometry.lines.UmlLineIterator;

/**
 * This interface contains common methods for all line shapes used in UML diagrams. Each line shape of UML diagram should implements
 * this interface.
 * <p>
 * This interface extends the {@link UmlShape} interface and adds common methods for all line shapes. Each line should have starting
 * and ending point. If the line shape is composite of more lines, it could be used the line iterator for iterating of each line.
 * Because of that could be any line tested if intersects another shape.
 * </p>
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UmlLineShape extends UmlShape {

  /**
   * Returns starting point of line shape (the point where the line starts).
   *
   * @return Starting point of line shape.
   */
  public UmlPoint getStartPoint();

  /**
   * Returns starting point of line shape (the point where the line ends).
   *
   * @return Ending point of line shape.
   */
  public UmlPoint getEndPoint();

  /**
   * Returns intersection points of entered line shape with this line shape.
   *
   * @param line the line shape for which will be returned points of intersection.
   * @return Array of intersection points of this and entered line shape.
   */
  public UmlPoint[] intersections(UmlLineShape line);

  /**
   * Returns iterator of parts of line shape (if the line shape is composite of more lines).
   *
   * @return Iterator of parts of line shape.
   */
  public UmlLineIterator getLineIterator();

}
