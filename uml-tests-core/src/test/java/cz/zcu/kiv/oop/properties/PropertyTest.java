package cz.zcu.kiv.oop.properties;

import org.junit.Test;

/**
 * Tests Property class.
 * 
 * @author Patrik Harag
 */
public class PropertyTest {

  /**
   * Key cannot be empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyKey() {
    new Property("", "val");
  }
    
  /**
   * Key cannot be blank.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBlankKey() {
    new Property(" \n \t ", "val");
  }
    
  /**
   * Key cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullKey() {
    new Property(null, "val");
  }

}