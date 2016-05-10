package cz.zcu.kiv.oop.uml.util;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.zcu.kiv.oop.uml.element.UmlClassType;
import cz.zcu.kiv.oop.uml.element.UmlRelationType;

/**
 * Tests UmlTranslationUtils.
 * 
 * @author Patrik Harag
 */
public class UmlTranslationUtilsTest {

  /**
   * Tests if all enum items are translated.
   */
  @Test
  public void testUmlClassTypeTranslated() {
    for (UmlClassType type : UmlClassType.values()) {
      assertFalse(UmlTranslationUtils.translateClassType(type).isEmpty());
    }
  }
    
  /**
   * Tests if all enum items are translated.
   */
  @Test
  public void testUmlRelationTypeTranslated() {
    for (UmlRelationType type : UmlRelationType.values()) {
      assertFalse(UmlTranslationUtils.translateRelationType(type).isEmpty());
    }
  }
    
}