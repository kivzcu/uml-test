package cz.zcu.kiv.oop.uml.geometry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.geom.Line2D;

/**
 * Test of UML line.
 *
 * @author Karel Zíbar
 */
public class UmlLineTest {

  protected static UmlLine line1;
  protected static UmlLine line2;

  /**
   * Prepare two lines for testing.
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    line1 = new UmlLine(0, 0, 1, 1);
    line2 = new UmlLine(new UmlPoint(1.5, 2.5), new UmlPoint(3.5, 4.5));
  }

  /**
   * Set tested lines back to null.
   */
  @AfterClass
  public static void tearDownAfterClass() {
    line1 = null;
    line2 = null;
  }

  /**
   * Checks getters and setters for StartPoint and EndPoint of the line.
   */
  @Test
  public void testSetGetPoints() {
    line1.setStartPoint(new UmlPoint(1.5, 2.5));
    line1.setEndPoint(new UmlPoint(3.5, 4.5));

    Assert.assertEquals(1.5, line1.getStartPointX(), 0.0);
    Assert.assertEquals(2.5, line1.getStartPointY(), 0.0);
    Assert.assertEquals(3.5, line1.getEndPointX(), 0.0);
    Assert.assertEquals(4.5, line1.getEndPointY(), 0.0);

    line1.setStartPointX(1.6);
    line1.setStartPointY(2.6);
    line1.setEndPointX(3.6);
    line1.setEndPointY(4.6);

    Assert.assertEquals(1.6, line1.getStartPointX(), 0.0);
    Assert.assertEquals(2.6, line1.getStartPointY(), 0.0);
    Assert.assertEquals(3.6, line1.getEndPointX(), 0.0);
    Assert.assertEquals(4.6, line1.getEndPointY(), 0.0);

    Assert.assertEquals(new UmlPoint(1.6, 2.6), line1.getStartPoint());
    Assert.assertEquals(new UmlPoint(3.6, 4.6), line1.getEndPoint());
  }

  /**
   * Tests if wanted line is set correctly to the relation as a relationLine.
   */
  @Test
  public void testSetLines() {
    line1.setLine(0, 0, 1, 1);
    Assert.assertEquals(new UmlPoint(0, 0), line1.getStartPoint());
    Assert.assertEquals(new UmlPoint(1, 1), line1.getEndPoint());

    line1.setLine(new UmlPoint(1, 1), new UmlPoint(0, 0));
    Assert.assertEquals(new UmlPoint(1, 1), line1.getStartPoint());
    Assert.assertEquals(new UmlPoint(0, 0), line1.getEndPoint());
  }

  /**
   * Tests if correct shape is returned.
   */
  @Test
  public void testGetShape() {
    Line2D shape = line2.getShape();
    Assert.assertEquals(1.5, shape.getX1(), 0.0);
    Assert.assertEquals(2.5, shape.getY1(), 0.0);
    Assert.assertEquals(3.5, shape.getX2(), 0.0);
    Assert.assertEquals(4.5, shape.getY2(), 0.0);
  }

  /**
   * Tests if relationLine contains some specific point.
   */
  @Test
  public void testContainsPoint() {
    UmlLine line = new UmlLine(0, 1, 1, 1);
    UmlLine line2 = new UmlLine(0, 1, 0, 2);

    UmlPoint point = new UmlPoint(0.5, 1);
    UmlPoint point2 = new UmlPoint(0, 0.5);

    Assert.assertTrue(line.contains(point));
    Assert.assertFalse(line.contains(point2));
    Assert.assertFalse(line2.contains(point2));
  }

  /**
   * Tests if two lines intersects.
   */
  @Test
  public void testIntersects() {
    UmlLine line = new UmlLine(0, 0, 5, 5);
    UmlLine line2 = new UmlLine(5, 0, 0, 5);
    UmlLine line3 = new UmlLine(1, 0, 6, 5);

    Assert.assertTrue(line.intersects(1, 1, 10, 10));
    Assert.assertTrue(line.intersects(new UmlRectangle(1, 1, 10, 10)));
    Assert.assertFalse(line.intersects(new UmlRectangle(20, 20, 5, 5)));

    Assert.assertTrue(line.intersectsLine(line2));
    Assert.assertFalse(line.intersectsLine(line3));
  }

  /**
   * Tests if correct instance of some UmlLine is returned using clone method.
   */
  @Test
  public void testClone() {
    Assert.assertTrue(line1.equals(line1.clone()));
  }

}
