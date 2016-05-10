package cz.zcu.kiv.oop.uml.geometry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of UML point.
 *
 * @author Karel Zíbar
 */
public class UmlPointTest {

  protected static UmlPoint point1;
  protected static UmlPoint point2;

  /**
   * Prepare two points for testing
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    point1 = new UmlPoint(0, 0);
    point2 = new UmlPoint(1, 1);
  }

  /**
   * Set both tested points to null
   */
  @AfterClass
  public static void tearDownAfterClass() {
    point1 = null;
    point2 = null;
  }

  /**
   * Tests whether correct value of distance of two points in space is returned,
   */
  @Test
  public void testDistance() {
    Assert.assertEquals(Math.sqrt(2.0), point1.distanceFrom(point2), 0.1);
    Assert.assertEquals(Math.sqrt(2.2), point1.distanceFrom(1.1, 1.1), 0.1);
  }

  /**
   * Tests if clone mthod return the same instance of UmlPoint.
   */
  @Test
  public void testClone() {
    Assert.assertTrue(point1.equals(point1.clone()));
  }

}
