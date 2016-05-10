package cz.zcu.kiv.oop.uml.geometry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

/**
 * Test of UML dimension.
 *
 * @author Karel Zíbar
 */
public class UmlDimensionTest {

  protected static UmlDimension dim1;
  protected static UmlDimension dim2;
  protected static UmlDimension dim3;

  /**
   * Method prepare 3 UmlDimension for testing
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    dim1 = new UmlDimension();
    dim2 = new UmlDimension(1.5, 2.5);
    dim3 = new UmlDimension(dim2);
  }

  /**
   * Method set all 3 UmlDimension back to null.
   */
  @AfterClass
  public static void tearDownAfterClass() {
    dim1 = null;
    dim2 = null;
    dim3 = null;
  }

  /**
   * Checks if IllegalArgumentException is thrown when trying to set width of UmlDimension to value < 0
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeWidth() {
    dim1.setWidth(-1);
  }

  /**
   * Checks if IllegalArgumentException is thrown when trying to set height of UmlDimension to value < 0
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    dim1.setHeight(-1);
  }

  /**
   * Checks if correct values of height and width are returned.
   */
  @Test
  public void testGetSizes() {
    Assert.assertEquals(0.0, dim1.getWidth(), 0.0);
    Assert.assertEquals(0.0, dim1.getHeight(), 0.0);
  }

  /**
   * Checks if correct values of height and width are set.
   */
  @Test
  public void testSetSizes() {
    dim1.setSize(new UmlDimension(0.5, 0.6));
    Assert.assertEquals(0.5, dim1.getWidth(), 0.0);
    Assert.assertEquals(0.6, dim1.getHeight(), 0.0);
  }

  /**
   * Checks if correct instance of UmlDimension is returned from another UmlDimension.
   */
  @Test
  public void testGetDimension() {
    Dimension dimension = new Dimension(1, 2);
    Assert.assertEquals(dimension, dim2.getDimension());
  }

  /**
   * Checks if correct instance of UmlDimension is returned when cloning created dimension.
   */
  @Test
  public void testClone() {
    Assert.assertEquals(dim3, dim3.clone());
  }

}
