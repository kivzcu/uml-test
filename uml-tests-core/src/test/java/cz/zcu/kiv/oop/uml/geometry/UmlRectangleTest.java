package cz.zcu.kiv.oop.uml.geometry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.geom.Rectangle2D;

/**
 * Test of UML rectangle.
 *
 * @author Karel Zíbar
 */
public class UmlRectangleTest {

  protected static UmlRectangle rect;

  /**
   * Prepare UmlRectangle for testing
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    rect = new UmlRectangle(0, 0, 10, 5);
  }

  /**
   * Set tested UmlRectangle to null
   */
  @AfterClass
  public static void tearDownAfterClass() {
    rect = null;
  }

  /**
   * Tests getters for UmlRectangle properties
   */
  @Test
  public void testGetProperty() {
    Assert.assertEquals(0, rect.getX(), 0.0);
    Assert.assertEquals(0, rect.getY(), 0.0);
    Assert.assertEquals(10, rect.getWidth(), 0.0);
    Assert.assertEquals(5, rect.getHeight(), 0.0);

    Assert.assertEquals(5, rect.getCenterX(), 0.0);
    Assert.assertEquals(2.5, rect.getCenterY(), 0.0);
  }

  /**
   * Checks if IllegalArgumentException is thrown when trying to set width of UmlRectangle to value < 0
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetWidthException() {
    rect.setWidth(-5);
  }

  /**
   * Checks if IllegalArgumentException is thrown when trying to set height of UmlRectangle to value < 0
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetHeightException() {
    rect.setHeight(-5);
  }

  /**
   * Tests if correct position of UmlRectangle is returned
   */
  @Test
  public void testGetPosition() {
    UmlPoint point = new UmlPoint(0, 0);
    Assert.assertEquals(point, rect.getPosition());
  }

  /**
   * Tests if correct UmlDimension represented UmlRectanhle content is returned
   */
  @Test
  public void testGetSize() {
    UmlDimension dim = new UmlDimension(10, 5);
    Assert.assertEquals(dim, rect.getSize());
  }

  /**
   * Tests setters for UmlRectangle properties.
   */
  @Test
  public void testSetRect() {
    UmlRectangle rect = new UmlRectangle(0, 0, 0, 0);
    rect.setRect(new UmlRectangle(1, 1, 10, 5));
    Assert.assertEquals(1.0, rect.getX(), 0.0);
    Assert.assertEquals(1.0, rect.getY(), 0.0);
    Assert.assertEquals(10.0, rect.getWidth(), 0.0);
    Assert.assertEquals(5.0, rect.getHeight(), 0.0);
  }

  /**
   * Tests if correct shape of UmlRectangle is returned.
   */
  @Test
  public void testGetShape() {
    Rectangle2D shape = rect.getShape();
    Assert.assertEquals(0.0, shape.getX(), 0.0);
    Assert.assertEquals(0.0, shape.getY(), 0.0);
    Assert.assertEquals(10.0, shape.getWidth(), 0.0);
    Assert.assertEquals(5.0, shape.getHeight(), 0.0);
  }

  /**
   * Tests if correct bounds of UmlRectangle are returned.
   */
  @Test
  public void testGetBounds() {
    UmlRectangle bounds = rect.getBounds();
    Assert.assertEquals(new UmlRectangle(0, 0, 10, 5), bounds);
  }

  /**
   * Tests checking if UmlRectangle contains some point in space
   */
  @Test
  public void testContainsPoint() {
    Assert.assertTrue(rect.contains(new UmlPoint(5, 2.5)));
    Assert.assertFalse(rect.contains(new UmlPoint(20, 10)));
  }

  /**
   * tests checking if two UmlRectangles intersects
   */
  @Test
  public void testIntersects() {
    UmlRectangle r1 = new UmlRectangle(5, 2.5, 10, 10);
    UmlRectangle r2 = new UmlRectangle(10, 10, 10, 10);

    Assert.assertTrue(rect.intersects(r1));
    Assert.assertFalse(rect.intersects(r2));
  }

  /**
   * Tests checking if UmlRectangle intersects UmlLine
   */
  @Test
  public void testIntersectsLine() {
    UmlLine line = new UmlLine(1, 1, 2, 2);
    Assert.assertTrue(rect.intersectsLine(line));

    line = new UmlLine(0, 2.5, 2.5, 2.5);
    Assert.assertTrue(rect.intersectsLine(line));

    line = new UmlLine(10.1, 2.5, 10.1, 10.5);
    Assert.assertFalse(rect.intersectsLine(line));
  }

  /**
   * Tests checking if UmlRectangle intersects UmlPolyline
   */
  @Test
  public void testIntersectsPolyLine() {
    UmlPolyline line = new UmlPolyline(new double[] {1, 1, 2, 2, 3, 1});
    Assert.assertTrue(rect.intersectsLine(line));

    line = new UmlPolyline(new double[] {0, 0, 5, 5, 5, 0});
    Assert.assertTrue(rect.intersectsLine(line));

    line = new UmlPolyline(new double[] {0, 5.5, 10.5, 5.5, 10.5, 0});
    Assert.assertFalse(rect.intersectsLine(line));
  }

}
