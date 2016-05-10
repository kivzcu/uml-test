package cz.zcu.kiv.oop.uml.geometry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of Uml polyline.
 *
 * @author Karel Zíbar
 */
public class UmlPolylineTest {

  protected static UmlPolyline line1;
  protected static UmlPolyline line2;
  protected static UmlPolyline line3;

  /**
   * Prepare three UmlPolyLines for testing
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    line1 = new UmlPolyline(new double[] {0, 0, 2, 2, 4, 0, 6, 6});

    line2 = new UmlPolyline(new Double[] {0.0, 0.0, 2.0, 0.0, 4.0, 4.0, 6.0, 6.0});

    line3 = new UmlPolyline(new UmlPoint[] {
        new UmlPoint(0.0, 0.0),
        new UmlPoint(2.0, 2.0),
        new UmlPoint(4.0, 0.0),
        new UmlPoint(6.0, 6.0)
});
  }

  /**
   * Sets all three tested lines to zero.
   */
  @AfterClass
  public static void tearDownAfterClass() {
    line1 = null;
    line2 = null;
    line3 = null;
  }

  /**
   * Checks if IllegalArgumentException is thrown when only one point is mean to be the line.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetPointsOnlyOnePoint() {
    line1.setPoints(new Double[] {0.0, 0.0});
  }

  /**
   * Checks if IllegalArgumentException is thrown when trying to set line from only one coordinate.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetPointsOddCountOfCoordinates() {
    line1.setPoints(new Double[] {0.0});
  }

  /**
   * Checks if IllegalArgumentException is thrown when only one point is mean to be the line.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetPointsOddCountOfPoints() {
    line1.setPoints(new UmlPoint[] {new UmlPoint(0.0, 0.0)});
  }

  /**
   * Tests if correct start- and end-point are returned.
   */
  @Test
  public void testGetPoints() {
    UmlPoint startPoint = new UmlPoint(0.0, 0.0);
    UmlPoint endPoint = new UmlPoint(6.0, 6.0);
    UmlPoint[] umlPoints = new UmlPoint[] {
        new UmlPoint(0.0, 0.0),
        new UmlPoint(2.0, 2.0),
        new UmlPoint(4.0, 0.0),
        new UmlPoint(6.0, 6.0)
    };

    Assert.assertEquals(startPoint, line1.getStartPoint());
    Assert.assertEquals(endPoint, line1.getEndPoint());
    Assert.assertArrayEquals(umlPoints, line3.getPoints());
  }

  /**
   * Checks if correct bounds of line are returned.
   */
  @Test
  public void testGetBounds() {
    UmlRectangle bounds = new UmlRectangle(0.0, 0.0, 6, 6);

    Assert.assertEquals(bounds, line1.getBounds());
    Assert.assertEquals(bounds, line1.getBounds());
  }

  /**
   * Tests checking if line contains some point in space
   */
  @Test
  public void testContainsPoint() {
    Assert.assertTrue(line1.contains(new UmlPoint(1, 1)));
    Assert.assertFalse(line1.contains(new UmlPoint(70, 70)));
  }

  /**
   * Tests checking if two polyLines intersects.
   */
  @Test
  public void testIntersects() {
    Assert.assertTrue(line1.intersects(new UmlRectangle(2, 2, 10, 10)));
    Assert.assertTrue(line1.intersects(2, 2, 10, 10));
  }

  /**
   * Tests checking if two polyLines intersects
   */
  @Test
  public void testIntersectLine() {
    UmlLine line = new UmlLine(0, 1, 3, 4);
    Assert.assertFalse(line1.intersectsLine(line));

    line = new UmlLine(0, 1, 6, 1);
    Assert.assertTrue(line1.intersectsLine(line));
  }

  /**
   * Tests checking if two polyLines intersects
   */
  @Test
  public void testIntersectPolyLine() {
    Assert.assertTrue(line1.intersectsLine(line2));

    UmlPolyline line = new UmlPolyline(new double[] {0.5, 1, 0.5, 5});
    Assert.assertFalse(line1.intersectsLine(line));
  }

  /**
   * Tests if clone method returned the correct instance of UmlPolyLine
   */
  @Test
  public void testClone() {
    Assert.assertTrue(line1.equals(line1.clone()));
  }

}
